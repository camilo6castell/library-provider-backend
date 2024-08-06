package com.reactive.user.values.user;

import com.reactive.generic.IValueObject;

public class Email implements IValueObject<String> {
    public final String email;

    private Email(String email){
        this.email = email;
    }

    @Override
    public String value() {
        return email;
    }

    public static Email of(String email){
        return new Email(email);
    }
}
