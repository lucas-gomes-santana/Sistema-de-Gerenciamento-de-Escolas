package com.br.api.mapper;

import com.br.api.dto.turma.TurmaCadastroDTO;
import com.br.api.dto.turma.TurmaDTO;
import com.br.api.model.Turma;

public class TurmaMapper {
    
    public TurmaDTO toDto(Turma turma) {
        return new TurmaDTO(
            turma.getId_turma(),
            turma.getTurno(),
            turma.getNome_turma(),
            turma.getAno_letivo()
        );
    }

    public Turma toEntity(TurmaCadastroDTO dto) {
        Turma turma = new Turma();
        turma.setNome_turma(dto.nome());
        turma.setAno_letivo(dto.ano_letivo());
        turma.setTurno(dto.turno());
    }
}
