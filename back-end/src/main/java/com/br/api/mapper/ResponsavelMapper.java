package com.br.api.mapper;

import org.springframework.stereotype.Component;
import com.br.api.dto.responsaveis.ResponsavelCadastroDTO;
import com.br.api.dto.responsaveis.ResponsaviesDTO;
import com.br.api.model.Responsavel;
import com.br.api.model.Endereco;

@Component
public class ResponsavelMapper {
    public ResponsaviesDTO toDTO(Responsavel responsavel) {
        return new ResponsaviesDTO(
            responsavel.getId_responsavies(),
            responsavel.getNome_responsavel(),
            responsavel.getCpf(),
            responsavel.getRg(),
            responsavel.getTelefone()
        );
    }

    public Responsavel toEntity(ResponsavelCadastroDTO dto) {
        Responsavel responsavel = new Responsavel();
        responsavel.setNome_responsavel(dto.nome());
        responsavel.setCpf(dto.cpf());
        responsavel.setRg(dto.rg());
        responsavel.setTelefone(dto.telefone());
        if (dto.endereco() != null) {
            Endereco endereco = new Endereco();
            endereco.setNome_rua(dto.endereco().rua());
            endereco.setNome_bairro(dto.endereco().bairro());
            endereco.setCep(dto.endereco().cep());
            endereco.setComplemento(dto.endereco().complemento());
            responsavel.setEndereco(endereco);
        }
        return responsavel;
    }

    public void updateEntity(Responsavel responsavel, ResponsavelCadastroDTO dto) {
        responsavel.setNome_responsavel(dto.nome());
        responsavel.setCpf(dto.cpf());
        responsavel.setRg(dto.rg());
        responsavel.setTelefone(dto.telefone());
    }
} 