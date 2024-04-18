package com.pinguinera.provider.model.dto.quote.request.shared;

import jakarta.validation.constraints.NotNull;

public class ItemFromTextBatchRequest {
    @NotNull
    public int index;
    @NotNull
    public int quantity;

    public ItemFromTextBatchRequest(int index, int quantity) {
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
