package com.pinguinera.provider.model.DTO;

import com.pinguinera.provider.model.enums.TextType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TextDTO {
    @NotNull
    @NotBlank
    public String title;
    @NotNull
    public TextType type;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    public float basePrice;

    public TextDTO(String title, TextType type, float basePrice) {
        this.title = title;
        this.type = type;
        this.basePrice = basePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TextType getType() {
        return type;
    }

    public void setType(TextType type) {
        this.type = type;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }
}
