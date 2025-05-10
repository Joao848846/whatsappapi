package com.zentry.whatsappapi.adapter.in.controller.scheduling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zentry.whatsappapi.application.service.scheduling.CsvService;
import com.zentry.whatsappapi.domain.model.scheduling.scheduling;

import java.util.List;

@RestController
@RequestMapping("/api/v1/csv")
public class CsvController  {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<scheduling> agendamentos = csvService.LerAgendamentos(file.getInputStream());
            return ResponseEntity.ok(agendamentos); // ou salvar no banco, ou retornar OK simples
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar CSV: " + e.getMessage());
        }
    }
}