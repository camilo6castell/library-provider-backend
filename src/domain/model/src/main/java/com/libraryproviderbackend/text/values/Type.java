package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.IValueObject;

public class Type implements IValueObject<TextTypeEnum> {
    public TextTypeEnum type;

    private Type(TextTypeEnum type) {
        this.type = type;
    }

    @Override
    public TextTypeEnum value() {
        return type;
    }

    public static Type of(TextTypeEnum type){
        return new Type(type);
    }
}
