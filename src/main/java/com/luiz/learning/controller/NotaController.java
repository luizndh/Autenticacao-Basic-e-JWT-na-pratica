package com.luiz.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.luiz.learning.model.Nota;
import com.luiz.learning.service.NotaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luiz.learning.dto.NotaDTO;

@RestController
@RequestMapping("/api")
public class NotaController {

    @Autowired
    private NotaService service;

    @GetMapping("/notas")
    public List<Nota> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/notas/{id}")
    public Nota findAll(@PathVariable Long id) {
        return this.service.find(id);
    }

    @PostMapping("/notas")
    public Nota save(@RequestBody @Valid NotaDTO novaNota) {
        return this.service.save(novaNota);
    }

    @PutMapping("/notas/{id}")
    public Nota update(@PathVariable Long id, @RequestBody @Valid NotaDTO notaAtualizada) {
        return this.service.update(id, notaAtualizada);
    }

    @DeleteMapping("/notas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}