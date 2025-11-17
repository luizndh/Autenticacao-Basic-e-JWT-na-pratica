package com.luiz.learning.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.luiz.learning.model.Role;
import com.luiz.learning.model.Usuario;
import com.luiz.learning.repository.UsuarioRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Pega o usuário que veio do provedor (Google/Github)
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Verifica se o usuário já existe no banco, senão cria
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setEmail(email);
                    novoUsuario.setRole(Role.USER);
                    novoUsuario.setSenha("");
                    return usuarioRepository.save(novoUsuario);
                });

        var authorities = List.of(new SimpleGrantedAuthority(usuario.getRole().toString()));

        // Autenticação para gerar o token
        Authentication authenticationInterna = new UsernamePasswordAuthenticationToken(
                usuario.getEmail(),
                null,
                authorities
        );

        String jwtToken = jwtService.createToken(authenticationInterna);

        // Retorna o token
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");
        response.getWriter().flush();
    }

}
