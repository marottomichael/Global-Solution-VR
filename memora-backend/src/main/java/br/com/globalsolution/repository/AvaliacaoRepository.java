package br.com.globalsolution.repository;

import br.com.globalsolution.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findBySolicitacaoId(Long solicitacaoId);

    List<Avaliacao> findByUsuarioId(Long usuarioId);
}
