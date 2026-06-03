package br.com.globalsolution.repository;

import br.com.globalsolution.model.ParametroSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParametroSolicitacaoRepository extends JpaRepository<ParametroSolicitacao, Long> {

    List<ParametroSolicitacao> findBySolicitacaoId(Long solicitacaoId);
}
