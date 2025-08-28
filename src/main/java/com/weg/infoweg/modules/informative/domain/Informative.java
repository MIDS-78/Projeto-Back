package com.weg.infoweg.modules.informative.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table (name="Informative")
public class Informative{


    @Id
    @GeneratedValue
    @Column(name="id_informative")
    private UUID id;

    @Column(name="title",nullable = false, length = 150)
    private String title;

    @Column(name="content",nullable = false, length = 500)
    private String description;

    @Lob
    @Column(name="fk_id_image",nullable = false)
    private byte[] image;

    public Informative(){

    }

    public Informative(UUID id, String title, String description, byte[] image) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}