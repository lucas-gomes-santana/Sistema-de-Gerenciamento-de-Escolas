package com.br.api.valueobject;

import java.util.Objects;

public final class RG {
    private final String value;

    public RG(String value) {
        if (value == null || value.length() < 5) {
            throw new IllegalArgumentException("RG inválido: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RG rg = (RG) o;
        return value.equals(rg.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
} 