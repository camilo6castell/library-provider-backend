package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.IValueObject;

public class Type implements IValueObject<TextTypeEnum> {
    private final TextTypeEnum type;

    private Type(TextTypeEnum type) {
        if (type == null) {
            throw new IllegalArgumentException("TextTypeEnum cannot be null");
        }
        this.type = type;
    }

    @Override
    public TextTypeEnum value() {
        return type;
    }

    /**
     * Factory method to create a Type object.
     *
     * @param type The TextTypeEnum value.
     * @return A new Type object.
     * @throws IllegalArgumentException if the type is null.
     */
    public static Type of(TextTypeEnum type) {
        return new Type(type);
    }

    @Override
    public String toString() {
        return type.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type that = (Type) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}


//public class Type implements IValueObject<TextTypeEnum> {
//    public TextTypeEnum type;
//
//    private Type(TextTypeEnum type) {
//        this.type = type;
//    }
//
//    @Override
//    public TextTypeEnum value() {
//        return type;
//    }
//
//    public static Type of(TextTypeEnum type){
//        return new Type(type);
//    }
//}
