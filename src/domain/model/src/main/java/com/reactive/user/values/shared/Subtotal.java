package com.reactive.user.values.shared;

import com.reactive.generic.IValueObject;

public class Subtotal implements IValueObject<Float> {
    public final Float total;

    private Subtotal(Float total){
        this.total = total;
    }

    @Override
    public Float value() {
        return total;
    }

    public static Subtotal of(Float subtotal){
        return new Subtotal(subtotal);
    }
}
