package com.pinguinera.provider.models.DTOS;


import com.pinguinera.provider.models.objects.text.BookObject;
import com.pinguinera.provider.models.objects.text.NovelObject;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleDTO {
    @NotNull
    public List<BookObject> bookObjectList;
    @NotNull
    public List<NovelObject> novelObjectList;
    @NotNull
    public LocalDate clientEntryDate;

    public WholeSaleDTO(List<BookObject> bookObjectList, List<NovelObject> novelObjectList, LocalDate clientEntryDate) {
        this.bookObjectList = bookObjectList;
        this.novelObjectList = novelObjectList;
        this.clientEntryDate = clientEntryDate;
    }

    public List<BookObject> getBookList() {
        return bookObjectList;
    }

    public void setBookList(List<BookObject> bookObjectList) {
        this.bookObjectList = bookObjectList;
    }

    public List<NovelObject> getNovelList() {
        return novelObjectList;
    }

    public void setNovelList(List<NovelObject> novelObjectList) {
        this.novelObjectList = novelObjectList;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
