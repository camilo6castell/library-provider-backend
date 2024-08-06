package com.reactive.user.values.identities;

import com.reactive.generic.Identity;

public class UserId extends Identity {

    public UserId(String uuid) {
        super(uuid);
    }

    public static UserId of(String uuid) {
        return new UserId(uuid);
    }
}
