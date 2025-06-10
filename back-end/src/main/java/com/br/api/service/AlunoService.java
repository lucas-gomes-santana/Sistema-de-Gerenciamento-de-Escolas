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
import com.br.api.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.br.api.model.Aluno;
import com.br.api.model.Turma;
import com.br.api.model.Endereco;
import com.br.api.model.Endereco.TipoEntidade;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final EnderecoRepository enderecoRepository;
    private final AlunoMapper alunoMapper;
    
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

    public AlunoDetalhesDTO buscarAlunoPorId(Long id) throws AlunoNotFoundException {
        return alunoRepository.findById(id)
            .map(alunoMapper::toDetalhesDTO)
            .orElseThrow(() -> new AlunoNotFoundException("Aluno de ID "+ id +" não encontrado"));
    }

    public boolean validarDadosAluno(Aluno aluno) throws InvalidCredentialException {
        if(aluno == null) {
            throw new InvalidCredentialException("O aluno deve ter um nome");
        }

        String telefoneRegex = "^\\(\\d{2}\\) \\d{5}-\\d{4}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if(!aluno.getTelefone().matches(telefoneRegex)) {
            throw new InvalidCredentialException("Formato inválido do Telefone " + aluno.getTelefone() + " do aluno " + aluno.getNome_aluno());
        }

        if (aluno.getEmail() != null && !aluno.getEmail().matches(emailRegex)) {
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
        Turma turma = turmaRepository.findByNome_turma(dto.turma().nomeTurma())
            .orElseThrow(() -> new AlunoNotFoundException("Turma não encontrada"));
        aluno.setTurma(turma);

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
        aluno.setEndereco(endereco);

        // Salva o aluno novamente com o endereço
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toDTO(aluno);
    }

    // Método para atualizar dados de alunos do sistema
    public AlunoDTO atualizar(Long id, AlunoCadastroDTO dto) throws 
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

        // Atualiza a turma se necessário
        Turma novaTurma = turmaRepository.findByNome_turma(dto.turma().nomeTurma())
            .orElseThrow(() -> new TurmaNotFoundException("Turma não encontrada"));
        if (!novaTurma.getId_turma().equals(aluno.getTurma().getId_turma())) {
            aluno.setTurma(novaTurma);
        }

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
    public void excluirAluno(Long id) throws AlunoNotFoundException {
        if (!alunoRepository.existsById(id)) {
            throw new AlunoNotFoundException("Aluno de ID " + id +  " não encontrado!");
        }
        alunoRepository.deleteById(id);
    }
}
