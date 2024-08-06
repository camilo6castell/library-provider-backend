package com.reactive.user.events;


import com.reactive.generic.DomainEvent;
import com.reactive.user.entity.BatchQuote;
import com.reactive.user.entity.TextQuote;
import com.reactive.user.values.batchquote.TextQuoteResponse;

import java.util.List;

public class VariousTextQuotedEvent extends DomainEvent {
    public List<TextQuoteResponse> bookQuoteList;
    public List<TextQuoteResponse> novelQuoteList;
    public float subtotal;
    public String discount;
    public float total;

    public VariousTextQuotedEvent() {
    }

    public VariousTextQuotedEvent(List<TextQuote> bookQuoteList, List<TextQuote> novelQuoteList, float subtotal, String discount, float total) {
        super("324fdsfrwefdsf4-435fer-35", "VariostextQuotedEvent");
        this.bookQuoteList = BatchQuote.mapToTextQuoteResponses(bookQuoteList);
        this.novelQuoteList = BatchQuote.mapToTextQuoteResponses(novelQuoteList);
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }



    public List<TextQuoteResponse> getBookQuoteList() {
        return bookQuoteList;
    }

    public void setBookQuoteList(List<TextQuoteResponse> bookQuoteList) {

        this.bookQuoteList = bookQuoteList;
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
