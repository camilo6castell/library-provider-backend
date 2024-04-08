package com.pinguinera.provider.models.DTOS;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleDTO {
    @NotNull
    public List<ItemFromTextBatchDTO> bookIndicesAnQuantity;
    @NotNull
    public List<ItemFromTextBatchDTO> novelIndicesAndQuantity;
    @NotNull
    public LocalDate clientEntryDate;

    public WholeSaleDTO(List<ItemFromTextBatchDTO> bookIndicesAnQuantity, List<ItemFromTextBatchDTO> novelIndicesAndQuantity, LocalDate clientEntryDate) {
        this.bookIndicesAnQuantity = bookIndicesAnQuantity;
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
        this.clientEntryDate = clientEntryDate;
    }

    public List<ItemFromTextBatchDTO> getBookIndicesAndQuantity() {
        return bookIndicesAnQuantity;
    }

    public void setBookIndicesAnQuantity(List<ItemFromTextBatchDTO> bookIndicesAnQuantity) {
        this.bookIndicesAnQuantity = bookIndicesAnQuantity;
    }

    public List<ItemFromTextBatchDTO> getNovelIndicesAndQuantity() {
        return novelIndicesAndQuantity;
    }

    public void setNovelIndicesAndQuantity(List<ItemFromTextBatchDTO> novelIndicesAndQuantity) {
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
