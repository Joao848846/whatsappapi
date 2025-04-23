package com.zentry.whatsappapi.domain.model.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class Users {

    @Getter
    @Setter
    @Id
    private String id;
    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private String nascimento; // formato: yyyy-MM-dd
    @Setter
    @Getter
    private String email;
    @Getter
    @Setter
    private String telefone;
    @Setter
    @Getter
    private String senha;
    @Setter
    @Getter
    private String cpf;
    @Setter
    @Getter
    private Boolean ativo;
    private List<String> modulosHabilitados;


    public Users() {}

    public Users(String id, String nome, String nascimento, String email, String telefone, String senha, String cpf, Boolean ativo) {
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