package com.basebackend.base_backend.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evento_id_seq")
    @SequenceGenerator(name = "evento_id_seq", sequenceName = "evento_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @Column(name = "fcreacion")
    private Instant fCreacion;

    @Column(name = "finicio")
    private Instant fInicio;

    @Column(name = "ffin")
    private Instant fFin;

    @Column(name = "ftopeinscripcion")
    private Instant fTopeInscripcion;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "categoria_id")
    @JsonBackReference
    private Categoria categoria;


    @OneToOne(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Feed feed;

    @JsonIgnoreProperties({ "eventosCreados", "handler", "hibernateLazyInitializer" })
    @ManyToMany(mappedBy = "eventosApuntados", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Usuario> usuarios = new ArrayList<>();

    @JsonIgnoreProperties({ "eventosSeguidos", "handler", "hibernateLazyInitializer" })
    @ManyToMany(mappedBy = "eventosSeguidos", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Usuario> seguidores = new ArrayList<>();

    @Column(name = "limiteusuarios")
    private Integer limiteUsuarios;

    @Column(name = "linkexterno")
    private String linkExterno;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "coste")
    private Boolean coste;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "dislikes")
    private Integer dislikes = 0;

    @Column(name = "codigovestimenta")
    private String codigoVestimenta;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({ "eventosCreados", "handler", "hibernateLazyInitializer" })
    private Usuario usuario;



    public Evento() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Instant getfCreacion() {
        return fCreacion;
    }

    public void setfCreacion(Instant fCreacion) {
        this.fCreacion = fCreacion;
    }

    public Instant getfInicio() {
        return fInicio;
    }

    public void setfInicio(Instant fInicio) {
        this.fInicio = fInicio;
    }

    public Instant getfFin() {
        return fFin;
    }

    public void setfFin(Instant fFin) {
        this.fFin = fFin;
    }

    public Instant getfTopeInscripcion() {
        return fTopeInscripcion;
    }

    public void setfTopeInscripcion(Instant fTopeInscripcion) {
        this.fTopeInscripcion = fTopeInscripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public Integer getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public void setLimiteUsuarios(Integer limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
    }

    public String getLinkExterno() {
        return linkExterno;
    }

    public void setLinkExterno(String linkExterno) {
        this.linkExterno = linkExterno;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Boolean getCoste() {
        return coste;
    }

    public void setCoste(Boolean coste) {
        this.coste = coste;
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

    public String getCodigoVestimenta() {
        return codigoVestimenta;
    }

    public void setCodigoVestimenta(String codigoVestimenta) {
        this.codigoVestimenta = codigoVestimenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



}