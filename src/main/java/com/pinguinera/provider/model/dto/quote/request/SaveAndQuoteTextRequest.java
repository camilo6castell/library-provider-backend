package com.pinguinera.provider.model.dto.quote.request;

import com.pinguinera.provider.model.dto.quote.request.shared.TextRequest;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SaveAndQuoteTextRequest {
    @NotNull
    public TextRequest text;
    @NotNull
    public LocalDate clientEntryDate;

    public SaveAndQuoteTextRequest() {
    }

    public SaveAndQuoteTextRequest(TextRequest text, LocalDate clientEntryDate) {
        this.text = text;
        this.clientEntryDate = clientEntryDate;
    }

    public TextRequest getText() {
        return text;
    }

    public void setText(TextRequest text) {
        this.text = text;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
