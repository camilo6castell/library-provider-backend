package com.pinguinera.provider.models.DTOS;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class BudgetSaleDTO {
    @NotNull
    public ArrayList<SaveTextDTO> textList;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    public float budget;
    @NotNull
    public LocalDate clientEntryDate;

    public BudgetSaleDTO(ArrayList<SaveTextDTO> textList, float budget, LocalDate clientEntryDate) {
        this.textList = textList;
        this.budget = budget;
        this.clientEntryDate = clientEntryDate;
    }

    public ArrayList<SaveTextDTO> getTextList() {
        return textList;
    }

    public void setTextList(ArrayList<SaveTextDTO> textList) {
        this.textList = textList;
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
