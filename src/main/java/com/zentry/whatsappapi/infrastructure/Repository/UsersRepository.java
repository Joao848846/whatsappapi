package com.zentry.whatsappapi.infrastructure.Repository;

import com.zentry.whatsappapi.domain.model.users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<Users, String> {


        Optional<Users> findByUsername(String username);

}
