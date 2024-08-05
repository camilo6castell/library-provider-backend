package com.pinguinera.provider.model.dto.quote.response;

import com.pinguinera.provider.model.dto.quote.response.shared.SummaryResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.TextQuoteResponse;

import java.util.List;

public class WholesaleQuoteResponse {
    private List<TextQuoteResponse> booksQuote;
    private List<TextQuoteResponse> novelsQuote;
    private SummaryResponse summary;

    public WholesaleQuoteResponse(
            List<TextQuoteResponse> booksQuote,
            List<TextQuoteResponse> novelsQuote,
            SummaryResponse summary
    ) {
        this.booksQuote = booksQuote;
        this.novelsQuote = novelsQuote;
        this.summary = summary;
    }

    public List<TextQuoteResponse> getBooksQuote() {
        return booksQuote;
    }

    public void setBooksQuote(List<TextQuoteResponse> booksQuote) {
        this.booksQuote = booksQuote;
    }

    public List<TextQuoteResponse> getNovelsQuote() {
        return novelsQuote;
    }

    public void setNovelsQuote(List<TextQuoteResponse> novelsQuote) {
        this.novelsQuote = novelsQuote;
    }

    public SummaryResponse getSummary() {
        return summary;
    }

    public void setSummary(SummaryResponse summary) {
        this.summary = summary;
    }
}
