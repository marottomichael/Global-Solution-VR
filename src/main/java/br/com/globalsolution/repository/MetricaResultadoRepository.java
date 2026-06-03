package br.com.globalsolution.repository;

import br.com.globalsolution.model.MetricaResultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetricaResultadoRepository extends JpaRepository<MetricaResultado, Long> {

    List<MetricaResultado> findByResultadoId(Long resultadoId);
}
