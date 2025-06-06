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
import com.br.api.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public List<ProfessorDTO> listarTodos() {
        return professorRepository.findAll()
            .stream()
            .map(professorMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ProfessorDTO buscarProfessorPorId(Long id) throws ProfessorNotFoundException {
        return professorRepository.findById(id)
            .map(professorMapper::toDTO)
            .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado de ID "+ id + " não encontrado!"));
    }

    public List<ProfessorDTO> buscarPorDisciplina(int disciplinaId) {
        return professorRepository.findByDisciplinaId(disciplinaId)
            .stream()
            .map(professorMapper::toDTO)
            .collect(Collectors.toList());
    }

    public boolean validarDadosProfessor(Professor professor) throws InvalidCredentialException {
        if (professor == null) {
            throw new InvalidCredentialException("O professor deve ter um nome");
        }

        String cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        String rgRegex = "^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$";
        String telefoneRegex = "^\\(\\d{2}\\) \\d{5}-\\d{4}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (!professor.getCpf().matches(cpfRegex)) {
            throw new InvalidCredentialException("Formato inválido do CPF " + professor.getCpf() + " do professor " + professor.getNome_professor());
        }

        if (!professor.getRg().matches(rgRegex)) {
            throw new InvalidCredentialException("Formato inválido do RG " + professor.getRg() + " do professor " + professor.getNome_professor());
        }

        if (!professor.getTelefone().matches(telefoneRegex)) {
            throw new InvalidCredentialException("Formato inválido do Telefone " + professor.getTelefone() + " do professor " + professor.getNome_professor());
        }

        if (!professor.getEmail().matches(emailRegex)) {
            throw new InvalidCredentialException("Formato inválido do Email " + professor.getEmail());
        }

        return true;
    }

    @Transactional
    public ProfessorDTO cadastrarProfessor(ProfessorCadastroDTO dto) throws InvalidCredentialException {
        Professor professor = professorMapper.toEntity(dto);
        
        validarDadosProfessor(professor);
        
        // Verificar se CPF ou RG já existem
        if (professorRepository.existsByCpf(professor.getCpf())) {
            throw new InvalidCredentialException("CPF já cadastrado");
        }
        
        if (professorRepository.existsByRg(professor.getRg())) {
            throw new InvalidCredentialException("RG já cadastrado");
        }

        professor = professorRepository.save(professor);
        return professorMapper.toDTO(professor);
    }

    @Transactional
    public ProfessorDTO atualizarProfessor(Long id, ProfessorCadastroDTO dto) throws ProfessorNotFoundException, InvalidCredentialException {
        Professor professor = professorRepository.findById(id)
        .orElseThrow(() -> new ProfessorNotFoundException("Professor não encontrado de ID "+ id + " não encontrado!"));


        professor.setNome_professor(dto.nome());
        professor.setCpf(dto.cpf());
        professor.setRg(dto.rg());
        professor.setTelefone(dto.telefone());
        professor.setEmail(dto.email());

        validarDadosProfessor(professor);

        professor = professorRepository.save(professor);
        return professorMapper.toDTO(professor);
    }

    @Transactional
    public void excluirProfessor(Long id) throws ProfessorNotFoundException {
        if (!professorRepository.existsById(id)) {
            throw new ProfessorNotFoundException("Professor de ID " + id + " não encontrado!");
        }
        professorRepository.deleteById(id);
    }
}
