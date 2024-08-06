package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.entity.BatchQuote;
import com.libraryproviderbackend.user.entity.TextQuote;
import com.libraryproviderbackend.user.values.batchquote.TextQuoteResponse;

import java.util.List;
import java.util.UUID;

public class BudgetTextsQuoted extends DomainEvent {
    public List<TextQuoteResponse> texts;
    public float subtotal;
    public String discount;
    public float total;
    public float change;

    public BudgetTextsQuoted() {
    }

    public BudgetTextsQuoted(List<TextQuote> texts, float subtotal, String discount, float total, float change) {
        super(UUID.randomUUID().toString(), "BudgetTextsQuotedEvent");
        this.texts = BatchQuote.mapToTextQuoteResponses(texts);
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.change = change;
    }

    public List<TextQuoteResponse> getTexts() {
        return texts;
    }

    public void setTexts(List<TextQuoteResponse> texts) {
        this.texts = texts;
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

