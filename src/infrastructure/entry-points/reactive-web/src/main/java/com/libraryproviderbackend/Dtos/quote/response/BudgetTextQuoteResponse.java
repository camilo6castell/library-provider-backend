package com.libraryproviderbackend.Dtos.quote.response;

import com.libraryproviderbackend.user.values.batchquote.TextQuoteResponse;

import java.util.List;

public class BudgetTextQuoteResponse {
    public List<TextQuoteResponse> textQuoteList;
    public float subtotal;
    public String discount;
    public float total;
    public float change;

    public BudgetTextQuoteResponse() {
    }

    public BudgetTextQuoteResponse(
            float change,
            float total,
            String discount,
            float subtotal,
            List<TextQuoteResponse> textQuoteList
    ) {
        this.change = change;
        this.total = total;
        this.discount = discount;
        this.subtotal = subtotal;
        this.textQuoteList = textQuoteList;
    }

    public List<TextQuoteResponse> getTextQuoteList() {
        return textQuoteList;
    }

    public void setTextQuoteList(List<TextQuoteResponse> textQuoteList) {
        this.textQuoteList = textQuoteList;
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

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }
}
