package com.luiz.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luiz.learning.model.Usuario;
import com.luiz.learning.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email).orElse(null);
    }
}
