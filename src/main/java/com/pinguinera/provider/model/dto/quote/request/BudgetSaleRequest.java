package com.pinguinera.provider.model.dto.quote.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class BudgetSaleRequest {
    @NotNull
    public List<Integer> textsIndices;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    public float budget;
    @NotNull
    public LocalDate clientEntryDate;

    public BudgetSaleRequest() {
    }

    public BudgetSaleRequest(List<Integer> textsIndices, float budget, LocalDate clientEntryDate) {
        this.textsIndices = textsIndices;
        this.budget = budget;
        this.clientEntryDate = clientEntryDate;
    }

    public BudgetSaleRequest(List<Long> textIndices, float budget) {
    }

    public List<Integer> getTextsIndices() {
        return textsIndices;
    }

    public void setTextsIndices(List<Integer> textsIndices) {
        this.textsIndices = textsIndices;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public LocalDate getClientEntryDate() {
        return clientEntryDate;
    }

    public void setClientEntryDate(LocalDate clientEntryDate) {
        this.clientEntryDate = clientEntryDate;
    }
}
