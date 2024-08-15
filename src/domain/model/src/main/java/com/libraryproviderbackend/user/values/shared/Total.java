package com.libraryproviderbackend.user.values.shared;

import com.libraryproviderbackend.generic.IValueObject;

import java.math.BigDecimal;

public class Total implements IValueObject<BigDecimal> {

    public final BigDecimal total;

    private Total(BigDecimal total) {
        this.total = total;
    }

    @Override
    public BigDecimal value() {
        return total;
    }

    public static Total of(BigDecimal total){
        return new Total(total);
    }
}
