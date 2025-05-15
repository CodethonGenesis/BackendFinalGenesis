package com.basebackend.base_backend.entities;

import com.basebackend.base_backend.entities.enums.Idioma;
import com.basebackend.base_backend.entities.enums.Tema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "preferencias")
public class Preferencias {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preferencias_id_seq")
    @SequenceGenerator(name = "preferencias_id_seq", sequenceName = "preferencias_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "daltonico")
    private Boolean daltonico;

    @Enumerated(EnumType.STRING)
    @Column(name = "tema")
    private Tema tema;

    @Column(name = "text_size")
    private Integer textSize;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "idioma")
    private Idioma idioma;

    @Column(name = "discapacidad_visual")
    private Boolean discapacidadVisual;

    @OneToOne(mappedBy = "selfPreferencias")
    private Usuario usuario;

    public Preferencias() {
    }
    
    public Preferencias(Boolean daltonico, Tema tema, Integer textSize, Idioma idioma, Boolean discapacidadVisual) {
        this.daltonico = daltonico;
        this.tema = tema;
        this.textSize = textSize;
        this.idioma = idioma;
        this.discapacidadVisual = discapacidadVisual;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDaltonico() {
        return daltonico;
    }

    public void setDaltonico(Boolean daltonico) {
        this.daltonico = daltonico;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer textSize) {
        this.textSize = textSize;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Boolean getDiscapacidadVisual() {
        return discapacidadVisual;
    }

    public void setDiscapacidadVisual(Boolean discapacidadVisual) {
        this.discapacidadVisual = discapacidadVisual;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
