package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.SendMessages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendMessageRepository  extends  MongoRepository<SendMessages, String> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo: List<SendMessages> findByStatus(String status);
}
