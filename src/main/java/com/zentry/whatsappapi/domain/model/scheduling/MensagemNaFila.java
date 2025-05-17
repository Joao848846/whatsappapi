package com.zentry.whatsappapi.domain.model.scheduling;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "mensagens_na_fila")
public class MensagemNaFila {

    @Id
    private String id;
    private String schedulingId; // ID do agendamento original
    private String telefoneDestino;
    private String mensagem;
    private LocalDateTime dataAgendamentoEnvio;
    private LocalDateTime dataEnvioRealizado;
    private String statusEnvio; // Ex: "PENDENTE", "ENVIADO", "ENTREGUE", "FALHA"
    private String detalhesFalha;

    // Construtores, getters e setters

    public MensagemNaFila() {
    }

    public MensagemNaFila(String schedulingId, String telefoneDestino, String mensagem, LocalDateTime dataAgendamentoEnvio) {
        this.schedulingId = schedulingId;
        this.telefoneDestino = telefoneDestino;
        this.mensagem = mensagem;
        this.dataAgendamentoEnvio = dataAgendamentoEnvio;
        this.statusEnvio = "PENDENTE";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(String schedulingId) {
        this.schedulingId = schedulingId;
    }

    public String getTelefoneDestino() {
        return telefoneDestino;
    }

    public void setTelefoneDestino(String telefoneDestino) {
        this.telefoneDestino = telefoneDestino;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataAgendamentoEnvio() {
        return dataAgendamentoEnvio;
    }

    public void setDataAgendamentoEnvio(LocalDateTime dataAgendamentoEnvio) {
        this.dataAgendamentoEnvio = dataAgendamentoEnvio;
    }

    public LocalDateTime getDataEnvioRealizado() {
        return dataEnvioRealizado;
    }

    public void setDataEnvioRealizado(LocalDateTime dataEnvioRealizado) {
        this.dataEnvioRealizado = dataEnvioRealizado;
    }

    public String getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(String statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public String getDetalhesFalha() {
        return detalhesFalha;
    }

    public void setDetalhesFalha(String detalhesFalha) {
        this.detalhesFalha = detalhesFalha;
    }
}