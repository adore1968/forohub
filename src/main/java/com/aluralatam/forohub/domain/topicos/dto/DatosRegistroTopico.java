package com.aluralatam.forohub.domain.topicos.dto;

import com.aluralatam.forohub.domain.topicos.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        Estado status,
        @NotBlank
        String nombreDeCurso,
        @NotNull
        Long autorId
) {
}
