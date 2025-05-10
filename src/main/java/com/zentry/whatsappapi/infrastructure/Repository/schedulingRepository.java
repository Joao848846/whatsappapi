package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.scheduling.scheduling;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface schedulingRepository extends  MongoRepository<scheduling, String> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Por exemplo: List<scheduling> findByNome(String nome);
}
