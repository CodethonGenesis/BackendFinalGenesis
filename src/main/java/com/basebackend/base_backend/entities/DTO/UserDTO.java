package com.basebackend.base_backend.entities.DTO;

import java.time.LocalDate;
import java.util.List;

import com.basebackend.base_backend.entities.Preferencias;
import com.basebackend.base_backend.entities.enums.TipoDocumento;



public class UserDTO {
    private Long id;
    private String userName;
    private LocalDate registro;
    private String email;
    private String name;
    private String lastName;

    private TipoDocumento tipoDocumento;

    private String documento;
    private Preferencias selfPreferencias;
    private List<CommentSummaryDTO> comentarios;
    private List<LogroUserDTO> logros;
    private List<CategoryUserDTO> categorias;

    public List<LogroUserDTO> getLogros() {
        return logros;
    }

    public void setLogros(List<LogroUserDTO> logros) {
        this.logros = logros;
    }

    public List<CategoryUserDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoryUserDTO> categorias) {
        this.categorias = categorias;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public List<CommentSummaryDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<CommentSummaryDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDate getRegistro() {
        return registro;
    }

    public void setRegistro(LocalDate registro) {
        this.registro = registro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Preferencias getSelfPreferencias() {
        return selfPreferencias;
    }

    public void setSelfPreferencias(Preferencias selfPreferencias) {
        this.selfPreferencias = selfPreferencias;
    }

}
