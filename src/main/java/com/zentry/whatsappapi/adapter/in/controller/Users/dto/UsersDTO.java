package com.zentry.whatsappapi.adapter.in.controller.Users.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UsersDTO {

    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private String nome;
    @Setter
    @Getter
    private String nascimento;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String telefone;
    @Getter
    @Setter
    private String senha;
    @Getter
    @Setter
    private String cpf;
    @Getter
    @Setter
    private Boolean ativo;
    private List<String> modulosHabilitados;


    public UsersDTO() {}

    public UsersDTO(String id, String nome, String nascimento, String email, String telefone, String senha, String cpf, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.cpf = cpf;
        this.ativo = ativo;

    }

}