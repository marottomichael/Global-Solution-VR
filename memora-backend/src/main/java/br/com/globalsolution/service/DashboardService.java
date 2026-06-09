package br.com.globalsolution.service;

import br.com.globalsolution.dto.response.DashboardResponse;
import br.com.globalsolution.dto.response.MetricaData;
import br.com.globalsolution.exception.RecursoNaoEncontradoException;
import br.com.globalsolution.model.MetricaResultado;
import br.com.globalsolution.model.SolicitacaoExperiencia;
import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.repository.MetricaResultadoRepository;
import br.com.globalsolution.repository.SolicitacaoExperienciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardService {

    private final SolicitacaoExperienciaRepository solicitacaoRepository;
    private final MetricaResultadoRepository metricaRepository;

    public DashboardService(
            SolicitacaoExperienciaRepository solicitacaoRepository,
            MetricaResultadoRepository metricaRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.metricaRepository = metricaRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getAmazoniaDashboardData() {
        SolicitacaoExperiencia solicitacao = solicitacaoRepository
                .findByCategoriaExperiencia(CategoriaExperiencia.MONITORAMENTO_AMBIENTAL)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Dashboard Espacial da Amazônia não encontrado. Reinicie o backend para executar o seed automático."));

        List<MetricaData> metricas = metricaRepository.findBySolicitacaoId(solicitacao.getId()).stream()
                .map(this::toMetricaData)
                .toList();

        return new DashboardResponse(
                solicitacao.getTitulo(),
                solicitacao.getDescricaoUsuario(),
                metricas);
    }

    private MetricaData toMetricaData(MetricaResultado metrica) {
        return new MetricaData(
                metrica.getNome(),
                metrica.getValor().doubleValue(),
                metrica.getUnidade(),
                metrica.getAnoReferencia());
    }
}
