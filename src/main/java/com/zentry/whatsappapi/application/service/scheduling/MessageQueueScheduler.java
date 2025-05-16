package com.zentry.whatsappapi.application.service.scheduling;

import com.zentry.whatsappapi.application.service.scheduling.ApiMessageService;
import com.zentry.whatsappapi.domain.model.scheduling.MensagemNaFila;
import com.zentry.whatsappapi.infrastructure.Repository.MensagemNaFilaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageQueueScheduler {

    private final MensagemNaFilaRepository mensagemNaFilaRepository;
    private final ApiMessageService apiMessageService;

    public MessageQueueScheduler(MensagemNaFilaRepository mensagemNaFilaRepository, ApiMessageService apiMessageService) {
        this.mensagemNaFilaRepository = mensagemNaFilaRepository;
        this.apiMessageService = apiMessageService;
    }

    @Scheduled(fixedDelay = 120000) // Executar a cada minuto (ajuste conforme necessário)
    public void processarFilaDeMensagens() {
        LocalDateTime now = LocalDateTime.now();
        List<MensagemNaFila> mensagensPendentes = mensagemNaFilaRepository.findByStatusEnvioAndDataAgendamentoEnvioLessThanEqualOrderByDataAgendamentoEnvioAsc("PENDENTE", now);

        for (MensagemNaFila mensagem : mensagensPendentes) {
            System.out.println("Processando mensagem da fila: " + mensagem.getId() + " para " + mensagem.getTelefoneDestino() + " agendada para " + mensagem.getDataAgendamentoEnvio());
            try {
                // *** ENVIAR A MENSAGEM USANDO apiMessageService ***
                apiMessageService.enviarMensagem(mensagem.getTelefoneDestino(), mensagem.getMensagem());

                // *** ATUALIZAR O STATUS DA MENSAGEM NA FILA PARA "ENVIADO" ***
                mensagem.setStatusEnvio("ENVIADO");
                mensagem.setDataEnvioRealizado(LocalDateTime.now());
                mensagemNaFilaRepository.save(mensagem);
                System.out.println("Mensagem enviada e status atualizado para ENVIADO: " + mensagem.getId());

            } catch (Exception e) {
                // *** TRATAR FALHAS NO ENVIO ***
                mensagem.setStatusEnvio("FALHA");
                mensagem.setDetalhesFalha(e.getMessage()); // Armazenar detalhes da falha
                mensagemNaFilaRepository.save(mensagem);
                System.err.println("Erro ao enviar mensagem " + mensagem.getId() + ": " + e.getMessage());
            }
        }
    }
}