package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.scheduling.scheduling;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface schedulingRepository extends  MongoRepository<scheduling, String> {

    scheduling findByDocumento(String documento);

    scheduling findByTelefone(String telefone);

    scheduling findByNome(String nome);

    List<scheduling> findByStatusPagamento(String statusPagamento);

    List<scheduling> findByLembreteEnviado(boolean lembreteEnviado);


}
