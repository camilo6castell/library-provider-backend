package com.libraryproviderbackend.user.values.user;

import com.libraryproviderbackend.generic.IValueObject;

import java.util.Objects;

public class Password implements IValueObject<String> {

    // Validations

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 64;
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+{}[]|:;\"'<>,.?/";

    // Value

    public final String password;


    public Password(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long");
        }
        if (!containsUpperCase(password)) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!containsLowerCase(password)) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!containsDigit(password)) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
        if (!containsSpecialCharacter(password)) {
            throw new IllegalArgumentException("Password must contain at least one special character: " + SPECIAL_CHARACTERS);
        }
        this.password = password;
    }

    // DDD

    @Override
    public String value() {
        return password;
    }

    public static Password of(String password){
        return new Password(password);
    }

    // Equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }


    // Methods for validations

    private boolean containsUpperCase(String value) {
        return value.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsLowerCase(String value) {
        return value.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsDigit(String value) {
        return value.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSpecialCharacter(String value) {
        return value.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);
    }
}
