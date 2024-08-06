package com.libraryproviderbackend.user.values.quote;

import com.libraryproviderbackend.generic.IValueObject;

public class Status implements IValueObject<QuoteSatusEnum> {
    public final QuoteSatusEnum status;

    private Status(QuoteSatusEnum status) {
        this.status = status;
    }

    @Override
    public QuoteSatusEnum value() {
        return status;
    }

    public static Status of(QuoteSatusEnum status){
        return new Status(status);
    }
}
