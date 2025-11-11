package com.luiz.learning.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luiz.learning.model.Role;
import com.luiz.learning.model.Usuario;
import com.luiz.learning.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeUsers(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // --- Crie o Usuário ADMIN ---
            Usuario admin = new Usuario();
            admin.setNome("admin");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            // --- Crie o Usuário COMUM ---
            Usuario user = new Usuario();
            user.setNome("user");
            user.setSenha(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);

            Usuario user2 = new Usuario();
            user2.setNome("user2");
            user2.setSenha(passwordEncoder.encode("user123"));
            user2.setRole(Role.USER);

            // Salva os dois no banco de dados
            usuarioRepository.saveAll(List.of(admin, user, user2));

            System.out.println("✅ Usuários 'admin' e 'user' criados com sucesso!");
        };
    }
}
