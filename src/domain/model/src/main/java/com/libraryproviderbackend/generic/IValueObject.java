package com.libraryproviderbackend.generic;

import java.io.Serializable;

public interface IValueObject<T> extends Serializable {
    T value();
}
