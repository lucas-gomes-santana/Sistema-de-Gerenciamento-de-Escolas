package com.br.api.mapper;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.model.Turma;

public class TurmaMapper {
    
    public TurmaDTO toDto(Turma turma) {
        return new TurmaDTO(
            turma.getId_turma(),
            turma.getTurno(),
            turma.getNome_turma()
        );
    }

    public Turma toEntity(TurmaCadastroDTO dto) {
        Turma turma = new Turma();
        turma.setNome_turma(dto.nome());
        turma.setTurno(dto.turno());
    }
}
