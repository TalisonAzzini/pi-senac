package com.senac.pi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Criptografia {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public static String encode(String senha) {
        return encoder.encode(senha);
    }

    public static boolean matches(String senhaDigitada, String senhaArmazenada) {
        return encoder.matches(senhaDigitada, senhaArmazenada);
    }
    
}
