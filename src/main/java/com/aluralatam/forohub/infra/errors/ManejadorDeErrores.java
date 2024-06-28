package com.aluralatam.forohub.infra.errors;

import com.aluralatam.forohub.infra.errors.dto.DatosError400;
import com.aluralatam.forohub.infra.errors.dto.DatosErrorValidacion;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejadorDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity manejarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity manejarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream()
                .map(DatosError400::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity manejarValidaciones(ValidationException e) {
        return ResponseEntity.badRequest().body(new DatosErrorValidacion(e));
    }
}
