package com.pinguinera.provider.models.DTOS;

import com.pinguinera.provider.models.entities.text.Book;
import com.pinguinera.provider.models.entities.text.Text;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetSaleDTO {
    @NotNull
    public ArrayList<RetailSaleDTO> textList;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    public float budget;
    @NotNull
    public LocalDate clientEntryDate;

    public BudgetSaleDTO(ArrayList<RetailSaleDTO> textList, float budget, LocalDate clientEntryDate) {
        this.textList = textList;
        this.budget = budget;
        this.clientEntryDate = clientEntryDate;
    }

    public ArrayList<RetailSaleDTO> getTextList() {
        return textList;
    }

    public void setTextList(ArrayList<RetailSaleDTO> textList) {
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
