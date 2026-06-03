package br.com.globalsolution.repository;

import br.com.globalsolution.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

    List<Localizacao> findByCidadeContainingIgnoreCase(String cidade);
}
