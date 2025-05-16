package com.zentry.whatsappapi.application.service.scheduling;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.zentry.whatsappapi.domain.model.scheduling.scheduling;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiMessageService {

    private final RestTemplate restTemplate;

    public ApiMessageService() {
        this.restTemplate = new RestTemplate();
    }

    // NOVO MÉTODO PARA ENVIAR COM TELEFONE E TEXTO
    public void enviarMensagem(String telefone, String mensagemTexto) {
        Map<String, String> body = new HashMap<>();
        body.put("number", telefone);
        body.put("text", mensagemTexto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        String endpoint = "http://localhost:3000/api/v1/message/sendText/Joao"; // SUBSTITUA AQUI

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);
            System.out.println("Mensagem enviada para " + telefone + ": " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para " + telefone + ": " + e.getMessage());
        }
    }

    // MÉTODO EXISTENTE QUE RECEBE 'scheduling' E CHAMA O NOVO MÉTODO
    public void enviarMensagem(scheduling agendamentos) {
        String mensagem = gerarMensagemPersonalizada(agendamentos);
        enviarMensagem(agendamentos.getTelefone(), mensagem); // CHAMA O NOVO MÉTODO
    }

    public String gerarMensagemPersonalizada(scheduling contato) {
        String status = contato.getStatusPagamento();
        LocalDate dataContrato = LocalDate.parse(contato.getData_contrato());
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd/MM"); // Formato para extrair apenas o dia
        String diaDoContrato = dataContrato.format(dayFormatter);

        String modelo = switch (status.toLowerCase()) {
            case "pendente" -> "Olá {{nome}}, sua mensalidade do valor está pendente desde o dia " + diaDoContrato + ". Por favor, regularize.";
            case "atrasado" -> "Olá {{nome}}, sua mensalidade está atrasada desde o dia " + diaDoContrato + ".";
            default -> "Olá {{nome}}, sua mensalidade se encontra paga.";
        };

        return modelo
                .replace("{{nome}}", contato.getNome());
    }
}