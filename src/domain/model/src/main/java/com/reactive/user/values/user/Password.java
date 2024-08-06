package com.reactive.user.values.user;

import com.reactive.generic.IValueObject;

public class Password implements IValueObject<String> {
    public final String password;
    private Password(String password){
        this.password = password;
    }

    @Override
    public String value() {
        return password;
    }

    public static Password of(String password){
        return new Password(password);
    }
}
