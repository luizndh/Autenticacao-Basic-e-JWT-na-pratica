package com.luiz.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.luiz.learning.model.Nota;
import com.luiz.learning.model.Usuario;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByUsuario(Usuario usuario);

    @Query("SELECT n FROM Nota n JOIN FETCH n.usuario")
    List<Nota> findAllWithUsuario();
}
