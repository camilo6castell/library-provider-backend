package com.reactive.user.values.identities;

import com.reactive.generic.Identity;

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
