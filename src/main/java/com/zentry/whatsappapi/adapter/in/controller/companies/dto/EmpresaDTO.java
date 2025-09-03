package com.zentry.whatsappapi.adapter.in.controller.companies.dto;

import com.zentry.whatsappapi.domain.model.companies.Empresa;
import lombok.Data;


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


}
