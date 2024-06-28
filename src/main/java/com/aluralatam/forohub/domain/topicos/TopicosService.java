package com.aluralatam.forohub.domain.topicos;

import com.aluralatam.forohub.domain.autores.AutorRepository;
import com.aluralatam.forohub.domain.topicos.dto.DatosActualizarTopico;
import com.aluralatam.forohub.domain.topicos.dto.DatosListadoTopico;
import com.aluralatam.forohub.domain.topicos.dto.DatosRegistroTopico;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TopicosService {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public Topico validarRegistro(DatosRegistroTopico datosRegistroTopico) {
        var autorId = datosRegistroTopico.autorId();
        if (autorId != null && !autorRepository.existsById(autorId)) {
            throw new ValidationException("No existe ningun autor con ese id");
        }

        var titulo = datosRegistroTopico.titulo();
        var mensaje = datosRegistroTopico.mensaje();
        var topicoDuplicado = topicoRepository.existsByTituloAndMensaje(titulo, mensaje);
        if (topicoDuplicado) {
            throw new ValidationException("Ya existe un topico con ese titulo y/o mensaje");
        }

        var autor = autorRepository.getReferenceById(autorId);
        var topico = new Topico(
                titulo,
                mensaje,
                LocalDateTime.now(),
                datosRegistroTopico.status(),
                datosRegistroTopico.nombreDeCurso(),
                autor
        );
        return topicoRepository.save(topico);
    }

    public Page<Topico> obtenerTopicos(Pageable pageable) {
        var topicos = topicoRepository.findAll(pageable);
        return topicos;
    }

    public List<Topico> obtenerTopicosConParam(String curso, Integer año) {
        var topicos = topicoRepository.findByCursoAndAño(curso, año);
        return topicos;
    }

    public Topico obtenerTopico(Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new EntityNotFoundException("No existe ningun topico con ese id");
        }
        return topicoOptional.get();
    }

    public Topico validarActualizar(Long id, DatosActualizarTopico datosActualizarTopico) {
        var topico = obtenerTopico(id);
        topico.actualizar(datosActualizarTopico);
        return topico;
    }

    public void eliminarTopico(Long id) {
        var topico = obtenerTopico(id);
        topicoRepository.deleteById(id);
    }
}
