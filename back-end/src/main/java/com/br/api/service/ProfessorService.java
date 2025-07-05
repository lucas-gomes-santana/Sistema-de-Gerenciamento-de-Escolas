package com.br.api.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorAtualizacaoDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.dto.professor.ProfessorDetalhesDTO;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.ProfessorException;
import com.br.api.mapper.ProfessorMapper;
import com.br.api.model.Professor;
import com.br.api.model.Disciplina;
import com.br.api.model.Endereco;
import com.br.api.model.Endereco.TipoEntidade;
import com.br.api.repository.ProfessorRepository;
import com.br.api.repository.DisciplinaRepository;
import com.br.api.repository.EnderecoRepository;
import com.br.api.repository.LoginRepository;
import com.br.api.model.Login;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.br.api.dto.professor.ProfessorCadastroComLoginDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorMapper professorMapper;
    private final EnderecoRepository enderecoRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ProfessorDTO> listarTodos() {
        return professorRepository.findAll()
            .stream()
            .map(professorMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ProfessorDetalhesDTO buscarPorId(Long id) throws ProfessorException {
        return professorRepository.findById(id)
            .map(professorMapper::toDetalhesDTO)
            .orElseThrow(() -> new ProfessorException("Professor não encontrado"));
    }

    @Transactional
    public ProfessorDTO cadastrar(ProfessorCadastroDTO dto) throws InvalidCredentialException {
        // Verificar se CPF ou RG já existem
        if (professorRepository.existsByCpf(dto.cpf())) {
            throw new InvalidCredentialException("CPF já cadastrado");
        }
        
        if (professorRepository.existsByRg(dto.rg())) {
            throw new InvalidCredentialException("RG já cadastrado");
        }

        // Buscar ou criar disciplina
        Disciplina disciplina = null;
        if (dto.disciplina() != null) {
            disciplina = disciplinaRepository.findByNome_disciplina(dto.disciplina().nomeDisciplina())
                .orElseGet(() -> {
                    Disciplina novaDisciplina = new Disciplina();
                    novaDisciplina.setNome_disciplina(dto.disciplina().nomeDisciplina());
                    return disciplinaRepository.save(novaDisciplina);
                });
        }

        // Criar professor usando o construtor com validações
        Professor professor = professorMapper.toEntity(dto, disciplina);

        // Primeiro salva o professor para obter o ID
        professor = professorRepository.save(professor);

        // Salvar endereço se existir
        if (professor.getEndereco() != null) {
            Endereco endereco = professor.getEndereco();
            endereco.setTipo_entidade(TipoEntidade.PROFESSOR);
            endereco.setId_entidade(professor.getId_professor());
            endereco = enderecoRepository.save(endereco);
            professor.definirEndereco(endereco);
        }

        professor = professorRepository.save(professor);
        return professorMapper.toDTO(professor);
    }

    @Transactional
    public ProfessorDTO atualizar(Long id, ProfessorAtualizacaoDTO dto) throws ProfessorException, InvalidCredentialException {
        Professor professor = professorRepository.findById(id)
            .orElseThrow(() -> new ProfessorException("Professor não encontrado"));

        // Atualiza dados básicos (sem dados sensíveis)
        professor.atualizarDadosBasicos(dto.nome(), dto.telefone(), dto.email());

        // Atualiza a disciplina se necessário
        if (dto.disciplina() != null) {
            Disciplina disciplina = disciplinaRepository.findByNome_disciplina(dto.disciplina().nomeDisciplina())
                .orElseGet(() -> {
                    Disciplina novaDisciplina = new Disciplina();
                    novaDisciplina.setNome_disciplina(dto.disciplina().nomeDisciplina());
                    return disciplinaRepository.save(novaDisciplina);
                });
            professor.alterarDisciplina(disciplina);
        }

        // Atualiza o endereço se fornecido
        if (dto.endereco() != null) {
            Endereco endereco = professor.getEndereco();
            if (endereco == null) {
                endereco = new Endereco();
                endereco.setTipo_entidade(TipoEntidade.PROFESSOR);
                endereco.setId_entidade(professor.getId_professor());
            }
            endereco.setNome_rua(dto.endereco().rua());
            endereco.setNome_bairro(dto.endereco().bairro());
            endereco.setCep(dto.endereco().cep());
            endereco.setComplemento(dto.endereco().complemento());
            endereco = enderecoRepository.save(endereco);
            professor.definirEndereco(endereco);
        }

        professor = professorRepository.save(professor);
        return professorMapper.toDTO(professor);
    }

    @Transactional
    public void excluir(Long id) throws ProfessorException {
        if (!professorRepository.existsById(id)) {
            throw new ProfessorException("Professor não encontrado");
        }
        professorRepository.deleteById(id);
    }

    @Transactional
    public void cadastrarComLogin(ProfessorCadastroComLoginDTO dto) throws InvalidCredentialException {
        // Validar gestor
        Login.TipoUsuario tipoGestor = Login.TipoUsuario.GESTOR;
        Login gestor = loginRepository.findByNomeUsuarioAndTipoUsuario(dto.nomeUsuarioGestor(), tipoGestor)
            .orElseThrow(() -> new InvalidCredentialException("Credenciais do gestor inválidas ou gestor não encontrado!"));
        if (!passwordEncoder.matches(dto.senhaGestor(), gestor.getSenha())) {
            throw new InvalidCredentialException("Senha do gestor inválida!");
        }
        
        // Reaproveitar lógica do método cadastrar
        ProfessorCadastroDTO cadastroDTO = new ProfessorCadastroDTO(
            dto.nome(), dto.cpf(), dto.rg(), dto.telefone(), dto.email(), dto.disciplina(), dto.turma(), dto.endereco()
        );
        this.cadastrar(cadastroDTO);
    }
}
