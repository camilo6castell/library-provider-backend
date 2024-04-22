package com.pinguinera.provider.model.dto.quote.request;

import com.pinguinera.provider.model.dto.quote.request.shared.TextRequest;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SaveAndQuoteTextRequest {
    @NotNull
    public TextRequest text;
    @NotNull
    public String token;

    public SaveAndQuoteTextRequest() {
    }

    public SaveAndQuoteTextRequest(TextRequest text, String token) {
        this.text = text;
        this.token = token;
    }

    public TextRequest getText() {
        return text;
    }

    public void setText(TextRequest text) {
        this.text = text;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
