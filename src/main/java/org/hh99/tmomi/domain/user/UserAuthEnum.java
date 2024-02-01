package org.hh99.tmomi.domain.user;

public enum UserAuthEnum {
	USER(Authority.USER),
	ADMIN(Authority.ADMIN);

	private final String authority;

	UserAuthEnum(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}

	public static class Authority {

		public static final String USER = "AUTH_USER";
		public static final String ADMIN = "AUTH_ADMIN";

	}

}
