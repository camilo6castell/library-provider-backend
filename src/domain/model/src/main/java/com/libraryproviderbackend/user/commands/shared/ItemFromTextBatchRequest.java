package com.libraryproviderbackend.user.commands.shared;

public class ItemFromTextBatchRequest {

    public int index;

    public int quantity;

    public ItemFromTextBatchRequest(int index, int quantity) {
        this.index = index;
        this.quantity = quantity;
    }

    public ItemFromTextBatchRequest() {
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