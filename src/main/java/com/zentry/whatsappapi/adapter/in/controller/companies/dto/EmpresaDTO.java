package com.zentry.whatsappapi.adapter.in.controller.companies.dto;

import com.zentry.whatsappapi.domain.model.companies.Empresa;
import org.hibernate.mapping.List;
import org.w3c.dom.stylesheets.LinkStyle;

// DTO (Data Transfer Object) que representa os dados trocados entre front e back
public class EmpresaDTO {

    private String id;
    private String nome;
    private String cnpj;
    private String cpf;
    private String nomeResponsavel;
    private String emailResponsavel;
    private Integer estadoPagamento;
    private String planoContratado;

    public EmpresaDTO() {}

    // Construtor que transforma um objeto Empresa (entidade) em DTO
    public EmpresaDTO(Empresa empresa) {
        this.id = empresa.getId();
        this.nome = empresa.getNome();
        this.cnpj = empresa.getCnpj();
        this.cpf = empresa.getCpf();
        this.nomeResponsavel = empresa.getNomeResponsavel();
        this.emailResponsavel = empresa.getEmailResponsavel();
        this.estadoPagamento = empresa.getEstadoPagamento();
        this.planoContratado = empresa.getPlanoContratado();
    }

    // Getters — agora retornando os valores reais das variáveis
    public String getId() { return id; }

    public String getNome() { return nome; }

    public String getCnpj() { return cnpj; }

    public String getCpf() { return cpf; }

    public String getNomeResponsavel() { return nomeResponsavel; }

    public String getEmailResponsavel() { return emailResponsavel; }

    public Integer getEstadoPagamento() { return estadoPagamento; }

    public String getPlanoContratado() { return planoContratado; }

    //  — permitem que os valores sejam definidos a partir do JSON recebido
    public void setId(String id) { this.id = id; }

    public void setNome(String nome) { this.nome = nome; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }

    public void setEmailResponsavel(String emailResponsavel) { this.emailResponsavel = emailResponsavel; }

    public void setEstadoPagamento(Integer estadoPagamento) { this.estadoPagamento = estadoPagamento; }

    public void setPlanoContratado(String planoContratado) { this.planoContratado = planoContratado; }
}
