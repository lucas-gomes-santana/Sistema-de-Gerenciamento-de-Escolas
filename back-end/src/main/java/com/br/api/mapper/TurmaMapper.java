package com.br.api.mapper;

import org.springframework.stereotype.Component;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.model.Turma;

@Component
public class TurmaMapper {
    
    public TurmaDTO toDTO(Turma turma) {
        return new TurmaDTO(
            turma.getId_turma(),
            turma.getNome_turma(),
            turma.getTurno()
        );
    }

    public Turma toEntity(TurmaCadastroDTO dto) {
        Turma turma = new Turma();
        turma.setNome_turma(dto.nome());
        turma.setTurno(dto.turno());
        return turma;
    }

    public void updateEntity(Turma turma, TurmaCadastroDTO dto) {
        turma.setNome_turma(dto.nome());
        turma.setTurno(dto.turno());
    }
}
