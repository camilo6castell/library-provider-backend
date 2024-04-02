package com.pinguinera.provider.models.DTOS;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RetailSaleDTO {
    @NotNull
    @NotBlank
    public String title;
    @NotNull
    @NotBlank
    public String type;
    @NotNull
    @NotBlank
    @DecimalMin(value = "1", inclusive = false)
    public float basePrice;
    @NotNull
    public LocalDate clientEntryDate;

    public RetailSaleDTO() {
    }

    public RetailSaleDTO(String title, String type, float basePrice, LocalDate clientEntryDate) {
        this.title = title;
        this.type = type;
        this.basePrice = basePrice;
        this.clientEntryDate = clientEntryDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
