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
    public String token;

    public BudgetSaleRequest() {
    }

    public BudgetSaleRequest(List<Integer> textsIndices, float budget, String token) {
        this.textsIndices = textsIndices;
        this.budget = budget;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
