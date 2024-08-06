package com.libraryproviderbackend.user.values.identities;

import com.libraryproviderbackend.generic.Identity;

public class BookQuoteId extends Identity {
    public BookQuoteId(){
        super();
    }
    private BookQuoteId(String uuid) {
        super(uuid);
    }

    public static BookQuoteId of(String uuid) {
        return new BookQuoteId(uuid);
    }
}
