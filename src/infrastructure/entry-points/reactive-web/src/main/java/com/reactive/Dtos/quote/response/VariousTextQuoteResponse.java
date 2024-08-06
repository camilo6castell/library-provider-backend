package com.reactive.Dtos.quote.response;


import com.reactive.user.entity.TextQuote;
import com.reactive.user.values.batchquote.TextQuoteResponse;

import java.util.List;

public class VariousTextQuoteResponse {
    public List<TextQuoteResponse> textQuoteList;
    public List<TextQuoteResponse> novelQuoteList;
    public float subtotal;
    public String discount;
    public float total;

    public VariousTextQuoteResponse(List<TextQuoteResponse> textQuoteList, List<TextQuoteResponse> novelQuoteList, float subtotal, String discount, float total) {
        this.textQuoteList = textQuoteList;
        this.novelQuoteList = novelQuoteList;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public List<TextQuoteResponse> getTextQuoteList() {
        return textQuoteList;
    }

    public void setTextQuoteList(List<TextQuoteResponse> textQuoteList) {
        this.textQuoteList = textQuoteList;
    }

    public List<TextQuoteResponse> getNovelQuoteList() {
        return novelQuoteList;
    }

    public void setNovelQuoteList(List<TextQuoteResponse> novelQuoteList) {
        this.novelQuoteList = novelQuoteList;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
