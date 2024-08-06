package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;

import java.util.List;

public class QuoteBatchQuoteCommand extends Command {

    public String userId;
    public List<List<Integer>> textsIndicesList;

    public QuoteBatchQuoteCommand() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<List<Integer>> getTextsIndicesList() {
        return textsIndicesList;
    }

    public void setTextsIndicesList(List<List<Integer>> textsIndicesList) {
        this.textsIndicesList = textsIndicesList;
    }
}
