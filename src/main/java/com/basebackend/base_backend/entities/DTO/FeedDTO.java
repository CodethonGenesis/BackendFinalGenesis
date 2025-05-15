package com.basebackend.base_backend.entities.DTO;

import java.util.List;

public class FeedDTO {
    private Long id;
    private Long eventoId;
    private String eventoName;
    private List<CommentDTO> comentarios;

    public FeedDTO() {
    }
    
    public FeedDTO(Long id, Long eventoId, String eventoName) {
        this.id = id;
        this.eventoId = eventoId;
        this.eventoName = eventoName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getEventoName() {
        return eventoName;
    }

    public void setEventoName(String eventoName) {
        this.eventoName = eventoName;
    }

    public List<CommentDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<CommentDTO> comentarios) {
        this.comentarios = comentarios;
    }

    
}
