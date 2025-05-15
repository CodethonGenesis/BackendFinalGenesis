package com.basebackend.base_backend.entities;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comentario_id_seq")
    @SequenceGenerator(name = "comentario_id_seq", sequenceName = "comentario_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;
    
    @Column(name = "fecha", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant fecha;
    
    @Column(name = "contenido", nullable = false, length = 1000)
    private String contenido;
    
    @Column(name = "likes")
    private Integer likes = 0;
    
    @Column(name = "dislikes")
    private Integer dislikes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    public Comentario() {
    }    

    public Comentario(Long id, Usuario autor, Instant fecha, String contenido, Integer likes, Integer dislikes,
            Feed feed) {
        this.id = id;
        this.autor = autor;
        this.fecha = fecha;
        this.contenido = contenido;
        this.likes = likes;
        this.dislikes = dislikes;
        this.feed = feed;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
