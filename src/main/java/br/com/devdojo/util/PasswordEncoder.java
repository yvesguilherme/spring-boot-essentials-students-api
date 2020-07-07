package br.com.devdojo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yvesguilherme on 06/07/2020.
 * @project spring-boot-essentials
 */
public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("devdojo"));
    }
}
