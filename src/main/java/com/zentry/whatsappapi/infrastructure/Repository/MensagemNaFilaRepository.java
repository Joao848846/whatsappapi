package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.scheduling.MensagemNaFila;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensagemNaFilaRepository extends MongoRepository<MensagemNaFila , String> {



    MensagemNaFila findBySchedulingId(String schedulingId);

    List<MensagemNaFila> findByStatusEnvioAndDataAgendamentoEnvioLessThanEqualOrderByDataAgendamentoEnvioAsc(String pendente, LocalDateTime now);

    MensagemNaFila findTopByTelefoneDestinoAndStatusEnvioOrderByDataEnvioRealizadoDesc(String telefoneDestino, String statusEnvio);

    MensagemNaFila findTopBySchedulingIdOrderByDataAgendamentoEnvioDesc(String schedulingId);

    MensagemNaFila findBySchedulingIdAndStatusEnvio(String schedulingId, String statusEnvio);

}
