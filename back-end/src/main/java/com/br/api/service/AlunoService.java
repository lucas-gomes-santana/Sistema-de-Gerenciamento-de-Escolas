package com.br.api.service;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.br.api.dto.aluno.AlunoCadastroDTO;
import com.br.api.dto.aluno.AlunoDTO;
import com.br.api.dto.aluno.AlunoDetalhesDTO;
import com.br.api.exception.*;
import com.br.api.mapper.AlunoMapper;
import com.br.api.repository.AlunoRepository;
import com.br.api.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.br.api.model.Aluno;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoMapper alunoMapper;
    
    public List<AlunoDTO> listarTodos() {
        return alunoRepository.findAll()
            .stream()
            .map(alunoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public List<AlunoDTO> buscarAlunosPorTurma(int turmaId) {
        return alunoRepository.findByTurmaId(turmaId)
            .stream()
            .map(alunoMapper::toDTO)
            .collect(Collectors.toList());
    }

    public AlunoDetalhesDTO buscarAlunoPorId(int id) throws AlunoNotFoundException {
        return alunoRepository.findById(id)
            .map(alunoMapper::toDetalhesDTO)
            .orElseThrow(() -> new AlunoNotFoundException("Aluno de ID "+ id +" não encontrado"));
    }

    public boolean validarDadosAluno(Aluno aluno) throws InvalidCredentialException {
        if(aluno == null) {
            throw new InvalidCredentialException("O aluno deve ter um nome");
        }

        String cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        String rgRegex = "^(\\d{1,2}\\.?\\d{3}\\.?\\d{3}-?[\\dX]{1,2}|\\d{8,10}|[\\dX]{7,10})$";
        String telefoneRegex = "^\\(\\d{2}\\) \\d{5}-\\d{4}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if(!aluno.getCpf().matches(cpfRegex)) {
            throw new InvalidCredentialException("Formato inválido do CPF "+ aluno.getCpf()+ " do aluno "+ aluno.getNome_aluno());
        }

        if(!aluno.getRg().matches(rgRegex)) {
            throw new InvalidCredentialException("Formato inválido do RG " + aluno.getRg() + " do aluno " + aluno.getNome_aluno());
        }

        if(!aluno.getTelefone().matches(telefoneRegex)) {
            throw new InvalidCredentialException("Formato inválido do Telefone " + aluno.getTelefone() + " do aluno " + aluno.getNome_aluno());
        }

        if(!aluno.getEmail().matches(emailRegex)) {
            throw new InvalidCredentialException("Formato inválido do Email " + aluno.getEmail());
        }

        return true;
    }

    // Método de cadastro de novos alunos
    public AlunoDTO cadastrarAluno(AlunoCadastroDTO dto) throws AlunoNotFoundException, InvalidCredentialException {

        Aluno aluno = alunoMapper.toEntity(dto);

        // Validar dados antes de cadastrar
        aluno.setNome_aluno(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setTelefone(dto.telefone());
        aluno.setEmail(dto.email());

        validarDadosAluno(aluno); 

        // Verifica se a turma existe
        aluno.setTurma(turmaRepository.findById(dto.turmaId())
            .orElseThrow(() -> new AlunoNotFoundException("Turma não encontrada")));

        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(aluno);
    }

    // Método para atualizar dados de alunos do sistema
    public AlunoDTO atualizar(int id, AlunoCadastroDTO dto) throws 
    AlunoNotFoundException, TurmaNotFoundException, InvalidCredentialException {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com ID: "+id));

        // Validar dados antes de atualizar
        aluno.setNome_aluno(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setTelefone(dto.telefone());
        aluno.setEmail(dto.email());
        
        validarDadosAluno(aluno); 

        if (dto.turmaId() != aluno.getTurma().getId_turma()) {
            aluno.setTurma(turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new TurmaNotFoundException("Turma de ID " + dto.turmaId() + " não encontrada")));
        }

        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(aluno);
    }

    // Método para excluir alunos do sistema
    public void excluirAluno(int id) throws AlunoNotFoundException {
        if (!alunoRepository.existsById(id)) {
            throw new AlunoNotFoundException("Aluno de ID " + id +  " não encontrado!");
        }
        alunoRepository.deleteById(id);
    }
}
