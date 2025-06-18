package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrazioneDto {
	
    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Formato email non valido")
	@Schema(description = "email dell'utente", example = "marco_bianchi@gmail.com")
	private String email;
    
    @NotBlank(message = "Password è obbligatoria")
    @Size(min = 6, message = "Password deve essere di almeno 6 caratteri")
	@Schema(description = "password dell'utente", example = "1dasdff4a56ds")
	private String password;

  public RegistrazioneDto() {

	}
	
	public RegistrazioneDto(String email, String password) {
		this.email = email;
		this.password = password;
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
	
}
