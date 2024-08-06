package com.libraryproviderbackend.user.values.identities;

import com.libraryproviderbackend.generic.Identity;

public class UserId extends Identity {

    public UserId(String uuid) {
        super(uuid);
    }

    public static UserId of(String uuid) {
        return new UserId(uuid);
    }
}
