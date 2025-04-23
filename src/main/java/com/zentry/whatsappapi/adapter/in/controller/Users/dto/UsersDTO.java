package com.zentry.whatsappapi.adapter.in.controller.Users.dto;

import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter apenas números e ter entre 10 a 11 dígitos.")
    private String telefone;
    @Getter
    @Setter
    private String senha;
    @Getter
    @Setter
    private String tipo;
    @Getter
    @Setter
    private String cpf;
    @Getter
    @Setter
    private Boolean ativo;
    @Getter
    @Setter
    private List<String> modos;


    public UsersDTO() {}

    public UsersDTO(String id, String nome, String nascimento, String email, String telefone, String senha, String cpf, Boolean ativo, List<String> modos, String tipo) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.cpf = cpf;
        this.ativo = ativo;
        this.modos = modos;
        this.tipo = tipo;

    }

}