package com.libraryproviderbackend.user.values.user;

import com.libraryproviderbackend.generic.IValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email implements IValueObject<String> {

    // Validations

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Value

    public final String email;

    // Constructor

    private Email(String email){

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de email incorrecto");
        }
        this.email = email;
    }

    // DDD

    @Override
    public String value() {
        return email;
    }

    public static Email of(String email){
        return new Email(email);
    }

    // Equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return email.equals(email.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
