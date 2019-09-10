package edu.cyclone.insider.auth;

class AuthConstants {
    static final String SECRET = "ThisIsTheTokenSecret";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users/sign-up";
}
