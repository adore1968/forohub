package com.aluralatam.forohub.infra.errors.dto;

import jakarta.validation.ValidationException;

public record DatosErrorValidacion(
        String mensaje
) {
    public DatosErrorValidacion(ValidationException e) {
        this(e.getMessage());
    }
}
