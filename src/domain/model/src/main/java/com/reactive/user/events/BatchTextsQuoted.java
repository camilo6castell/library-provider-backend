package com.reactive.user.events;

import com.reactive.generic.DomainEvent;
import com.reactive.user.entity.BatchQuote;
import com.reactive.user.values.batchquote.TextQuoteResponse;
import com.reactive.user.values.shared.DiscountsEnum;
import com.reactive.user.values.shared.Subtotal;
import com.reactive.user.values.shared.Total;

import java.util.List;
import java.util.stream.Collectors;

public class BatchTextsQuoted extends DomainEvent {

    public List<TextQuoteResponse> batchQuotes;
    public float subtotal;
    public DiscountsEnum discount;
    public float total;

    public BatchTextsQuoted() {
    }

//    public BatchTextsQuoted(List<BatchQuote> batchQuotes) {
//        this.batchQuotes = batchQuotes.stream()
//                .map(batch -> BatchQuote.mapToTextQuoteResponses(batch))
//                .collect(Collectors.toList())

        // Calculate the subtotal as the sum of all subtotals from batchQuotes
//        this.subtotal = (float) batchQuotes.stream()
//                .map(BatchQuote::getSubtotal)
//                .mapToDouble(Subtotal::value)
//                .sum();
//
//        // Calculate the total as the sum of all totals from batchQuotes
//        this.total = (float) batchQuotes.stream()
//                .map(BatchQuote::getTotal)
//                .mapToDouble(Total::value)
//                .sum();
//
//        // Use the discount from the first BatchQuote in the list
//        this.discount = batchQuotes.isEmpty() ? null : batchQuotes.getFirst().discount.value();
//    }
//
//
//    public BatchTextsQuoted(List<BatchQuote> batchQuotes, float subtotal, DiscountsEnum discount, float total) {
//        this.batchQuotes = batchQuotes;
//        this.subtotal = subtotal;
//        this.discount = discount;
//        this.total = total;
//    }
//
//    public List<BatchQuote> getBatchQuotes() {
//        return batchQuotes;
//    }
//
//    public void setBatchQuotes(List<BatchQuote> batchQuotes) {
//        this.batchQuotes = batchQuotes;
//    }
//
//    public float getSubtotal() {
//        return subtotal;
//    }
//
//    public void setSubtotal(float subtotal) {
//        this.subtotal = subtotal;
//    }
//
//    public DiscountsEnum getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(DiscountsEnum discount) {
//        this.discount = discount;
//    }
//
//    public float getTotal() {
//        return total;
//    }
//
//    public void setTotal(float total) {
//        this.total = total;
//    }
}
