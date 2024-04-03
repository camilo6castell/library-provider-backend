package com.pinguinera.provider.models.entities.quote;

import java.util.ArrayList;

public class BudgetSaleQuote {
    private ArrayList<String> budgetQuote;
    private String quoteSummary;

    public BudgetSaleQuote(ArrayList<String> budgetQuote, String quoteSummary) {
        this.budgetQuote = budgetQuote;
        this.quoteSummary = quoteSummary;
    }

    public ArrayList<String> getBudgetQuote() {
        return budgetQuote;
    }

    public void setBudgetQuote(ArrayList<String> budgetQuote) {
        this.budgetQuote = budgetQuote;
    }

    public String getQuoteSummary() {
        return quoteSummary;
    }

    public void setQuoteSummary(String quoteSummary) {
        this.quoteSummary = quoteSummary;
    }
}
