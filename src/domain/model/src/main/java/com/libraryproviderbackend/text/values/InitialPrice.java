package com.libraryproviderbackend.text.values;

import com.libraryproviderbackend.generic.IValueObject;

public class InitialPrice implements IValueObject<Float> {
    private final Float initialPrice;

    private InitialPrice(Float initialPrice) {
        if (initialPrice == null) {
            throw new IllegalArgumentException("InitialPrice cannot be null");
        }
        if (initialPrice < 0) {
            throw new IllegalArgumentException("InitialPrice cannot be negative");
        }
        this.initialPrice = initialPrice;
    }

    @Override
    public Float value() {
        return initialPrice;
    }

    /**
     * Factory method to create an InitialPrice object.
     *
     * @param initialPrice The Float value of the initial price.
     * @return A new InitialPrice object.
     * @throws IllegalArgumentException if the initialPrice is null or negative.
     */
    public static InitialPrice of(Float initialPrice) {
        return new InitialPrice(initialPrice);
    }

    @Override
    public String toString() {
        return initialPrice.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitialPrice that = (InitialPrice) o;
        return initialPrice.equals(that.initialPrice);
    }

    @Override
    public int hashCode() {
        return initialPrice.hashCode();
    }
}


//public class InitialPrice implements IValueObject<Float> {
//    public final Float initialPrice;
//
//    private InitialPrice(Float initialPrice) {
//        this.initialPrice = initialPrice;
//    }
//
//    @Override
//    public Float value() {
//        return initialPrice;
//    }
//
//    public static InitialPrice of(Float initialPrice){
//        return new InitialPrice(initialPrice);
//    }
//}
