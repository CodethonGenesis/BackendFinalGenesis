package com.basebackend.base_backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_id_seq")
    @SequenceGenerator(name = "categoria_id_seq", sequenceName = "categoria_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Evento> eventos = new ArrayList<>();

    @JsonIgnoreProperties({ "categorias", "handler", "hibernateLazyInitializer" })
    @ManyToMany(mappedBy = "categorias")
    private List<Usuario> seguidores = new ArrayList<>();

    public Categoria() {
    }

    public Categoria(String name, String descripcion) {
        this.name = name;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }
}