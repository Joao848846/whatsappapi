package com.zentry.whatsappapi.adapter.in.controller.companies.dto;

import com.zentry.whatsappapi.domain.model.companies.Empresa;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmpresaDTO {

    private String id;
    private String nome;
    private String cnpj;
    private String cpf;
    private String nomeResponsavel;
    private String emailResponsavel;
    private Integer estadoPagamento;
    private String planoContratado;


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

}
