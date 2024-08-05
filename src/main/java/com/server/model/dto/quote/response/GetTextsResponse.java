package com.pinguinera.provider.model.dto.quote.response;

import com.pinguinera.provider.model.persistence.TextEntity;

import java.util.List;

public class GetTextsResponse {
    private List<TextEntity> textsStock;

    public GetTextsResponse(List<TextEntity> textsStock) {
        this.textsStock = textsStock;
    }

    public GetTextsResponse() {
    }

    public List<TextEntity> getTextsStock() {
        return textsStock;
    }

    public void setTextsStock(List<TextEntity> textsStock) {
        this.textsStock = textsStock;
    }
}
