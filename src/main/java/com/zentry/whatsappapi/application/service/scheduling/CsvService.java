package com.zentry.whatsappapi.application.service.scheduling;

import com.opencsv.CSVReader;
import com.zentry.whatsappapi.infrastructure.Repository.schedulingRepository;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.zentry.whatsappapi.domain.model.scheduling.scheduling;

@Service
public class CsvService {

    private final schedulingRepository agendamentoRepository;

    public CsvService(schedulingRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<scheduling> LerAgendamentos(InputStream csvInputStream) {

        List<scheduling> agendamentos = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        try (CSVReader reader = new CSVReader(new InputStreamReader(csvInputStream))) {
            String[] linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readNext()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pula o cabeçalho
                    continue;
                }

                scheduling agendamento = new scheduling();
                agendamento.setNome(linha[0]);
                agendamento.setTelefone(linha[1]);
                agendamento.setTipo_contrato(linha[2]);
                agendamento.setData_contrato(String.valueOf(LocalDate.parse(linha[3], dateFormatter)));
                agendamento.setStatus_pagamento(linha[4]);
                agendamento.setValor_mensalidade(linha[5]);


                agendamentos.add(agendamento);
                // Salva o agendamento no banco de dados
                agendamentoRepository.save(agendamento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return agendamentos;
    }
}
