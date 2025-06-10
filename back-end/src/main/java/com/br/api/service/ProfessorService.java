package com.br.api.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.br.api.dto.professor.ProfessorCadastroDTO;
import com.br.api.dto.professor.ProfessorDTO;
import com.br.api.exception.InvalidCredentialException;
import com.br.api.exception.ProfessorNotFoundException;
import com.br.api.mapper.ProfessorMapper;
import com.br.api.model.Professor;
import com.br.api.model.Disciplina;
import com.br.api.repository.ProfessorRepository;
import com.br.api.repository.DisciplinaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorMapper professorMapper;

    public List<ProfessorDTO> listarTodos() {
        return professorRepository.findAll()
            .stream()
            .map(professorMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ProfessorDTO buscarPorId(Long id) throws ProfessorNotFoundException {
        return professorRepository.findById(id)
            .map(professorMapper::toDTO)
            .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado"));
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

        professor = professorRepository.save(professor);
        return professorMapper.toDTO(professor);
    }

    @Transactional
    public ProfessorDTO atualizar(Long id, ProfessorCadastroDTO dto) throws ProfessorNotFoundException, InvalidCredentialException {
        Professor professor = professorRepository.findById(id)
            .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado"));

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
    public void excluir(Long id) throws ProfessorNotFoundException {
        if (!professorRepository.existsById(id)) {
            throw new ProfessorNotFoundException("Professor não encontrado");
        }
        professorRepository.deleteById(id);
    }

    private void validarDadosProfessor(ProfessorCadastroDTO dto) throws InvalidCredentialException {
        String telefoneRegex = "^\\(\\d{2}\\) \\d{5}-\\d{4}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (!dto.telefone().matches(telefoneRegex)) {
            throw new InvalidCredentialException("Formato inválido do Telefone " + dto.telefone());
        }

        if (dto.email() != null && !dto.email().matches(emailRegex)) {
            throw new InvalidCredentialException("Formato inválido do Email " + dto.email());
        }
    }
}
