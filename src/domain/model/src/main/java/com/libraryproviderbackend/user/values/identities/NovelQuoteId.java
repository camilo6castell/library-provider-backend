package com.libraryproviderbackend.user.values.identities;

import com.libraryproviderbackend.generic.Identity;

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
