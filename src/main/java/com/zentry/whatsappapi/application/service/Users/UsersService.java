package com.zentry.whatsappapi.application.service.Users;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.zentry.whatsappapi.domain.model.users.Users;
import com.zentry.whatsappapi.infrastructure.Repository.UsersRepository;
import com.zentry.whatsappapi.adapter.in.controller.Users.dto.UsersDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersDTO criarUsuario(UsersDTO usersDTO) {
        try {

            String senhaCriptografada = passwordEncoder.encode(usersDTO.getSenha());

            Users usuario = new Users(
                    usersDTO.getId(),
                    usersDTO.getNome(),
                    usersDTO.getNascimento(),
                    usersDTO.getEmail(),
                    usersDTO.getTelefone(),
                    senhaCriptografada,
                    usersDTO.getCpf(),
                    usersDTO.getAtivo(),
                    usersDTO.getModos(),
                    usersDTO.getTipo(),
                    usersDTO.getUsername(),
                    usersDTO.getEmpresa()

            );
            Users usuarioCriado = usersRepository.save(usuario);
            return new UsersDTO(
                    usuarioCriado.getId(),
                    usuarioCriado.getNome(),
                    usuarioCriado.getNascimento(),
                    usuarioCriado.getEmail(),
                    usuarioCriado.getTelefone(),
                    null,
                    usuarioCriado.getCpf(),
                    usuarioCriado.getAtivo(),
                    usuarioCriado.getModos(),
                    usuarioCriado.getTipo(),
                    usuarioCriado.getUsername(),
                    usuarioCriado.getEmpresa()

            );
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public List<Users>ListarUsuarios() {
        return usersRepository.findAll();
    }
}
