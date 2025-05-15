package com.basebackend.base_backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "logro")
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logro_id_seq")
    @SequenceGenerator(name = "logro_id_seq", sequenceName = "logro_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "icono")
    private String icono;

    @JsonIgnoreProperties({"logros", "handler", "hibernateLazyInitializer"})
    @ManyToMany(mappedBy = "logros")
    private List<Usuario> usuarios = new ArrayList<>();

    public Logro() {
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
