package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;

import java.util.List;

public class QuoteTextsByBudgetCommand extends Command {

    private String userId;
    private List<Integer> textsIndices;
    private Float budget;

    public QuoteTextsByBudgetCommand() {
    }

    public QuoteTextsByBudgetCommand(String userId, List<Integer> textsIndices, Float budget) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (textsIndices == null || textsIndices.isEmpty()) {
            throw new IllegalArgumentException("Text indices cannot be null or empty");
        }
        if (budget == null || budget <= 0) {
            throw new IllegalArgumentException("Budget must be greater than zero");
        }
        this.userId = userId;
        this.textsIndices = textsIndices;
        this.budget = budget;
    }

    public String getUserId()                   { return userId; }
    public void setUserId(String userId)         { this.userId = userId; }

    public List<Integer> getTextsIndices()       { return textsIndices; }
    public void setTextsIndices(List<Integer> v) { this.textsIndices = v; }

    public Float getBudget()                     { return budget; }
    public void setBudget(Float budget)          { this.budget = budget; }
}
