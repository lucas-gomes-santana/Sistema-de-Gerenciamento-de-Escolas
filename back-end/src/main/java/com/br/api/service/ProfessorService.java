package com.br.api.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.ProfessorException;
import com.br.api.mapper.ProfessorMapper;
import com.br.api.model.Professor;
import com.br.api.model.Disciplina;
import com.br.api.model.Endereco;
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

    public ProfessorDTO buscarPorId(Long id) throws ProfessorException {
        return professorRepository.findById(id)
            .map(professorMapper::toDTO)
            .orElseThrow(() -> new ProfessorException("Professor não encontrado"));
    }

    @Transactional
    public ProfessorDTO cadastrar(ProfessorCadastroDTO dto) throws InvalidCredentialException {
        validarDadosProfessor(dto);

        // Verificar se CPF ou RG já existem
        if (professorRepository.existsByCpf(dto.cpf())) {
            throw new InvalidCredentialException("CPF já cadastrado");
        }
        
        if (professorRepository.existsByRg(dto.rg())) {
            throw new InvalidCredentialException("RG já cadastrado");
        }

        Professor professor = professorMapper.toEntity(dto);
        professor.setStatus("Ativo");

        // Se uma disciplina foi fornecida, verifica se já existe ou cria uma nova
        if (dto.disciplina() != null) {
            Disciplina disciplina = disciplinaRepository.findByNome_disciplina(dto.disciplina().nomeDisciplina())
                .orElseGet(() -> {
                    Disciplina novaDisciplina = new Disciplina();
                    novaDisciplina.setNome_disciplina(dto.disciplina().nomeDisciplina());
                    return disciplinaRepository.save(novaDisciplina);
                });
            professor.setDisciplinas(disciplina);
        }

        // Salvar endereço se existir
        if (professor.getEndereco() != null) {
            Endereco endereco = professor.getEndereco();
            endereco = enderecoRepository.save(endereco);
            professor.setEndereco(endereco);
        }

        professor = professorRepository.save(professor);
        return professorMapper.toDTO(professor);
    }

    @Transactional
    public ProfessorDTO atualizar(Long id, ProfessorCadastroDTO dto) throws ProfessorException, InvalidCredentialException {
        Professor professor = professorRepository.findById(id)
            .orElseThrow(() -> new ProfessorException("Professor não encontrado"));

        validarDadosProfessor(dto);

        // Verificar se o CPF ou RG já existem em outro professor
        if (!professor.getCpf().equals(dto.cpf()) && professorRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        
        if (!professor.getRg().equals(dto.rg()) && professorRepository.existsByRg(dto.rg())) {
            throw new IllegalArgumentException("RG já cadastrado");
        }

        professor.setNome_professor(dto.nome());
        professor.setCpf(dto.cpf());
        professor.setRg(dto.rg());
        professor.setTelefone(dto.telefone());
        professor.setEmail(dto.email());

        // Se uma disciplina foi fornecida, verifica se já existe ou cria uma nova
        if (dto.disciplina() != null) {
            Disciplina disciplina = disciplinaRepository.findByNome_disciplina(dto.disciplina().nomeDisciplina())
                .orElseGet(() -> {
                    Disciplina novaDisciplina = new Disciplina();
                    novaDisciplina.setNome_disciplina(dto.disciplina().nomeDisciplina());
                    return disciplinaRepository.save(novaDisciplina);
                });
            professor.setDisciplinas(disciplina);
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

    private void validarDadosProfessor(ProfessorCadastroDTO dto) throws InvalidCredentialException {
        try {
            new com.br.api.valueobject.Telefone(dto.telefone());
        } 
        catch (Exception e) {
            throw new InvalidCredentialException("Formato inválido do Telefone " + dto.telefone());
        }
        if (dto.email() != null) {
            try {
                new com.br.api.valueobject.Email(dto.email());
            } 
            catch (Exception e) {
                throw new InvalidCredentialException("Formato inválido do Email " + dto.email());
            }
        }
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
