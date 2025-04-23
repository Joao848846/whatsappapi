package com.zentry.whatsappapi.application.service.Users;

import com.zentry.whatsappapi.domain.model.users.Users;
import com.zentry.whatsappapi.infrastructure.Repository.UsersRepository;
import com.zentry.whatsappapi.adapter.in.controller.Users.dto.UsersDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersDTO criarUsuario(UsersDTO usersDTO) {
        try {
            Users usuario = new Users(
                    usersDTO.getId(),
                    usersDTO.getNome(),
                    usersDTO.getNascimento(),
                    usersDTO.getEmail(),
                    usersDTO.getTelefone(),
                    usersDTO.getSenha(),
                    usersDTO.getCpf(),
                    usersDTO.getAtivo()

            );
            Users usuarioCriado = usersRepository.save(usuario);
            return new UsersDTO(
                    usuarioCriado.getId(),
                    usuarioCriado.getNome(),
                    usuarioCriado.getNascimento(),
                    usuarioCriado.getEmail(),
                    usuarioCriado.getTelefone(),
                    usuarioCriado.getSenha(),
                    usuarioCriado.getCpf(),
                    usuarioCriado.getAtivo()
            );
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }
}