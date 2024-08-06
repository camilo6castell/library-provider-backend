package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;
import com.libraryproviderbackend.user.commands.shared.ItemFromTextBatchRequest;

import java.util.List;

public class QuoteVariousTextsCommand extends Command {
    public String userId;
    public List<ItemFromTextBatchRequest> bookIndicesAndQuantity;
    public List<ItemFromTextBatchRequest> novelIndicesAndQuantity;

    public QuoteVariousTextsCommand() {
    }

    public QuoteVariousTextsCommand(String userId, List<ItemFromTextBatchRequest> bookIndicesAndQuantity, List<ItemFromTextBatchRequest> novelIndicesAndQuantity) {
        this.userId = userId;
        this.bookIndicesAndQuantity = bookIndicesAndQuantity;
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ItemFromTextBatchRequest> getBookIndicesAndQuantity() {
        return bookIndicesAndQuantity;
    }

    public void setBookIndicesAndQuantity(List<ItemFromTextBatchRequest> bookIndicesAndQuantity) {
        this.bookIndicesAndQuantity = bookIndicesAndQuantity;
    }

    public List<ItemFromTextBatchRequest> getNovelIndicesAndQuantity() {
        return novelIndicesAndQuantity;
    }

    public void setNovelIndicesAndQuantity(List<ItemFromTextBatchRequest> novelIndicesAndQuantity) {
        this.novelIndicesAndQuantity = novelIndicesAndQuantity;
    }
}
