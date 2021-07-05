package com.dummy.shared.domain.value_object;

import java.io.Serializable;

public abstract class TypeValueObject<T> implements ValueObject<T>, Serializable {

    private T value;

    public TypeValueObject() {
        super();
    }

    public TypeValueObject(T value) {
        super();
        this.value = value;
    }

    public TypeValueObject(ValueObject<T> valueObject) {
        super();
        this.value = valueObject.value();
    }

    public final T value() {
        return value;
    }

    public final void value(T value) {
        this.value = value;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TypeValueObject<?> other = (TypeValueObject<?>) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

    @Override
    public final String toString() {
        return value.toString();
    }
}
