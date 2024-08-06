package com.reactive.user.values.user;

import com.reactive.generic.IValueObject;

import java.time.LocalDate;

public class EntryDate implements IValueObject<LocalDate> {
    public LocalDate entryDate;

    private EntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public LocalDate value() {
        return entryDate;
    }

    public static EntryDate of(LocalDate entryDate){
        return new EntryDate(entryDate);
    }
}
