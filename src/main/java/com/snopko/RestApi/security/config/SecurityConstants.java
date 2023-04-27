package com.snopko.RestApi.security.config;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyJWT";
    public static final long EXPIRATION_TIME = 86_400_000;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
