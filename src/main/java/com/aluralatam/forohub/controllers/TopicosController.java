package com.aluralatam.forohub.controllers;

import com.aluralatam.forohub.domain.topicos.TopicosService;
import com.aluralatam.forohub.domain.topicos.dto.*;
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

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        var topico = topicosService.validarRegistro(datosRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosDetallesTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 5, sort = "titulo") Pageable pageable) {
        var topicos = topicosService.obtenerTopicos(pageable).map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/filtrar1")
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicosPorFecha(@PageableDefault(size = 10, sort = "fechaDeCreacion") Pageable pageable) {
        var topicos = topicosService.obtenerTopicos(pageable).map(DatosListadoTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/filtrar2")
    public ResponseEntity<List<DatosListadoTopico>> listarTopicosPorCursoYAño(@RequestParam String curso, @RequestParam Integer año) {
        var topicos = topicosService.obtenerTopicosConParam(curso, año).stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> detallarTopico(@PathVariable Long id) {
        var topico = topicosService.obtenerTopico(id);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datosActualizarTopico) {
        var topico = topicosService.validarActualizar(id, datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        topicosService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
