package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.Identity;

public class TextId extends Identity {
    public TextId(){
        super();
    }
    private TextId(String uuid) {
        super(uuid);
    }

    public static TextId of(String uuid) {
        return new TextId(uuid);
    }
}
