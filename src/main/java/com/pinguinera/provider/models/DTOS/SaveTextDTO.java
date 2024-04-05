package com.pinguinera.provider.models.DTOS;

import com.pinguinera.provider.models.enums.TextType;
import jakarta.validation.constraints.*;

public class SaveTextDTO {
    @NotNull
    @NotBlank
    public String title;
    @NotNull
    public TextType type;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    public float basePrice;

    public SaveTextDTO(String title, TextType type, float basePrice) {
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
