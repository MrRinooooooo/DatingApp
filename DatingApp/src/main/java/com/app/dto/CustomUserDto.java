package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

// Modello esteso della classe User per implementare lo userId
public class CustomUserDto extends User {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "ID dell'utente", example = "13")
	Long userId;

	public CustomUserDto(Long userId, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}	

}
