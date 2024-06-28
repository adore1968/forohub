package com.aluralatam.forohub.domain.topicos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.nombreDeCurso = :curso AND FUNCTION('YEAR', t.fechaDeCreacion) = :año")
    List<Topico> findByCursoAndAño(String curso, Integer año);
}
