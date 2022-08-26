package com.store.storebookspring.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptPassword {

    public static void main(String[] args){
        var password = "user";

        System.out.println("password: " + password);
        System.out.println("password encript: " + encrypt(password));
    }

    public static String encrypt(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
