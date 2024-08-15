package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.IValueObject;

public class Title implements IValueObject<String> {
    private static final int MIN_LENGTH = 1; // Longitud mínima permitida para el título

    private final String title;

    private Title(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    @Override
    public String value() {
        return title;
    }

    /**
     * Factory method to create a Title object.
     *
     * @param title The title string.
     * @return A new Title object.
     * @throws IllegalArgumentException if the title is null, empty, or too short.
     */
    public static Title of(String title) {
        return new Title(title);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title that = (Title) o;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}


//public class Title implements IValueObject<String> {
//    public final String title;
//
//    private Title(String title) {
//        this.title = title;
//    }
//
//    @Override
//    public String value() {
//        return title;
//    }
//
//    public static Title of(String title){
//        return new Title(title);
//    }
//}
