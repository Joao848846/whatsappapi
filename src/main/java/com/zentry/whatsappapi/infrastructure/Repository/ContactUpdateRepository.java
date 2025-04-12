package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.ContactUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUpdateRepository extends MongoRepository<ContactUpdate, String> {
}
