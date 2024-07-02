package com.aluralatam.forohub.controllers;

import com.aluralatam.forohub.domain.topicos.TopicosService;
import com.aluralatam.forohub.domain.topicos.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
    @Autowired
    private TopicosService topicosService;

    @Tag(name = "post", description = "Metodos POST de la API de Topicos")
    @Operation(summary = "Registrar un topico", description = "Registrar un topico. La respuesta es ese mismo topico creado con su respectivo id, titulo, mensaje, fecha de creacion, status, nombre de curso y el id del autor")
    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        var topico = topicosService.validarRegistro(datosRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosDetallesTopico(topico));
    }

    @Tag(name = "get", description = "Metodos GET de la API de Topicos")
    @Operation(summary = "Listar topicos", description = "Listar 5 topicos ordenados por su titulo. La respuesta es una lista de topicos con su respectivo id, titulo, mensaje, fecha de creacion, status, nombre de curso y el correo electronico del autor")
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 5, sort = "titulo") Pageable pageable) {
        var topicos = topicosService.obtenerTopicos(pageable).map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @Tag(name = "get", description = "Metodos GET de la API de Topicos")
    @Operation(summary = "Listar topicos por su fecha", description = "Listar 10 topicos ordenados por su fecha de creacion. La respuesta es una lista de topicos con su respectivo id, titulo, mensaje, fecha de creacion, status, nombre de curso y el correo electronico del autor")
    @GetMapping("/filtrar1")
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicosPorFecha(@PageableDefault(size = 10, sort = "fechaDeCreacion") Pageable pageable) {
        var topicos = topicosService.obtenerTopicos(pageable).map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @Tag(name = "get", description = "Metodos GET de la API de Topicos")
    @Operation(summary = "Listar topicos por curso y año", description = "Listar topicos ordenados por un año especifico y nombre de curso. La respuesta es una lista de topicos con su respectivo id, titulo, mensaje, fecha de creacion, status, nombre de curso y el correo electronico del autor")
    @GetMapping("/filtrar2")
    public ResponseEntity<List<DatosListadoTopico>> listarTopicosPorCursoYAño(@RequestParam String curso, @RequestParam Integer año) {
        var topicos = topicosService.obtenerTopicosConParam(curso, año).stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicos);
    }

    @Tag(name = "get", description = "Metodos GET de la API de Topicos")
    @Operation(summary = "Detallar topico", description = "Encontrar y detallar topico por su id. La respuesta es el topico encontrado con su respectivo id, titulo, mensaje, fecha de creacion, status, nombre de curso y el correo electronico del autor")
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> detallarTopico(@PathVariable Long id) {
        var topico = topicosService.obtenerTopico(id);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @Tag(name = "put", description = "Metodos PUT de la API de Topicos")
    @Operation(summary = "Actualizar topico", description = "Encontrar y actualizar topico por su id. La respuesta es el topico actualizado con su respectivo id, titulo, mensaje, fecha de creacion, status, nombre de curso y el correo electronico del autor")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datosActualizarTopico) {
        var topico = topicosService.validarActualizar(id, datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @Tag(name = "delete", description = "Metodos DELETE de la API de Topicos")
    @Operation(summary = "Eliminar", description = "Encontrar y eliminar topico por su id")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        topicosService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
