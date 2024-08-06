package com.pinguinera.provider.model.dto.quote.request;

import com.pinguinera.provider.model.dto.quote.request.shared.ItemFromTextBatchRequest;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleRequest {
    @NotNull
    public List<ItemFromTextBatchRequest> bookIndicesAndQuantity;
    @NotNull
    public List<ItemFromTextBatchRequest> novelIndicesAndQuantity;
    @NotNull
    public String token;

    public WholeSaleRequest(List<ItemFromTextBatchRequest> bookIndicesAndQuantity, List<ItemFromTextBatchRequest> novelIndicesAndQuantity, String token) {
        this.bookIndicesAndQuantity = bookIndicesAndQuantity;
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
        this.token = token;
    }

    public WholeSaleRequest() {
    }

    public List<ItemFromTextBatchRequest> getBookIndicesAndQuantity() {
        return bookIndicesAndQuantity;
    }

    public void setBookIndicesAndQuantity(List<ItemFromTextBatchRequest> bookIndicesAndQuantity) {
        this.bookIndicesAndQuantity = bookIndicesAndQuantity;
    }

    public List<ItemFromTextBatchRequest> getNovelIndicesAndQuantity() {
        return novelIndicesAndQuantity;
    }

    public void setNovelIndicesAndQuantity(List<ItemFromTextBatchRequest> novelIndicesAndQuantity) {
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
