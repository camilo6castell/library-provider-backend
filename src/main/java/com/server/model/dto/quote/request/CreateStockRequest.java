package com.pinguinera.provider.model.dto.quote.request;

import jakarta.validation.constraints.NotNull;

public class CreateStockRequest {
    @NotNull
    public boolean fillDataBase;

    public CreateStockRequest() {
    }

    public CreateStockRequest(boolean fillDataBase) {
        this.fillDataBase = fillDataBase;
    }

    public boolean isFillDataBase() {
        return fillDataBase;
    }

    public void setFillDataBase(boolean fillDataBase) {
        this.fillDataBase = fillDataBase;
    }
}
