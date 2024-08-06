package com.libraryproviderbackend.user.values.batchquote;

import com.libraryproviderbackend.generic.IValueObject;

public class Change implements IValueObject<Float> {
    public Float change;

    private Change(Float change) {
        this.change = change;
    }

    @Override
    public Float value() {
        return change;
    }

    public static Change of(Float change){
        return new Change(change);
    }
}
