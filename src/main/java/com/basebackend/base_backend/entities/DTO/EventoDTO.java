package com.basebackend.base_backend.entities.DTO;

import java.time.Instant;
import java.util.List;

public class EventoDTO {

    private Long id;

    private String name;

    private String descripcion;

    private Long estado_id;
    
    private String estado_nombre;

    private Instant fCreacion;

    private Instant fInicio;

    private Instant fTopeInscripcion;

    private Instant fFin;

    private Long feed_id;

    private Long categoria_id;

    private String nombreCategoria;

    private String codigoVestimenta;

    private Boolean coste;

    private Integer dislikes;

    private Integer likes;

    private List<Long> usuarios;
    
    private List<Long> seguidores;
    
    private Integer limiteUsuarios;

    private String linkExterno;

    private String ubicacion;

    private Long usuario_id;

    private String userName;

    public EventoDTO() {
    }


    public EventoDTO(Long id, String name, String descripcion, Long estado_id, String estado_nombre,
            Instant fCreacion, Instant fInicio, Instant fFin, Instant fTopeInscripcion, Long feed_id,
            Long categoria_id, String nombreCategoria, String codigoVestimenta, Boolean coste, Integer dislikes,
            Integer likes, Integer limiteUsuarios, String linkExterno, String ubicacion, Long usuario_id,
            String userName) {
        this.id = id;
        this.name = name;
        this.descripcion = descripcion;
        this.estado_id = estado_id;
        this.estado_nombre = estado_nombre;
        this.fCreacion = fCreacion;
        this.fInicio = fInicio;
        this.fTopeInscripcion = fTopeInscripcion;
        this.fFin = fFin;
        this.feed_id = feed_id;
        this.categoria_id = categoria_id;
        this.nombreCategoria = nombreCategoria;
        this.codigoVestimenta = codigoVestimenta;
        this.coste = coste;
        this.dislikes = dislikes;
        this.likes = likes;
        this.limiteUsuarios = limiteUsuarios;
        this.linkExterno = linkExterno;
        this.ubicacion = ubicacion;
        this.usuario_id = usuario_id;
        this.userName = userName;
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

    public Long getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(Long estado_id) {
        this.estado_id = estado_id;
    }

    public String getEstado_nombre() {
        return estado_nombre;
    }

    public void setEstado_nombre(String estado_nombre) {
        this.estado_nombre = estado_nombre;
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

    public Instant getfTopeInscripcion() {
        return fTopeInscripcion;
    }

    public void setfTopeInscripcion(Instant fTopeInscripcion) {
        this.fTopeInscripcion = fTopeInscripcion;
    }

    public Instant getfFin() {
        return fFin;
    }

    public void setfFin(Instant fFin) {
        this.fFin = fFin;
    }

    public Long getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(Long feed_id) {
        this.feed_id = feed_id;
    }

    public Long getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Long categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getCodigoVestimenta() {
        return codigoVestimenta;
    }

    public void setCodigoVestimenta(String codigoVestimenta) {
        this.codigoVestimenta = codigoVestimenta;
    }

    public Boolean getCoste() {
        return coste;
    }

    public void setCoste(Boolean coste) {
        this.coste = coste;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
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

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Long> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Long> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Long> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<Long> seguidores) {
        this.seguidores = seguidores;
    }
}
