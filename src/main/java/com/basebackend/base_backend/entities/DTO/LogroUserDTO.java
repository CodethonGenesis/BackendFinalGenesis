package com.basebackend.base_backend.entities.DTO;

public class LogroUserDTO {
    private Long id;
    private String name;
    private String icono;
    private String descripcion;
    private UserSummaryDTO userSummaryDTO;

    public LogroUserDTO() {
    }

    public LogroUserDTO(Long id, String name, String icono, String descripcion, Long userId, String username, String email) {
        this.id = id;
        this.name = name;
        this.icono = icono;
        this.descripcion = descripcion;
        this.userSummaryDTO = new UserSummaryDTO(userId, username, email);
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    
}
