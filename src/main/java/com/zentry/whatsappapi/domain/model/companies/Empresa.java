package com.zentry.whatsappapi.domain.model.companies;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "empresas")
public class Empresa {

    @Id
    private String id;

    private String nome;

    @Field("cnpj") // Pode ser usado se você quiser customizar o nome do campo no MongoDB
    private String cnpj;

    @Field("cpf")
    private String cpf;

    private String nomeResponsavel;
    private String emailResponsavel;

    // 0: Aguardando, 1: Em análise, 2: Concluído
    private Integer estadoPagamento;

    private String planoContratado;

    // Construtor
    public Empresa(String nome, String cnpj, String cpf, String nomeResponsavel, String emailResponsavel, Integer estadoPagamento, String planoContratado) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.cpf = cpf;
        this.nomeResponsavel = nomeResponsavel;
        this.emailResponsavel = emailResponsavel;
        this.estadoPagamento = estadoPagamento;
        this.planoContratado = planoContratado;
    }

    public Empresa() {

    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

    public Integer getEstadoPagamento() {
        return estadoPagamento;
    }

    public void setEstadoPagamento(Integer estadoPagamento) {
        this.estadoPagamento = estadoPagamento;
    }

    public String getPlanoContratado() {
        return planoContratado;
    }

    public void setPlanoContratado(String planoContratado) {
        this.planoContratado = planoContratado;
    }

    // Pode adicionar também métodos de equals e hashCode para garantir a comparação correta entre instâncias
}
