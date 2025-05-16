package com.zentry.whatsappapi.application.service.scheduling;

import com.zentry.whatsappapi.application.service.scheduling.ApiMessageService;
import com.zentry.whatsappapi.infrastructure.Repository.schedulingRepository;
import com.zentry.whatsappapi.infrastructure.Repository.MensagemNaFilaRepository;
import com.zentry.whatsappapi.domain.model.scheduling.scheduling;
import com.zentry.whatsappapi.domain.model.scheduling.MensagemNaFila;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulingService {

    private final schedulingRepository agendamentoRepository;
    private final ApiMessageService apiMessageService;
    private final MensagemNaFilaRepository mensagemNaFilaRepository;

    public SchedulingService( schedulingRepository agendamentoRepository,
                             ApiMessageService apiMessageService, MensagemNaFilaRepository mensagemNaFilaRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.apiMessageService = apiMessageService;
        this.mensagemNaFilaRepository = mensagemNaFilaRepository;
    }

    @Scheduled(fixedDelay = 120000) // 2 minutos
    public void verificarContratosEVencimentos() {
        List<scheduling> agendamentos = agendamentoRepository.findAll();
        LocalDate hoje = LocalDate.now();

        for (scheduling contato : agendamentos) {
            System.out.println("Worker verificando agendamento ID: " + contato.getId() + ", Lembrete Enviado: " + contato.getLembreteEnviado() + ", Status Pagamento: " + contato.getStatusPagamento()  +  "contato" + contato.getTelefone());


            if (!contato.getStatusPagamento().equalsIgnoreCase("pendente") &&
                    !contato.getStatusPagamento().equalsIgnoreCase("atrasado") &&
                    contato.getLembreteEnviado() != null && contato.getLembreteEnviado()) {
                System.out.println("Pulando agendamento pois já foi pago e/ou lembrete enviado.");
                continue; // pula se já estiver pago
            }

            // extrai dia de vencimento do contrato
            LocalDate dataContrato = LocalDate.parse(contato.getData_contrato());
            int diaVencimento = dataContrato.getDayOfMonth();

            if (hoje.getDayOfMonth() == diaVencimento || hoje.isEqual(dataContrato.minusDays(3)) || hoje.isEqual(dataContrato.plusDays(2))) {
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
                        // Se enviarmos agora,  garantir que o webhook atualizará a data e manterá lembreteEnviado como true
                    }
                }
                if (podeEnviarLembrete) {

                    System.out.println("Enviando lembrete para o agendamento ID: " + contato.getId() + ", telefone: " + contato.getTelefone());
                    MensagemNaFila mensagemPendenteExistente = mensagemNaFilaRepository.findBySchedulingIdAndStatusEnvio(contato.getId(), "PENDENTE");
                    System.out.println("Mensagem PENDENTE existente: " + mensagemPendenteExistente);

                    if (mensagemPendenteExistente == null) {
                        // *** LÓGICA DA FILA ***
                        String mensagemParaEnviar = apiMessageService.gerarMensagemPersonalizada(contato);
                        LocalDateTime horarioAgendamento = LocalDateTime.now().plusMinutes(3); // 3 minutos no futuro

                        MensagemNaFila mensagemNaFila = new MensagemNaFila(
                                contato.getId(),
                                contato.getTelefone(),
                                mensagemParaEnviar,
                                horarioAgendamento
                        );
                        mensagemNaFila.setStatusEnvio("PENDENTE"); // Garanta que o status seja "PENDENTE" na criação
                        mensagemNaFilaRepository.save(mensagemNaFila);
                        System.out.println("Nova mensagem ENFILEIRADA para o scheduling ID: " + contato.getId());
                    } else {
                        System.out.println("Mensagem PENDENTE já existe na fila para o scheduling ID: " + contato.getId() + ". Não enfileirando novamente.");
                    }
                }
            }
        }
    }
}
