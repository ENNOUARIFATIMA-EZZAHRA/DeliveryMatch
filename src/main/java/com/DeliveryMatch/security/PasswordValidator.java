package com.DeliveryMatch.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    
    public boolean isValid(String password) {
        if (password == null || password.length() != 8) {
            return false;
        }

        // التحقق من أن كل الأحرف أرقام
        for (char c : password.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public String getPasswordRequirements() {
        return "Le mot de passe doit contenir exactement 8 chiffres";
    }
} 