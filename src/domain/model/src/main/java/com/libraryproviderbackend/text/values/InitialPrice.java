package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.IValueObject;

public class InitialPrice implements IValueObject<Float> {
    public final Float initialPrice;

    private InitialPrice(Float initialPrice) {
        this.initialPrice = initialPrice;
    }

    @Override
    public Float value() {
        return initialPrice;
    }

    public static InitialPrice of(Float initialPrice){
        return new InitialPrice(initialPrice);
    }
}
