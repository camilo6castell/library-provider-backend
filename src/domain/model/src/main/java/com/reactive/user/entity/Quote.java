package com.reactive.user.entity;

import com.reactive.generic.Entity;
import com.reactive.user.values.identities.QuoteId;
import com.reactive.user.values.quote.Status;
import com.reactive.user.values.shared.Discount;
import com.reactive.user.values.shared.Subtotal;
import com.reactive.user.values.shared.Total;

import java.util.ArrayList;
import java.util.UUID;

public class Quote extends Entity<QuoteId> {

    public ArrayList<BatchQuote> batchQuoteList;

    public Subtotal subtotal;
    public Discount discount;
    public Total total;

    public Quote(ArrayList<BatchQuote> batchQuoteList, Subtotal subtotal, Discount discount, Total total) {
        super(new QuoteId());
        this.batchQuoteList = batchQuoteList;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public ArrayList<BatchQuote> getBatchQuoteList() {
        return batchQuoteList;
    }

    public void setBatchQuoteList(ArrayList<BatchQuote> batchQuoteList) {
        this.batchQuoteList = batchQuoteList;
    }

    public Subtotal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Subtotal subtotal) {
        this.subtotal = subtotal;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }
}

