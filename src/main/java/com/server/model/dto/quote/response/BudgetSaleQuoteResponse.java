package com.pinguinera.provider.model.dto.quote.response;

import com.pinguinera.provider.model.dto.quote.response.shared.SummaryResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.TextBatchResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.StatusResponse;

import java.util.ArrayList;

public class BudgetSaleQuoteResponse {
    private ArrayList<TextBatchResponse> suggestedTextsBatch;
    private SummaryResponse quoteSummary;
    private StatusResponse messageFromServer;

    public BudgetSaleQuoteResponse(
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            SummaryResponse quoteSummary,
            StatusResponse messageFromServer
    ) {
        this.suggestedTextsBatch = suggestedTextsBatch;
        this.quoteSummary = quoteSummary;
        this.messageFromServer = messageFromServer;
    }

    public ArrayList<TextBatchResponse> getSuggestedTextsBatch() {
        return suggestedTextsBatch;
    }

    public void setSuggestedTextsBatch(ArrayList<TextBatchResponse> suggestedTextsBatch) {
        this.suggestedTextsBatch = suggestedTextsBatch;
    }

    public SummaryResponse getQuoteSummary() {
        return quoteSummary;
    }

    public void setQuoteSummary(SummaryResponse quoteSummary) {
        this.quoteSummary = quoteSummary;
    }

    public StatusResponse getMessageFromServer() {
        return messageFromServer;
    }

    public void setMessageFromServer(StatusResponse messageFromServer) {
        this.messageFromServer = messageFromServer;
    }
}
