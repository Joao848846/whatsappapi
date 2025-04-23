package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, String> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

}
