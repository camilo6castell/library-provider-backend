package com.pinguinera.provider.model.dto.quote.request;

public class GetTextRequest {
    private boolean getStock;

    public GetTextRequest() {
    }

    public GetTextRequest(boolean getStock) {
        this.getStock = getStock;
    }

    public boolean isGetStock() {
        return getStock;
    }

    public void setGetStock(boolean getStock) {
        this.getStock = getStock;
    }
}
