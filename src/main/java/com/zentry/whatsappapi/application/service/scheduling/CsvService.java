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
                    primeiraLinha = false;
                    continue;
                }

                scheduling agendamento = new scheduling();
                agendamento.setNome(linha[0]);
                agendamento.setTelefone(linha[1]);
                agendamento.setDocumento(linha[2]);
                agendamento.setTipo_contrato(linha[3]);
                agendamento.setData_contrato(String.valueOf(LocalDate.parse(linha[4], dateFormatter)));
                agendamento.setStatus_pagamento(linha[5]);
                agendamento.setValor_mensalidade(linha[6]);
                agendamento.setLembreteEnviado(false);
                agendamento.setDataUltimoLembreteEnviado(null);

                // Verifica se já existe um agendamento com este documento
                scheduling existente = agendamentoRepository.findByDocumento(agendamento.getDocumento());

                if (existente == null) {
                    // Se não existe, salva o novo agendamento e adiciona à lista
                    agendamentos.add(agendamento);
                    System.out.println("Agendamento adicionado: " + agendamento.getDocumento() + "UltimoLembrete: " + agendamento.getDataUltimoLembreteEnviado());
                    agendamentoRepository.save(agendamento);
                    System.out.println("Agendamento salvo: " + agendamento.getDocumento() + "UltimoLembrete: " + agendamento.getDataUltimoLembreteEnviado());

                } else {
                    // Se já existe, loga a duplicidade (ou outra ação que você preferir)
                    System.out.println("Duplicidade encontrada para o documento: " + agendamento.getDocumento());
                    // Por enquanto, não adicionamos à lista 'agendamentos'

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return agendamentos;
    }
}
