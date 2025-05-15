package com.basebackend.base_backend.entities.DTO;

import java.time.Instant;

public class CommentDTO {
    private Long id;
    private String contenido;
    private Instant fecha;
    private Integer likes = 0;
    private Integer dislikes = 0;
    private Long feed_id;
    private Long autor_id;
    private String autor_username;
    private String autor_email;
    
    public CommentDTO() {
    }
    

    public CommentDTO(Long id, String contenido, Instant fecha, Integer likes, Integer dislikes, Long feed_id, Long autor_id,
            String autor_username, String autor_email) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.likes = likes;
        this.dislikes = dislikes;
        this.feed_id = feed_id;
        this.autor_id = autor_id;
        this.autor_username = autor_username;
        this.autor_email = autor_email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
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

    public Long getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(Long autor_id) {
        this.autor_id = autor_id;
    }

    public String getAutor_username() {
        return autor_username;
    }

    public void setAutor_username(String autor_username) {
        this.autor_username = autor_username;
    }

    public String getAutor_email() {
        return autor_email;
    }

    public void setAutor_email(String autor_email) {
        this.autor_email = autor_email;
    }
    
    public Long getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(Long feed_id) {
        this.feed_id = feed_id;
    }
}
