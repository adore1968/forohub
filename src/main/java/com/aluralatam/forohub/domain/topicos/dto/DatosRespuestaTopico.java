package com.aluralatam.forohub.domain.topicos.dto;

import com.aluralatam.forohub.domain.topicos.Estado;
import com.aluralatam.forohub.domain.topicos.Topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        String id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        Estado status,
        String nombreDeCurso,
        String autorCorreoElectronico
) {
    public DatosRespuestaTopico(Topico topico) {
        this(
                String.valueOf(topico.getId()),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                topico.getNombreDeCurso(),
                topico.getAutor().getCorreoElectronico()
        );
    }
}
