package br.com.globalsolution.repository;

import br.com.globalsolution.model.MetricaResultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricaResultadoRepository extends JpaRepository<MetricaResultado, Long> {

    List<MetricaResultado> findByResultadoId(Long resultadoId);

    @Query("""
            SELECT m FROM MetricaResultado m
            JOIN m.resultado r
            WHERE r.solicitacao.id = :solicitacaoId
            ORDER BY m.nome, m.anoReferencia
            """)
    List<MetricaResultado> findBySolicitacaoId(@Param("solicitacaoId") Long solicitacaoId);
}
