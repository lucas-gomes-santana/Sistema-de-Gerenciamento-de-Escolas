package com.br.api.valueobject;

import java.util.regex.Pattern;
import java.util.Objects;

public final class Telefone {
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\(\\d{2}\\) \\d{5}-\\d{4}$");
    private final String value;

    public Telefone(String value) {
        if (value == null || !TELEFONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Telefone inválido: " + value);
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
        Telefone telefone = (Telefone) o;
        return value.equals(telefone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
} 