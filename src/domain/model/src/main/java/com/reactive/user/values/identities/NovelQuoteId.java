package com.reactive.user.values.identities;

import com.reactive.generic.Identity;

public class NovelQuoteId extends Identity {
    public NovelQuoteId(){
        super();
    }
    private NovelQuoteId(String uuid) {
        super(uuid);
    }

    public static NovelQuoteId of(String uuid) {
        return new NovelQuoteId(uuid);
    }
}
