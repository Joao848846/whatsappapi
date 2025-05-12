package com.zentry.whatsappapi.application.service.scheduling;

import com.zentry.whatsappapi.application.service.scheduling.ApiMessageService;
import com.zentry.whatsappapi.infrastructure.Repository.schedulingRepository;
import com.zentry.whatsappapi.domain.model.scheduling.scheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulingService {

    private final schedulingRepository agendamentoRepository;
    private final ApiMessageService apiMessageService;

    public SchedulingService( schedulingRepository agendamentoRepository,
                             ApiMessageService apiMessageService) {
        this.agendamentoRepository = agendamentoRepository;
        this.apiMessageService = apiMessageService;
    }

    @Scheduled(fixedDelay = 120000) // a cada 15 minutos
    public void verificarContratosEVencimentos() {
        List<scheduling> agendamentos = agendamentoRepository.findAll();
        LocalDate hoje = LocalDate.now();

        for (scheduling contato : agendamentos) {
            System.out.println("Worker verificando agendamento ID: " + contato.getId() + ", Lembrete Enviado: " + contato.getLembreteEnviado() + ", Status Pagamento: " + contato.getStatus_pagamento()  +  "contato" + contato.getTelefone());


            if (!contato.getStatus_pagamento().equalsIgnoreCase("pendente") &&
                    !contato.getStatus_pagamento().equalsIgnoreCase("atrasado") &&
                    contato.getLembreteEnviado() != null && contato.getLembreteEnviado()) {
                System.out.println("Pulando agendamento pois já foi pago e/ou lembrete enviado.");
                continue; // pula se já estiver pago
            }

            // extrai dia de vencimento do contrato
            LocalDate dataContrato = LocalDate.parse(contato.getData_contrato());
            int diaVencimento = dataContrato.getDayOfMonth();

            if (hoje.getDayOfMonth() == diaVencimento || hoje.isEqual(dataContrato.minusDays(3))) {
                boolean podeEnviarLembrete = false;
                java.time.YearMonth mesAnoAtual = java.time.YearMonth.now();
                System.out.println("Dia de vencimento encontrado para o agendamento ID: " + contato.getId() +
                        ", dataUltimoLembreteEnviado: " + contato.getDataUltimoLembreteEnviado() +
                        ", lembreteEnviado: " + contato.getLembreteEnviado());
                if (contato.getDataUltimoLembreteEnviado() == null || !contato.getLembreteEnviado()) {
                    System.out.println("Condição para enviar lembrete ATIVADA (primeiro envio ou lembreteEnviado false) para ID: " + contato.getId());
                    podeEnviarLembrete = true;
                    // Se enviarmos agora, precisamos garantir que o webhook setará lembreteEnviado para true
                } else {
                    java.time.YearMonth mesAnoUltimoEnvio = java.time.YearMonth.from(contato.getDataUltimoLembreteEnviado());
                    if (!mesAnoUltimoEnvio.equals(mesAnoAtual) && contato.getLembreteEnviado()) {
                        System.out.println("Condição para enviar lembrete ATIVADA (mês diferente e lembreteEnviado true) para ID: " + contato.getId());
                        podeEnviarLembrete = true;
                        // Se enviarmos agora, precisamos garantir que o webhook atualizará a data e manterá lembreteEnviado como true
                    }
                }
                if (podeEnviarLembrete) {
                    apiMessageService.enviarMensagem(contato);
                    // O webhook irá atualizar o dataUltimoLembreteEnviado e lembreteEnviado
                    try {
                        Thread.sleep(10000); // espera 10 segundos entre cada envio
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
