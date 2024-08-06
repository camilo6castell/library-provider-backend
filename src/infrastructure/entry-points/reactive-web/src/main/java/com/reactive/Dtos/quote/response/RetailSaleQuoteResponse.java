package com.reactive.Dtos.quote.response;

public class RetailSaleQuoteResponse {
    private String textTitle;
    private String textType;
    private float total;

    public RetailSaleQuoteResponse(String textTitle, String textType, float total) {
        this.textTitle = textTitle;
        this.textType = textType;
        this.total = total;
    }

}
