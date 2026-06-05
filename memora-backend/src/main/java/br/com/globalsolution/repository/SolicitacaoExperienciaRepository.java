package br.com.globalsolution.repository;

import br.com.globalsolution.model.SolicitacaoExperiencia;
import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.StatusGeracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoExperienciaRepository extends JpaRepository<SolicitacaoExperiencia, Long> {

    List<SolicitacaoExperiencia> findByUsuarioId(Long usuarioId);

    List<SolicitacaoExperiencia> findByCategoriaExperiencia(CategoriaExperiencia categoriaExperiencia);

    List<SolicitacaoExperiencia> findByStatus(StatusGeracao status);

    long countByStatus(StatusGeracao status);
}
