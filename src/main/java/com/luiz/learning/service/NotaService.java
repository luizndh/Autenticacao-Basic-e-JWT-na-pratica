package com.luiz.learning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luiz.learning.dto.NotaDTO;
import com.luiz.learning.exception.NotaNaoEncontradaException;
import com.luiz.learning.model.Nota;
import com.luiz.learning.model.Usuario;
import com.luiz.learning.repository.NotaRepository;
import com.luiz.learning.repository.UsuarioRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly=true)
    public List<Nota> findAll() {
        Authentication auth = getAuthentication();

        if(isAdmin(auth)) {
            return this.repository.findAllWithUsuario();
        } else {
            Usuario usuario = getUsuarioLogado(auth);
            return this.repository.findByUsuario(usuario);
        }
    }

    @Transactional(readOnly=true)
    public Nota find(Long id) {
        Nota nota = this.repository.findById(id)
            .orElseThrow(() -> new NotaNaoEncontradaException(id));

        this.checarPermissao(nota, getAuthentication());
        return nota;
    }

    @Transactional
    public Nota save(NotaDTO nota) {
        Usuario usuario = getUsuarioLogado(getAuthentication());

        Nota novaNota = new Nota();
        novaNota.setTitulo(nota.titulo());
        novaNota.setConteudo(nota.conteudo());
        novaNota.setUsuario(usuario);

        return this.repository.save(novaNota);
    }

    @Transactional
    public void delete(Long id) {
        Nota nota = this.repository.findById(id)
            .orElseThrow(() -> new NotaNaoEncontradaException(id));

        checarPermissao(nota, getAuthentication());
        this.repository.deleteById(id);
    }

    @Transactional
    public Nota update(Long id, NotaDTO notaAtualizada) {
        Nota nota = this.repository.findById(id)
            .orElseThrow(() -> new NotaNaoEncontradaException(id));

        checarPermissao(nota, getAuthentication());

        nota.setConteudo(notaAtualizada.conteudo());
        nota.setTitulo(notaAtualizada.titulo());
        return this.repository.save(nota);
    }



    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(ga -> ga.getAuthority().equals("ADMIN"));
    }

    private Usuario getUsuarioLogado(Authentication auth) {
        String username = auth.getName();
        return this.usuarioRepository.findByNome(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário de nome " + username + " não encontrado"));
    }

    private void checarPermissao(Nota nota, Authentication auth) {
        if (isAdmin(auth)) {
            return;
        }

        Usuario usuario = getUsuarioLogado(auth);

        if(!nota.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("O usuário não tem acesso a essa nota.");
        }
    }
}
