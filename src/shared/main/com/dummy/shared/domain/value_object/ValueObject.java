package com.dummy.shared.domain.value_object;

public interface ValueObject<T> {

    T value();

    void value(T value);

}