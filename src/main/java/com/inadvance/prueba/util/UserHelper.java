package com.inadvance.prueba.util;

import com.inadvance.prueba.exception.ValidationException;

import java.util.regex.Pattern;

public class UserHelper {

    private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[cC][lL]?";

    public static boolean isValidEmail(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new ValidationException("Formato de correo no válido");
        }
        return true;
    }

    public static boolean isValidPassword(String passwordRegex, String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            throw new ValidationException("Formato de contraseña no válido");
        }
        return true;
    }

    public static String generateToken() {
        return "token";
    }
}
