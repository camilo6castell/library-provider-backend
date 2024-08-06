package com.libraryproviderbackend.user.values.identities;

import com.libraryproviderbackend.generic.Identity;

public class BatchQuoteId extends Identity {
    public BatchQuoteId(){
        super();
    }
    private BatchQuoteId(String uuid) {
        super(uuid);
    }

    public static BatchQuoteId of(String uuid) {
        return new BatchQuoteId(uuid);
    }
}
