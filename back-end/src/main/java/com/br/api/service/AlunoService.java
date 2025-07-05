package com.br.api.service;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.br.api.dto.aluno.AlunoCadastroDTO;
import com.br.api.dto.aluno.AlunoAtualizacaoDTO;
import com.br.api.dto.aluno.AlunoDTO;
import com.br.api.dto.aluno.AlunoDetalhesDTO;
import com.br.api.exception.*;
import com.br.api.mapper.AlunoMapper;
import com.br.api.repository.AlunoRepository;
import com.br.api.repository.TurmaRepository;
import com.br.api.repository.EnderecoRepository;
import com.br.api.repository.LoginRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.br.api.model.Aluno;
import com.br.api.model.Turma;
import com.br.api.model.Endereco;
import com.br.api.model.Endereco.TipoEntidade;
import java.util.List;
import com.br.api.dto.aluno.CadastroAlunoComLoginDTO;
import com.br.api.model.Login;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final EnderecoRepository enderecoRepository;
    private final LoginRepository loginRepository;
    private final AlunoMapper alunoMapper;
    private final PasswordEncoder passwordEncoder;
    
    public List<AlunoDTO> listarTodos() {
        return alunoRepository.findAll()
            .stream()
            .map(alunoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<AlunoDTO> buscarAlunosPorTurma(Long id_turma) {
        return alunoRepository.findByTurmaId(id_turma)
            .stream()
            .map(alunoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public AlunoDetalhesDTO buscarAlunoPorId(Long id) throws AlunoException {
        return alunoRepository.findById(id)
            .map(alunoMapper::toDetalhesDTO)
            .orElseThrow(() -> new AlunoException("Aluno de ID "+ id +" não encontrado"));
    }

    // Método de cadastro de novos alunos
    public AlunoDTO cadastrarAluno(AlunoCadastroDTO dto) throws AlunoException, InvalidCredentialException {
        // Verifica se a turma existe
        Turma turma = turmaRepository.findByNome_turma(dto.turma().nomeTurma())
            .orElseThrow(() -> new AlunoException("Turma não encontrada"));

        // Cria o aluno usando o construtor com validações
        Aluno aluno = alunoMapper.toEntity(dto, turma);

        // Primeiro salva o aluno para obter o ID
        aluno = alunoRepository.save(aluno);

        // Cria e salva o endereço
        Endereco endereco = new Endereco();
        endereco.setNome_rua(dto.endereco().rua());
        endereco.setNome_bairro(dto.endereco().bairro());
        endereco.setCep(dto.endereco().cep());
        endereco.setComplemento(dto.endereco().complemento());
        endereco.setTipo_entidade(TipoEntidade.ALUNO);
        endereco.setId_entidade(aluno.getId_aluno());
        endereco = enderecoRepository.save(endereco);
        
        // Define o endereço no aluno
        aluno.definirEndereco(endereco);

        // Salva o aluno novamente com o endereço
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(aluno);
    }

    // Método para atualizar dados de alunos do sistema
    public AlunoDTO atualizar(Long id, AlunoAtualizacaoDTO dto) throws 
    AlunoException, TurmaException, InvalidCredentialException {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new AlunoException("Aluno não encontrado com ID: "+id));

        // Atualiza dados básicos (sem dados sensíveis)
        aluno.atualizarDadosBasicos(dto.nome(), dto.telefone(), dto.email());

        // Atualiza a turma se necessário
        Turma novaTurma = turmaRepository.findByNome_turma(dto.turma().nomeTurma())
            .orElseThrow(() -> new TurmaException("Turma não encontrada"));
        aluno.alterarTurma(novaTurma);

        // Atualiza o endereço
        Endereco endereco = aluno.getEndereco();
        endereco.setNome_rua(dto.endereco().rua());
        endereco.setNome_bairro(dto.endereco().bairro());
        endereco.setCep(dto.endereco().cep());
        endereco.setComplemento(dto.endereco().complemento());
        endereco.setId_entidade(aluno.getId_aluno());
        enderecoRepository.save(endereco);

        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(aluno);
    }

    // Método para excluir alunos do sistema
    public void excluirAluno(Long id) throws AlunoException {
        if (!alunoRepository.existsById(id)) {
            throw new AlunoException("Aluno de ID " + id +  " não encontrado!");
        }
        alunoRepository.deleteById(id);
    }

    @Transactional
    public void cadastrarAlunoComLogin(CadastroAlunoComLoginDTO dto) throws AlunoException, InvalidCredentialException {
        // 0. Validar gestor
        Login.TipoUsuario tipoGestor = Login.TipoUsuario.GESTOR;
        Login gestor = loginRepository.findByNomeUsuarioAndTipoUsuario(dto.nomeUsuarioGestor(), tipoGestor)
            .orElseThrow(() -> new InvalidCredentialException("Credenciais do gestor inválidas ou gestor não encontrado!"));
        if (!passwordEncoder.matches(dto.senhaGestor(), gestor.getSenha())) {
            throw new InvalidCredentialException("Senha do gestor inválida!");
        }
        
        // 1. Criar e salvar endereço
        Endereco.TipoEntidade tipoEntidade = Endereco.TipoEntidade.valueOf(dto.tipoUsuario().toUpperCase());
        Endereco endereco = new Endereco();
        endereco.setNome_rua(dto.endereco().rua());
        endereco.setNome_bairro(dto.endereco().bairro());
        endereco.setCep(dto.endereco().cep());
        endereco.setComplemento(dto.endereco().complemento());
        endereco.setTipo_entidade(tipoEntidade);
        endereco.setId_entidade(0L); // temporário
        endereco = enderecoRepository.save(endereco);

        // 2. Criar e salvar aluno usando o construtor com validações
        Turma turma = turmaRepository.findByNome_turma(dto.turma().nomeTurma())
            .orElseThrow(() -> new AlunoException("Turma não encontrada"));
            
        Aluno aluno = new Aluno(
            dto.nome(),
            dto.cpf(),
            dto.rg(),
            dto.telefone(),
            dto.email(),
            turma
        );
        aluno.definirEndereco(endereco);
        aluno = alunoRepository.save(aluno);

        // 3. Atualizar id_entidade do endereço
        endereco.setId_entidade(aluno.getId_aluno());
        enderecoRepository.save(endereco);

        // 4. Criar e salvar login
        Login.TipoUsuario tipoUsuario = Login.TipoUsuario.valueOf(dto.tipoUsuario().toUpperCase());
        Login login = new Login();
        login.setNome_usuario(dto.nomeUsuario());
        login.setSenha(passwordEncoder.encode(dto.senha()));
        login.setTipo_usuario(tipoUsuario);
        login.setId_usuario(aluno.getId_aluno());
        loginRepository.save(login);
    }
}
