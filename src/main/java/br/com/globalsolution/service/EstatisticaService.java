package br.com.globalsolution.service;

import br.com.globalsolution.dto.response.EstatisticaResponse;
import br.com.globalsolution.model.Avaliacao;
import br.com.globalsolution.model.ResultadoGeracao;
import br.com.globalsolution.model.SolicitacaoExperiencia;
import br.com.globalsolution.model.enums.StatusGeracao;
import br.com.globalsolution.repository.AvaliacaoRepository;
import br.com.globalsolution.repository.ResultadoGeracaoRepository;
import br.com.globalsolution.repository.SolicitacaoExperienciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstatisticaService {

    private final SolicitacaoExperienciaRepository solicitacaoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ResultadoGeracaoRepository resultadoRepository;

    public EstatisticaService(SolicitacaoExperienciaRepository solicitacaoRepository,
                              AvaliacaoRepository avaliacaoRepository,
                              ResultadoGeracaoRepository resultadoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.resultadoRepository = resultadoRepository;
    }

    @Transactional(readOnly = true)
    public EstatisticaResponse obterResumo() {
        List<SolicitacaoExperiencia> solicitacoes = solicitacaoRepository.findAll();
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();
        List<ResultadoGeracao> resultados = resultadoRepository.findAll();

        EstatisticaResponse response = new EstatisticaResponse();
        response.setTotalSolicitacoes((long) solicitacoes.size());
        response.setTotalConcluidas(solicitacaoRepository.countByStatus(StatusGeracao.CONCLUIDA));
        response.setMediaAvaliacoes(calcularMediaAvaliacoes(avaliacoes));
        response.setMediaConfiabilidade(calcularMediaConfiabilidade(resultados));
        response.setTotalPorCategoria(agruparPorCategoria(solicitacoes));
        return response;
    }

    private BigDecimal calcularMediaAvaliacoes(List<Avaliacao> avaliacoes) {
        if (avaliacoes.isEmpty()) {
            return BigDecimal.ZERO;
        }
        double media = avaliacoes.stream()
                .mapToInt(Avaliacao::getNota)
                .average()
                .orElse(0.0);
        return BigDecimal.valueOf(media).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularMediaConfiabilidade(List<ResultadoGeracao> resultados) {
        if (resultados.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal soma = resultados.stream()
                .map(ResultadoGeracao::getIndiceConfiabilidade)
                .filter(indice -> indice != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long total = resultados.stream()
                .filter(r -> r.getIndiceConfiabilidade() != null)
                .count();
        if (total == 0) {
            return BigDecimal.ZERO;
        }
        return soma.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
    }

    private Map<String, Long> agruparPorCategoria(List<SolicitacaoExperiencia> solicitacoes) {
        Map<String, Long> totalPorCategoria = new HashMap<>();
        for (SolicitacaoExperiencia solicitacao : solicitacoes) {
            String categoria = solicitacao.getCategoriaExperiencia().name();
            totalPorCategoria.merge(categoria, 1L, Long::sum);
        }
        return totalPorCategoria;
    }
}
