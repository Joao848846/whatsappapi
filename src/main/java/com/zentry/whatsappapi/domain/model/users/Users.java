package com.zentry.whatsappapi.domain.model.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;


import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Document(collection = "users")
public class Users {


    @Id
    private String id;
    private String username;
    private String empresa;
    private String nome;
    private String nascimento; // formato: yyyy-MM-dd
    private String email;
    private String telefone;
    private String tipo;
    private String senha;
    private String cpf;
    private Boolean ativo;
    private List<String> modos;


    public Users() {}

    public Users(String id, String nome, String nascimento, String email, String telefone, String senha, String cpf, Boolean ativo, List<String> modos, String tipo, String username, String empresa) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.empresa = empresa;
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