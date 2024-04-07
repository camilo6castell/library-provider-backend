package com.pinguinera.provider.models.DTOS;

import com.pinguinera.provider.models.enums.TextType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SaveAndQuoteTextDTO {
    @NotNull
    public TextDTO text;
    @NotNull
    public LocalDate clientEntryDate;

    public SaveAndQuoteTextDTO(TextDTO text, LocalDate clientEntryDate) {
        this.text = text;
        this.clientEntryDate = clientEntryDate;
    }

    public TextDTO getText() {
        return text;
    }

    public void setText(TextDTO text) {
        this.text = text;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
