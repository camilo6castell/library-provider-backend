package com.libraryproviderbackend.Dtos.quote.response.shared;

import java.util.ArrayList;
import java.util.List;

public class TextBatchResponse {
    private String title;
    private String type;
    private float price;
    private List<DiscountResponse> discounts;
    private float totalPrice;

    public TextBatchResponse(
            String title,
            String type,
            float price,
            List<DiscountResponse> discounts,
            float totalPrice
    ) {
        List<DiscountResponse> discountAux = new ArrayList<>();
        discountAux.add(
                new DiscountResponse(
                        "No aplica",
                        0
                )
        );
        this.title = title;
        this.type = type;
        this.price = price;
        this.discounts = discounts.isEmpty() ? discountAux : discounts;
        this.totalPrice = totalPrice;
    }

}
