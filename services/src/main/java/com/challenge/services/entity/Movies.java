package com.challenge.services.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Builder
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private Long id;


    @Column(name = "mName")
    @NotBlank
    @Size(max = 25)
    private String mName;
    @Column(name = "stars")
    @NotBlank
    private int stars;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Movies(@NotBlank @Size(max = 25) String mName) {
        this.mName = mName;
    }

    public Movies(@NotBlank @Size(max = 25) String mName, @NotBlank int stars) {
        this.mName = mName;
        this.stars = stars;
    }

    public Movies() {
    }
}
