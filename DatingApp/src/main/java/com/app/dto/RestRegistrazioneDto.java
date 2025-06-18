package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
public class RestRegistrazioneDto {

	@Schema(description = "stato registrazione", example = "true/false")
	private boolean success;
	
	@Schema(description = "esito registrazione", example = "registrazione avvenuta con successo")
	private String message;
	
	
	public RestRegistrazioneDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public RestRegistrazioneDto(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
