package com.zentry.whatsappapi.domain.model.scheduling;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "scheduling")
public class scheduling {

    @Id
    private String id;
    private String nome;
    private String telefone;
    private String documento;
    private String tipo_contrato;
    private String data_contrato;
    private String status_pagamento;
    private String valor_mensalidade;
    private Boolean lembreteEnviado;
    private LocalDateTime dataUltimoLembreteEnviado;



    public scheduling( String id ,String nome, String telefone, String documento, String tipo_contrato, String data_contrato , String status_pagamento, String valor_mensalidade, Boolean lembreteEnviado, LocalDateTime dataUltimoLembreteEnviado) {
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.tipo_contrato = tipo_contrato;
        this.data_contrato = data_contrato;
        this.status_pagamento = status_pagamento;
        this.valor_mensalidade = valor_mensalidade;
        this.lembreteEnviado = lembreteEnviado;
        this.dataUltimoLembreteEnviado = dataUltimoLembreteEnviado;
    }


    public scheduling() {

    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
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

    public Boolean getLembreteEnviado() {
        return lembreteEnviado;
    }

    public void setLembreteEnviado(Boolean lembreteEnviado) {
        this.lembreteEnviado = lembreteEnviado;
    }

    public LocalDateTime getDataUltimoLembreteEnviado() {
        return dataUltimoLembreteEnviado;
    }

    public void setDataUltimoLembreteEnviado(LocalDateTime dataUltimoLembreteEnviado) {
        this.dataUltimoLembreteEnviado = dataUltimoLembreteEnviado;
    }
}
