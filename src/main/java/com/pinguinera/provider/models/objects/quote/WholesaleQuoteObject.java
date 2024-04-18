package com.pinguinera.provider.models.objects.quote;

import java.util.List;

public class WholesaleQuoteObject {
    private List<TextQuoteObject> booksQuote;
    private List<TextQuoteObject> novelsQuote;
    private SummaryObject summary;

    public WholesaleQuoteObject(
            List<TextQuoteObject> booksQuote,
            List<TextQuoteObject> novelsQuote,
            SummaryObject summary
    ) {
        this.booksQuote = booksQuote;
        this.novelsQuote = novelsQuote;
        this.summary = summary;
    }

    public List<TextQuoteObject> getBooksQuote() {
        return booksQuote;
    }

    public void setBooksQuote(List<TextQuoteObject> booksQuote) {
        this.booksQuote = booksQuote;
    }

    public List<TextQuoteObject> getNovelsQuote() {
        return novelsQuote;
    }

    public void setNovelsQuote(List<TextQuoteObject> novelsQuote) {
        this.novelsQuote = novelsQuote;
    }

    public SummaryObject getSummary() {
        return summary;
    }

    public void setSummary(SummaryObject summary) {
        this.summary = summary;
    }
}
