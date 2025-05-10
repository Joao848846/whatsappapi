package com.zentry.whatsappapi.domain.model.scheduling;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scheduling")
public class scheduling {

    private String nome;
    private String telefone;
    private String tipo_contrato;
    private String data_contrato;
    private String status_pagamento;
    private String valor_mensalidade;


    public scheduling(String nome, String telefone, String tipo_contrato, String data_contrato , String status_pagamento, String valor_mensalidade) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipo_contrato = tipo_contrato;
        this.data_contrato = data_contrato;
        this.status_pagamento = status_pagamento;
        this.valor_mensalidade = valor_mensalidade;
    }


    public scheduling() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public String getData_contrato() {
        return data_contrato;
    }

    public void setData_contrato(String data_contrato) {
        this.data_contrato = data_contrato;
    }

    public String getStatus_pagamento() {
        return status_pagamento;
    }

    public void setStatus_pagamento(String status_pagamento) {
        this.status_pagamento = status_pagamento;
    }

    public String getValor_mensalidade() {
        return valor_mensalidade;
    }

    public void setValor_mensalidade(String valor_mensalidade) {
        this.valor_mensalidade = valor_mensalidade;
    }
}
