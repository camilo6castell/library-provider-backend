package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.IValueObject;

public class Title implements IValueObject<String> {
    public final String title;

    private Title(String title) {
        this.title = title;
    }

    @Override
    public String value() {
        return title;
    }

    public static Title of(String title){
        return new Title(title);
    }
}
