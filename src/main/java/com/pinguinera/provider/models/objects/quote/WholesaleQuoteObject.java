package com.pinguinera.provider.models.objects.quote;

import java.util.List;

public class WholesaleQuoteObject {
    private List<String> booksQuote;
    private List<String> novelsQuote;
    private String total;

    public WholesaleQuoteObject(List<String> booksQuote, List<String> novelsQuote, String total) {
        this.booksQuote = booksQuote;
        this.novelsQuote = novelsQuote;
        this.total = total;
    }

    public List<String> getBooksQuote() {
        return booksQuote;
    }

    public void setBooksQuote(List<String> booksQuote) {
        this.booksQuote = booksQuote;
    }

    public List<String> getNovelsQuote() {
        return novelsQuote;
    }

    public void setNovelsQuote(List<String> novelsQuote) {
        this.novelsQuote = novelsQuote;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
