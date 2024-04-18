package com.pinguinera.provider.model.dto.quote.request;

import com.pinguinera.provider.model.dto.quote.request.shared.ItemFromTextBatchRequest;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleRequest {
    @NotNull
    public List<ItemFromTextBatchRequest> bookIndicesAnQuantity;
    @NotNull
    public List<ItemFromTextBatchRequest> novelIndicesAndQuantity;
    @NotNull
    public LocalDate clientEntryDate;

    public WholeSaleRequest(
            List<ItemFromTextBatchRequest> bookIndicesAnQuantity,
            List<ItemFromTextBatchRequest> novelIndicesAndQuantity,
            LocalDate clientEntryDate
    ) {
        this.bookIndicesAnQuantity = bookIndicesAnQuantity;
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
        this.clientEntryDate = clientEntryDate;
    }

    public List<ItemFromTextBatchRequest> getBookIndicesAndQuantity() {
        return bookIndicesAnQuantity;
    }

    public void setBookIndicesAnQuantity(List<ItemFromTextBatchRequest> bookIndicesAnQuantity) {
        this.bookIndicesAnQuantity = bookIndicesAnQuantity;
    }

    public List<ItemFromTextBatchRequest> getNovelIndicesAndQuantity() {
        return novelIndicesAndQuantity;
    }

    public void setNovelIndicesAndQuantity(List<ItemFromTextBatchRequest> novelIndicesAndQuantity) {
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
