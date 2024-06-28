package com.aluralatam.forohub.domain.topicos;

import com.aluralatam.forohub.domain.autores.Autor;
import com.aluralatam.forohub.domain.topicos.dto.DatosActualizarTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;
    @Enumerated(EnumType.STRING)
    private Estado status;
    @Column(name = "nombre_de_curso")
    private String nombreDeCurso;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Topico(String titulo, String mensaje, LocalDateTime fechaDeCreacion, Estado status, String nombreDeCurso, Autor autor) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaDeCreacion = fechaDeCreacion;
        this.status = status;
        this.nombreDeCurso = nombreDeCurso;
        this.autor = autor;
    }

    public void actualizar(DatosActualizarTopico datosActualizarTopico) {
        var titulo = datosActualizarTopico.titulo();
        var mensaje = datosActualizarTopico.mensaje();
        var nombreDeCurso = datosActualizarTopico.nombreDeCurso();
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (mensaje != null) {
            this.mensaje = mensaje;
        }
        if (nombreDeCurso != null) {
            this.nombreDeCurso = nombreDeCurso;
        }
    }
}
