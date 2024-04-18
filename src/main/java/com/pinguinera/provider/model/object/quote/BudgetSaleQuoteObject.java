package com.pinguinera.provider.model.object.quote;

import com.pinguinera.provider.model.object.response.ResponseObject;

import java.util.ArrayList;

public class BudgetSaleQuoteObject {
    private ArrayList<TextBatchObject> suggestedTextsBatch;
    private SummaryObject quoteSummary;
    private ResponseObject messageFromServer;

    public BudgetSaleQuoteObject(
            ArrayList<TextBatchObject> suggestedTextsBatch,
            SummaryObject quoteSummary,
            ResponseObject messageFromServer
    ) {
        this.suggestedTextsBatch = suggestedTextsBatch;
        this.quoteSummary = quoteSummary;
        this.messageFromServer = messageFromServer;
    }

    public ArrayList<TextBatchObject> getSuggestedTextsBatch() {
        return suggestedTextsBatch;
    }

    public void setSuggestedTextsBatch(ArrayList<TextBatchObject> suggestedTextsBatch) {
        this.suggestedTextsBatch = suggestedTextsBatch;
    }

    public SummaryObject getQuoteSummary() {
        return quoteSummary;
    }

    public void setQuoteSummary(SummaryObject quoteSummary) {
        this.quoteSummary = quoteSummary;
    }

    public ResponseObject getMessageFromServer() {
        return messageFromServer;
    }

    public void setMessageFromServer(ResponseObject messageFromServer) {
        this.messageFromServer = messageFromServer;
    }
}
