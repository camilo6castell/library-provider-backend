package com.pinguinera.provider.models.DTOS;


import com.pinguinera.provider.models.entities.text.Book;
import com.pinguinera.provider.models.entities.text.Novel;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class WholeSaleDTO {
    @NotNull
    public List<Book> bookList;
    @NotNull
    public List<Novel> novelList;
    @NotNull
    public LocalDate clientEntryDate;

    public WholeSaleDTO(List<Book> bookList, List<Novel> novelList, LocalDate clientEntryDate) {
        this.bookList = bookList;
        this.novelList = novelList;
        this.clientEntryDate = clientEntryDate;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Novel> getNovelList() {
        return novelList;
    }

    public void setNovelList(List<Novel> novelList) {
        this.novelList = novelList;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
