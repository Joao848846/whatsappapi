package com.zentry.whatsappapi.adapter.in.controller.Users.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
public class UsersDTO {


    private String id;

    private String empresa;

    private String nome;

    private String username;

    private String nascimento;

    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter apenas números e ter entre 10 a 11 dígitos.")
    private String telefone;

    private String senha;

    private String tipo;

    private String cpf;

    private Boolean ativo;

    private List<String> modos;


    public UsersDTO() {}

    public UsersDTO(String id, String nome, String nascimento, String email, String telefone, String senha, String cpf, Boolean ativo, List<String> modos, String tipo, String username, String empresa) {
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
        this.username = username;
        this.empresa = empresa;

    }


}