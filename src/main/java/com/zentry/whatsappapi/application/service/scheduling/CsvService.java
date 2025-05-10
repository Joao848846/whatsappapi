package com.zentry.whatsappapi.application.service.scheduling;

import com.opencsv.CSVReader;
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

    public List<scheduling> LerAgendamentos(InputStream csvInputStream) {

        List<scheduling> agendamentos = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (CSVReader reader = new CSVReader(new InputStreamReader(csvInputStream))) {
            String[] linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readNext()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pula o cabeçalho
                    continue;
                }

                scheduling agendamento = new scheduling();
                agendamento.setNomeCliente(linha[0]);
                agendamento.setTelefoneCliente(linha[1]);
                agendamento.setDataConsulta(String.valueOf(LocalDate.parse(linha[2], dateFormatter)));
                agendamento.setHoraConsulta(String.valueOf(LocalTime.parse(linha[3], timeFormatter)));
                agendamento.setDoutor(linha[4]);

                agendamentos.add(agendamento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return agendamentos;
    }
}
