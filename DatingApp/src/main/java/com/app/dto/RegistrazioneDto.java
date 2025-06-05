package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrazioneDto {

	@NotBlank(message = "Email è obbligatoria")
    @Email(message = "Formato email non valido")
	private String email;
	
	@NotBlank(message = "Password è obbligatoria")
    @Size(min = 6, message = "Password deve essere di almeno 6 caratteri")
	private String password;
	
	private String tipoAccount;

	public RegistrazioneDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegistrazioneDto(
			@NotBlank(message = "Email è obbligatoria") @Email(message = "Formato email non valido") String email,
			@NotBlank(message = "Password è obbligatoria") @Size(min = 6, message = "Password deve essere di almeno 6 caratteri") String password) {
		super();
		this.email = email;
		this.password = password;
		this.tipoAccount = "Standard";
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

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}
	
}
