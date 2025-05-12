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
            System.out.println("Worker verificando agendamento ID: " + contato.getId() + ", Lembrete Enviado: " + contato.getLembreteEnviado() + ", Status Pagamento: " + contato.getStatus_pagamento()); // ADICIONE ESTE LOG


            if (!contato.getStatus_pagamento().equalsIgnoreCase("pendente") &&
                    !contato.getStatus_pagamento().equalsIgnoreCase("atrasado") &&
                    contato.getLembreteEnviado() != null && contato.getLembreteEnviado()) {
                System.out.println("Pulando agendamento pois já foi pago e/ou lembrete enviado.");
                continue; // pula se já estiver pago
            }

            // extrai dia de vencimento do contrato
            LocalDate dataContrato = LocalDate.parse(contato.getData_contrato());
            int diaVencimento = dataContrato.getDayOfMonth();

            if (hoje.getDayOfMonth() == diaVencimento && (contato.getLembreteEnviado() == null || !contato.getLembreteEnviado())) {
                apiMessageService.enviarMensagem(contato);

                try {
                    Thread.sleep(10000); // espera 3 segundos entre cada envio
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
