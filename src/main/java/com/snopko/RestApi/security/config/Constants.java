package com.snopko.RestApi.security.config;

public class Constants {
    public static final String SECRET = "SecretKeyJWT";
    public static final long EXPIRATION_TIME = 86_400_000;

    public static final String TOKEN_PREFIX = "jwt";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
