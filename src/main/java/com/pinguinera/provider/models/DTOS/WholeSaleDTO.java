package com.pinguinera.provider.models.DTOS;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleDTO {
    @NotNull
    public List<ItemFromTextBatchDTO> bookIndices;
    @NotNull
    public List<ItemFromTextBatchDTO> novelIndices;
    @NotNull
    public LocalDate clientEntryDate;

    public WholeSaleDTO(List<ItemFromTextBatchDTO> bookIndices, List<ItemFromTextBatchDTO> novelIndices, LocalDate clientEntryDate) {
        this.bookIndices = bookIndices;
        this.novelIndices = novelIndices;
        this.clientEntryDate = clientEntryDate;
    }

    public List<ItemFromTextBatchDTO> getBookIndices() {
        return bookIndices;
    }

    public void setBookIndices(List<ItemFromTextBatchDTO> bookIndices) {
        this.bookIndices = bookIndices;
    }

    public List<ItemFromTextBatchDTO> getNovelIndices() {
        return novelIndices;
    }

    public void setNovelIndices(List<ItemFromTextBatchDTO> novelIndices) {
        this.novelIndices = novelIndices;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
