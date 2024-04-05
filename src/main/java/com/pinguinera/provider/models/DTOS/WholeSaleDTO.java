package com.pinguinera.provider.models.DTOS;


import com.pinguinera.provider.models.objects.text.BookObject;
import com.pinguinera.provider.models.objects.text.NovelObject;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleDTO {
    @NotNull
    public List<Long> bookIndices;
    @NotNull
    public List<Long> novelIndices;
    @NotNull
    public LocalDate clientEntryDate;

    public WholeSaleDTO(List<Long> bookIndices, List<Long> novelIndices, LocalDate clientEntryDate) {
        this.bookIndices = bookIndices;
        this.novelIndices = novelIndices;
        this.clientEntryDate = clientEntryDate;
    }

    public List<Long> getBookIndices() {
        return bookIndices;
    }

    public void setBookIndices(List<Long> bookIndices) {
        this.bookIndices = bookIndices;
    }

    public List<Long> getNovelIndices() {
        return novelIndices;
    }

    public void setNovelIndices(List<Long> novelIndices) {
        this.novelIndices = novelIndices;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
