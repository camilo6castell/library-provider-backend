package com.pinguinera.provider.model.DTO;

import jakarta.validation.constraints.NotNull;

public class ItemFromTextBatchDTO {
    @NotNull
    public int index;
    @NotNull
    public int quantity;

    public ItemFromTextBatchDTO(int index, int quantity) {
        this.index = index;
        this.quantity = quantity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
