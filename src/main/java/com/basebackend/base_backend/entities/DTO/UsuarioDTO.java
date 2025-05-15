package com.basebackend.base_backend.entities.DTO;

import java.time.LocalDate;
import java.util.List;

import com.basebackend.base_backend.entities.enums.Idioma;
import com.basebackend.base_backend.entities.enums.Tema;
import com.basebackend.base_backend.entities.enums.TipoDocumento;

public class UsuarioDTO {

    private Long id;
    private String userName;
    private LocalDate registro;
    private String email;
    private String name;
    private String lastName;
    private TipoDocumento tipoDocumento;
    private String documento;
    private PreferenciasDTO selfPreferencias;
    private List<Long> eventosCreados;
    private List<Long> eventosApuntados;
    private List<Long> eventosSeguidos;
    //private List<Long> logros;
    private List<Long> usuariosSeguidos;
    private List<Long> seguidores;
    private List<Long> roles;
    private List<Long> logros;
    private List<Long> comentarios;
    private List<Long> categorias;

    record PreferenciasDTO(Long id, Boolean daltonico, String tema, Integer textSize, String idioma, Boolean discapacidadVisual) {

    }

    public UsuarioDTO(Long id, String userName, LocalDate registro, String email, String name, String lastName,
            TipoDocumento tipoDocumento, String documento, Long preferenciasId, Boolean daltonico,
            Tema tema, Integer textSize, Idioma idioma, Boolean discapacidadVisual) {
        this.id = id;
        this.userName = userName;
        this.registro = registro;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.selfPreferencias = new PreferenciasDTO(preferenciasId, daltonico, (tema != null && tema.name() != null) ? tema.name() : "CLARO", textSize, (idioma != null) ? idioma.name() : "ESPANOL",
                discapacidadVisual);
    }

    public UsuarioDTO() {
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

    public LocalDate getRegistro() {
        return registro;
    }

    public void setRegistro(LocalDate registro) {
        this.registro = registro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public PreferenciasDTO getSelfPreferencias() {
        return selfPreferencias;
    }

    public void setSelfPreferencias(PreferenciasDTO selfPreferencias) {
        this.selfPreferencias = selfPreferencias;
    }

    public List<Long> getEventosCreados() {
        return eventosCreados;
    }

    public void setEventosCreados(List<Long> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

    public List<Long> getEventosApuntados() {
        return eventosApuntados;
    }

    public void setEventosApuntados(List<Long> eventosApuntados) {
        this.eventosApuntados = eventosApuntados;
    }

    public List<Long> getEventosSeguidos() {
        return eventosSeguidos;
    }

    public void setEventosSeguidos(List<Long> eventosSeguidos) {
        this.eventosSeguidos = eventosSeguidos;
    }

    public List<Long> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setUsuariosSeguidos(List<Long> usuariosSeguidos) {
        this.usuariosSeguidos = usuariosSeguidos;
    }

    public List<Long> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Long> seguidores) {
        this.seguidores = seguidores;
    }

    public List<Long> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Long> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Long> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Long> categorias) {
        this.categorias = categorias;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public List<Long> getLogros() {
        return logros;
    }

    public void setLogros(List<Long> logros) {
        this.logros = logros;
    }

    

}
