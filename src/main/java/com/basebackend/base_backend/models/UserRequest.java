package com.basebackend.base_backend.models;

import java.time.LocalDate;
import java.util.List;

import com.basebackend.base_backend.entities.enums.TipoDocumento;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest implements IUser {


    private String name;

    private String lastname;

    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    private String documento;

    private Long selfPreferencias;

    private LocalDate registro;

    private TipoDocumento tipoDocumento;

    // Списки для обновления связей с сущностями
    private List<Long> eventosCreados;   // ID событий для обновления
    private List<Long> eventosApuntados; // ID событий, на которые юзер записан
    private List<Long> eventosSeguidos;  // ID событий, которые юзер следит
    private List<Long> categorias;       // ID категорий, к которым относится юзер
    private List<Long> logros;           // ID логров юзера
    private List<Long> usuariosSeguidos; // ID пользователей, которых юзер подписал

    

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

    public List<Long> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Long> categorias) {
        this.categorias = categorias;
    }

    public List<Long> getLogros() {
        return logros;
    }

    public void setLogros(List<Long> logros) {
        this.logros = logros;
    }

    public List<Long> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setUsuariosSeguidos(List<Long> usuariosSeguidos) {
        this.usuariosSeguidos = usuariosSeguidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Long getSelfPreferencias() {
        return selfPreferencias;
    }
    public void setSelfPreferencias(Long selfPreferencias) {
        this.selfPreferencias = selfPreferencias;
    }

    public LocalDate getRegistro() {
        return registro;
    }

    public void setRegistro(LocalDate registro) {
        this.registro = registro;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    private boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    
}
