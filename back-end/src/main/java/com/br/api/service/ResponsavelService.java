package com.br.api.service;

import com.br.api.dto.responsaveis.ResponsavelCadastroDTO;
import com.br.api.dto.responsaveis.ResponsavelAtualizacaoDTO;
import com.br.api.dto.responsaveis.ResponsaviesDTO;
import com.br.api.dto.responsaveis.ResponsaveisDetalhesDTO;
import com.br.api.exception.ResponsavelException;
import com.br.api.mapper.ResponsavelMapper;
import com.br.api.model.Responsavel;
import com.br.api.repository.ResponsavelRepository;
import com.br.api.repository.EnderecoRepository;
import com.br.api.model.Endereco;
import com.br.api.model.Endereco.TipoEntidade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.br.api.dto.responsaveis.ResponsavelCadastroComLoginDTO;
import com.br.api.repository.LoginRepository;
import com.br.api.model.Login;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponsavelService {
    private final ResponsavelRepository responsavelRepository;
    private final ResponsavelMapper responsavelMapper;
    private final EnderecoRepository enderecoRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponsaviesDTO cadastrar(ResponsavelCadastroDTO dto) throws ResponsavelException {
        // Verificar se CPF ou RG já existem
        if (responsavelRepository.existsByCpf(dto.cpf())) {
            throw new ResponsavelException("CPF já cadastrado");
        }

        if (responsavelRepository.existsByRg(dto.rg())) {
            throw new ResponsavelException("RG já cadastrado");
        }
        
        // Criar responsável usando o construtor com validações
        Responsavel responsavel = responsavelMapper.toEntity(dto);

        // Primeiro salva o responsável para obter o ID
        responsavel = responsavelRepository.save(responsavel);

        // Salvar endereço se existir
        if (dto.endereco() != null) {
            Endereco endereco = new Endereco();
            endereco.setNome_rua(dto.endereco().rua());
            endereco.setNome_bairro(dto.endereco().bairro());
            endereco.setCep(dto.endereco().cep());
            endereco.setComplemento(dto.endereco().complemento());
            endereco.setTipo_entidade(TipoEntidade.ADMIN); // Responsável é considerado ADMIN
            endereco.setId_entidade(responsavel.getId_responsavies());
            endereco = enderecoRepository.save(endereco);
            responsavel.definirEndereco(endereco);
        }

        responsavel = responsavelRepository.save(responsavel);
        return responsavelMapper.toDTO(responsavel);
    }

    public List<ResponsaviesDTO> listarTodos() {
        return responsavelRepository.findAll().stream().map(responsavelMapper::toDTO).toList();
    }

    public ResponsaveisDetalhesDTO buscarPorId(Long id) throws ResponsavelException {
        Responsavel responsavel = responsavelRepository.findById(id)
            .orElseThrow(() -> new ResponsavelException("Responsável não encontrado"));
        return responsavelMapper.toDetalhesDTO(responsavel);
    }

    public ResponsaviesDTO atualizar(Long id, ResponsavelAtualizacaoDTO dto) throws ResponsavelException {
        Responsavel responsavel = responsavelRepository.findById(id)
            .orElseThrow(() -> new ResponsavelException("Responsável não encontrado"));

        // Atualiza dados básicos (sem dados sensíveis)
        responsavel.atualizarDadosBasicos(dto.nome(), dto.telefone());

        // Atualiza o endereço se fornecido
        if (dto.endereco() != null) {
            Endereco endereco = responsavel.getEndereco();
            if (endereco == null) {
                endereco = new Endereco();
                endereco.setTipo_entidade(TipoEntidade.ADMIN);
                endereco.setId_entidade(responsavel.getId_responsavies());
            }
            endereco.setNome_rua(dto.endereco().rua());
            endereco.setNome_bairro(dto.endereco().bairro());
            endereco.setCep(dto.endereco().cep());
            endereco.setComplemento(dto.endereco().complemento());
            endereco = enderecoRepository.save(endereco);
            responsavel.definirEndereco(endereco);
        }

        responsavel = responsavelRepository.save(responsavel);
        return responsavelMapper.toDTO(responsavel);
    }

    public void deletar(Long id) throws ResponsavelException {
        if (!responsavelRepository.existsById(id)) {
            throw new ResponsavelException("Responsável não encontrado");
        }
        responsavelRepository.deleteById(id);
    }

    @Transactional
    public void cadastrarComLogin(ResponsavelCadastroComLoginDTO dto) throws ResponsavelException, com.br.api.exception.InvalidCredentialException {
        // Validar gestor
        Login.TipoUsuario tipoGestor = Login.TipoUsuario.GESTOR;
        Login gestor = loginRepository.findByNomeUsuarioAndTipoUsuario(dto.nomeUsuarioGestor(), tipoGestor)
            .orElseThrow(() -> new com.br.api.exception.InvalidCredentialException("Credenciais do gestor inválidas ou gestor não encontrado!"));
        if (!passwordEncoder.matches(dto.senhaGestor(), gestor.getSenha())) {
            throw new com.br.api.exception.InvalidCredentialException("Senha do gestor inválida!");
        }
        // Reaproveitar lógica do método cadastrar
        ResponsavelCadastroDTO cadastroDTO = new ResponsavelCadastroDTO(
            dto.nome(), dto.cpf(), dto.rg(), dto.telefone(), dto.endereco()
        );
        this.cadastrar(cadastroDTO);
    }
} 