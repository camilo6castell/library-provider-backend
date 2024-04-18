package com.pinguinera.provider.model.dto.quote.response;

public class RetailSaleQuoteResponse {
    private String textTitle;
    private String textType;
    private float total;

    public RetailSaleQuoteResponse(String textTitle, String textType, float total) {
        this.textTitle = textTitle;
        this.textType = textType;
        this.total = total;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
