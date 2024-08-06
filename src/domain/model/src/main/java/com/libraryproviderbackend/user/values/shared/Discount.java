package com.libraryproviderbackend.user.values.shared;

import com.libraryproviderbackend.generic.IValueObject;

public class Discount implements IValueObject<DiscountsEnum> {
    public DiscountsEnum discount;

    private Discount(DiscountsEnum discount) {
        this.discount = discount;
    }

    @Override
    public DiscountsEnum value() {
        return discount;
    }

    public static Discount of(DiscountsEnum discount){
        return new Discount(discount);
    }
}
