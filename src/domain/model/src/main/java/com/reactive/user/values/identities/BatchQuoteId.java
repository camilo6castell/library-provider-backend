package com.reactive.user.values.identities;

import com.reactive.generic.Identity;

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
