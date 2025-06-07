package com.app.dto;

public class UtenteDiscoverDTO {
    
    private Long id;
    private String nome;
    private String bio;
    private String interessi;
    private String fotoProfilo;
    private String citta;
    private Integer eta;  // Calcolata dall'età
    
    // ========== COSTRUTTORI ==========
    public UtenteDiscoverDTO() {}
    
    public UtenteDiscoverDTO(Long id, String nome, String bio, String interessi, 
                           String fotoProfilo, String citta, Integer eta) {
        this.id = id;
        this.nome = nome;
        this.bio = bio;
        this.interessi = interessi;
        this.fotoProfilo = fotoProfilo;
        this.citta = citta;
        this.eta = eta;
    }
    
    // ========== GETTER E SETTER ==========
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
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
    
    public String getFotoProfilo() {
        return fotoProfilo;
    }
    
    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }
    
    public String getCitta() {
        return citta;
    }
    
    public void setCitta(String citta) {
        this.citta = citta;
    }
    
    public Integer getEta() {
        return eta;
    }
    
    public void setEta(Integer eta) {
        this.eta = eta;
    }
    
    @Override
    public String toString() {
        return "UtenteDiscoverDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", bio='" + bio + '\'' +
                ", citta='" + citta + '\'' +
                ", eta=" + eta +
                '}';
    }
}