package com.basebackend.base_backend.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.basebackend.base_backend.entities.enums.TipoDocumento;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    @SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    @NotBlank
    private String userName;

    @Column(name = "registro")
    private LocalDate registro;

    @Column(name = "password", nullable = false, length = 100)
    @NotBlank
    @Size(min = 5)
    private String password;

    @Column(name = "email", length = 100)
    @Email
    private String email;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "lastname", length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "documento", length = 20)
    private String documento;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "preferencias_id")
    private Preferencias selfPreferencias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventosCreados = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_evento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "evento_id"}))
    private List<Evento> eventosApuntados = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_evento_seguido",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "evento_id"}))
    private List<Evento> eventosSeguidos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_logro",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "logro_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "logro_id"}))
    private List<Logro> logros = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_categoria",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "categoria_id"}))
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_seguido",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "seguido_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "seguido_id"}))
    private List<Usuario> usuariosSeguidos = new ArrayList<>();

    @ManyToMany(mappedBy = "usuariosSeguidos")
    private List<Usuario> seguidores = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "rol_id"}))
    private List<Rol> roles = new ArrayList<>();

    public Usuario() {
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Preferencias getSelfPreferencias() {
        return selfPreferencias;
    }

    public void setSelfPreferencias(Preferencias selfPreferencias) {
        this.selfPreferencias = selfPreferencias;
    }

    public List<Evento> getEventosCreados() {
        return eventosCreados;
    }

    public void setEventosCreados(List<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

    public List<Evento> getEventosApuntados() {
        return eventosApuntados;
    }

    public void setEventosApuntados(List<Evento> eventosApuntados) {
        this.eventosApuntados = eventosApuntados;
    }

    public List<Evento> getEventosSeguidos() {
        return eventosSeguidos;
    }

    public void setEventosSeguidos(List<Evento> eventosSeguidos) {
        this.eventosSeguidos = eventosSeguidos;
    }

    public List<Logro> getLogros() {
        return logros;
    }

    public void setLogros(List<Logro> logros) {
        this.logros = logros;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Usuario> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setUsuariosSeguidos(List<Usuario> usuariosSeguidos) {
        this.usuariosSeguidos = usuariosSeguidos;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}