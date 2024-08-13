package com.libraryproviderbackend.user.values.user;

import com.libraryproviderbackend.generic.IValueObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class EntryDate implements IValueObject<LocalDate> {

    // Validations
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Value

    public LocalDate entryDate;

    // Constructor

    private EntryDate(String entryDate) {

        if (entryDate == null || entryDate.isEmpty()) {
            throw new IllegalArgumentException("La fecha no pueda ser nula o vacía");
        }

        LocalDate parsedDate;

        try {
            parsedDate =  LocalDate.parse(entryDate, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha incorrecto");
        }

        if (parsedDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de entrada no puede ser posterior a la fecha actual");
        }

        this.entryDate = parsedDate;
    }

    // DDD

    @Override
    public LocalDate value() {
        return entryDate;
    }

    public static EntryDate of(String entryDate){
        return new EntryDate(entryDate);
    }

    // Equals and hashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntryDate entryDate1 = (EntryDate) o;
        return Objects.equals(dateTimeFormatter, entryDate1.dateTimeFormatter) && Objects.equals(entryDate, entryDate1.entryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTimeFormatter, entryDate);
    }
}
