package br.com.devdojo.config;

import java.util.concurrent.TimeUnit;

/**
 * @author yvesguilherme on 26/07/2020.
 * @project spring-boot-essentials
 */
public class SecurityConstants {
    static final String SECRET = "DevDojoFoda";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/users/sign-up";
    static final long EXPIRATION_TIME = 86400000L;

    /* Saber quantos milissegundos possui um dia. */
//    public static void main(String[] args) {
//        System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
//    }
}