package com.zentry.whatsappapi.domain.model.scheduling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "scheduling")
public class scheduling {

    @Id
    private String id;
    private String empresaID;
    private String nome;
    private String telefone;
    private String documento;
    private String tipo_contrato;
    private String data_contrato;
    private String statusPagamento;
    private String valor_mensalidade;
    private Boolean lembreteEnviado;
    private LocalDateTime dataUltimoLembreteEnviado;



    public scheduling(String id , String empresaID , String nome, String telefone, String documento, String tipo_contrato, String data_contrato , String statusPagamento, String valor_mensalidade, Boolean lembreteEnviado, LocalDateTime dataUltimoLembreteEnviado) {
        this.empresaID = empresaID;
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
        this.tipo_contrato = tipo_contrato;
        this.data_contrato = data_contrato;
        this.statusPagamento = statusPagamento;
        this.valor_mensalidade = valor_mensalidade;
        this.lembreteEnviado = lembreteEnviado;
        this.dataUltimoLembreteEnviado = dataUltimoLembreteEnviado;
    }

    public scheduling() {}

}
