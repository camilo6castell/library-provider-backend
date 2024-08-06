package com.libraryproviderbackend.user.values.shared;

import com.libraryproviderbackend.generic.IValueObject;

public class Total implements IValueObject<Float> {

    public final Float total;

    private Total(Float total) {
        this.total = total;
    }

    @Override
    public Float value() {
        return total;
    }

    public static Total of(Float total){
        return new Total(total);
    }
}
