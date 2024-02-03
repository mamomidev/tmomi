package org.hh99.tmomi.domain.user;

public enum UserAuthEnum {
	USER("USER"),
	ADMIN("ADMIN");

	private final String name;

	UserAuthEnum(String name) {
		this.name = name;
	}

	public String getAuthority() {
		return this.name;
	}

}
