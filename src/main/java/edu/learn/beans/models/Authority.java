package edu.learn.beans.models;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

	ANONYMOUS,
	REGISTERED_USER,
	BOOKING_MANAGER;

	@Override
	public String getAuthority() {
		return toString();
	}

}
