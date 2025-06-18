package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
public class UtenteDiscoverDTO {
	@Schema(description = "ID eta dell'user", example = "12")
    private Long id;
	
	@Schema(description = "nome dell'user", example = "marco")
    private String nome;
	
	@Schema(description = "bio dell'user", example = "......")
    private String bio;
	
	@Schema(description = "interessi dell'user", example = "informatica")
    private String interessi;
	
	@Schema(description = "foto dell'user", example = "")
    private String fotoProfilo;
	
	@Schema(description = "eta dell'user", example = "Roma")
    private String citta;
	
	@Schema(description = "eta dell'user", example = "19")
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