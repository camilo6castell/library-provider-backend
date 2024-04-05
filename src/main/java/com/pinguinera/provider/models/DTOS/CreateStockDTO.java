package com.pinguinera.provider.models.DTOS;

import jakarta.validation.constraints.NotNull;

public class CreateStockDTO {
    @NotNull
    public boolean fillDataBase;

    public CreateStockDTO() {
    }

    public CreateStockDTO(boolean fillDataBase) {
        this.fillDataBase = fillDataBase;
    }

    public boolean isFillDataBase() {
        return fillDataBase;
    }

    public void setFillDataBase(boolean fillDataBase) {
        this.fillDataBase = fillDataBase;
    }
}
