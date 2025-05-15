package com.basebackend.base_backend.entities.DTO;

import java.time.Instant;

public class CommentSummaryDTO {
    private Long id;
    private String content;
    private Instant fecha;
    
    public CommentSummaryDTO() {
    }

    public CommentSummaryDTO(Long id, String content, Instant fecha) {
        this.id = id;
        this.content = content;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "CommentSummaryDTO [id=" + id + ", content=" + content + ", fecha=" + fecha + "]";
    }
}
