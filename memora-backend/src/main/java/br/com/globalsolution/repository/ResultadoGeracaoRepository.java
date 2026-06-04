package br.com.globalsolution.repository;

import br.com.globalsolution.model.ResultadoGeracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoGeracaoRepository extends JpaRepository<ResultadoGeracao, Long> {

    Optional<ResultadoGeracao> findBySolicitacaoId(Long solicitacaoId);
}
