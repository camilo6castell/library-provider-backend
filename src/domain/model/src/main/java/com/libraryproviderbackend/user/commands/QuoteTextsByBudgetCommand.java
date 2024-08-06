package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;

import java.util.List;

public class QuoteTextsByBudgetCommand extends Command {
    public String userId;
    public List<Integer> textsIndices;
    public float budget;

    public QuoteTextsByBudgetCommand() {
    }

    public QuoteTextsByBudgetCommand(String userId, List<Integer> textsIndices, float budget) {
        this.userId = userId;
        this.textsIndices = textsIndices;
        this.budget = budget;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
