package br.com.globalsolution.repository;

import br.com.globalsolution.model.FonteDados;
import br.com.globalsolution.model.enums.TipoFonteDados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FonteDadosRepository extends JpaRepository<FonteDados, Long> {

    List<FonteDados> findByTipoFonte(TipoFonteDados tipoFonte);

    @Query(value = "SELECT COUNT(*) FROM solicitacao_fontes_dados WHERE fonte_dados_id = :fonteId", nativeQuery = true)
    long countVinculosComSolicitacao(@Param("fonteId") Long fonteId);
}
