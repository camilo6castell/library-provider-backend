package com.pinguinera.provider.models.DTOS;

import jakarta.validation.constraints.NotNull;

public class FillDataBaseDTO {
    @NotNull
    public boolean fillDataBase;

    public FillDataBaseDTO() {
    }

    public FillDataBaseDTO(boolean fillDataBase) {
        this.fillDataBase = fillDataBase;
    }

    public boolean isFillDataBase() {
        return fillDataBase;
    }

    public void setFillDataBase(boolean fillDataBase) {
        this.fillDataBase = fillDataBase;
    }
}
