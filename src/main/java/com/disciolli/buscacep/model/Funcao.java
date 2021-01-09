package com.disciolli.buscacep.model;

import org.springframework.security.core.GrantedAuthority;

public enum Funcao implements GrantedAuthority {

	ROLE_ADMIN, ROLE_USER;

	public String getAuthority() {
		return name();
	}
}
