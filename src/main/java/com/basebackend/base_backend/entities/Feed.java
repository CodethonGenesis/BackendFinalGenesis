package com.basebackend.base_backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feed_id_seq")
    @SequenceGenerator(name = "feed_id_seq", sequenceName = "feed_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "evento_id", unique = true)
    private Evento evento;

    public Feed() {
    }

    public Feed(Evento evento) {
        this.evento = evento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void addComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }
}