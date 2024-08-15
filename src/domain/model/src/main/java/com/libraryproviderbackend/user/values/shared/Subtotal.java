package com.libraryproviderbackend.user.values.shared;

import com.libraryproviderbackend.generic.IValueObject;

import java.math.BigDecimal;

public class Subtotal implements IValueObject<BigDecimal> {
    public final BigDecimal total;

    private Subtotal(BigDecimal total){
        this.total = total;
    }

    @Override
    public BigDecimal value() {
        return total;
    }

    public static Subtotal of(BigDecimal subtotal){
        return new Subtotal(subtotal);
    }
}
