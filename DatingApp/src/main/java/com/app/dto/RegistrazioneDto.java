package com.app.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrazioneDto {

    private String nome;
    private String email;
    private String password;
    private String genere;
    @JsonProperty("data_nascita")
    private LocalDate dataNascita;
    private String bio;
    private String interessi;
    private String citta;
    private Double latitudine;
    private Double longitudine;

    public RegistrazioneDto() {
    }

    public RegistrazioneDto(String nome, String email, String password, String genere,
                            LocalDate dataNascita, String bio, String interessi,
                            String citta, Double latitudine, Double longitudine) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.genere = genere;
        this.dataNascita = dataNascita;
        this.bio = bio;
        this.interessi = interessi;
        this.citta = citta;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    // ========== GETTER & SETTER ==========

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInteressi() {
        return interessi;
    }

    public void setInteressi(String interessi) {
        this.interessi = interessi;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }
}
