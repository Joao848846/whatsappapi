package com.zentry.whatsappapi.adapter.in.controller.scheduling;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zentry.whatsappapi.application.service.scheduling.CsvService;
import com.zentry.whatsappapi.domain.model.scheduling.scheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zentry.whatsappapi.infrastructure.Repository.schedulingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<scheduling> agendamentos = csvService.LerAgendamentos(file.getInputStream());
            System.out.println("Agendamentos lidos: " + agendamentos.size());
            return ResponseEntity.ok(agendamentos); // ou salvar no banco, ou retornar OK simples
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar CSV: " + e.getMessage());
        }
    }

    @GetMapping("/agendamentos")
    public ResponseEntity<List<scheduling>> listarAgendamentos() {
        List<scheduling> agendamentos = csvService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/agendamentos/telefone")
    public ResponseEntity<scheduling> buscarAgendamentoPorTelefone(@RequestParam String telefone) {
        scheduling agendamento = csvService.buscarAgendamentoPorTelefone(telefone);
        if (agendamento != null) {
            return ResponseEntity.ok(agendamento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/agendamentos/documento")
    public ResponseEntity<scheduling> buscarAgendamentoPorDocumento(@RequestParam String documento) {
        scheduling agendamento = csvService.buscarAgendamentoPorDocumento(documento);
        if (agendamento != null) {
            return ResponseEntity.ok(agendamento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/agendamentos/nome")
    public ResponseEntity<scheduling> buscarPorDocumento(@RequestParam String nome) {
        scheduling agendamento = csvService.buscarAgendamentoPorNome(nome);
        if (agendamento != null) {
            return ResponseEntity.ok(agendamento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/agendamentos/status_pagamento/count")
    public ResponseEntity<Integer> contarAgendamentosPorStatusPagamento(@RequestParam String status_pagamento) {
        List<scheduling> agendamentos = csvService.buscarAgendamentoPorStatusPagamento(status_pagamento);
        int quantidade = agendamentos.size();
        if (quantidade > 0) {
            return ResponseEntity.ok(quantidade); // Retorna a quantidade como Integer
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0); // Retorna 0 se nenhum for encontrado
        }
    }

    @GetMapping("/agendamentos/status_pagamento")
    public ResponseEntity<List<scheduling>> listarAgendamentosPorStatusPagamento(@RequestParam String status_pagamento) {
        List<scheduling> agendamentos = csvService.buscarAgendamentoPorStatusPagamento(status_pagamento);
        if (agendamentos != null && !agendamentos.isEmpty()) {
            return ResponseEntity.ok(agendamentos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/agendamentos/lembrete/count")
    public ResponseEntity<Integer> contarAgendamentosComLembreteEnviado(@RequestParam boolean lembrete_enviado) {
        List<scheduling> agendamentos = csvService.buscarPorLembreteEnviado(lembrete_enviado);
        int quantidade = agendamentos.size();
        if (quantidade > 0) {
            return ResponseEntity.ok(quantidade); // Retorna a quantidade como Integer
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0); // Retorna 0 se nenhum for encontrado
        }
    }

    @GetMapping("/estatisticas/pagamentos-por-status")
    public ResponseEntity<List<Map<String, Object>>> getEstatisticasPagamentosPorStatus() {
        List<scheduling> agendamentos = csvService.listarAgendamentos(); // Ou buscar com filtros de data se necessário

        Map<String, Long> contagemPorStatus = agendamentos.stream()
                .collect(Collectors.groupingBy(scheduling::getStatusPagamento, Collectors.counting()));
        System.out.println("Contagem por status PASSOU POR AQUI: " + contagemPorStatus);

        List<Map<String, Object>> resposta = contagemPorStatus.entrySet().stream()
                .map(entry -> (Map<String, Object>) Map.of("statusPagamento", entry.getKey(), "quantidade", (Object) entry.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

}