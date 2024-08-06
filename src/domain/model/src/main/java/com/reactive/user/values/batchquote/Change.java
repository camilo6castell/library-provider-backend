package com.reactive.user.values.batchquote;

import com.reactive.generic.IValueObject;

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
