package br.com.globalsolution.dto.response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EstatisticaResponse {

    private Long totalSolicitacoes;
    private Long totalConcluidas;
    private BigDecimal mediaAvaliacoes;
    private BigDecimal mediaConfiabilidade;
    private Map<String, Long> totalPorCategoria = new HashMap<>();

    public Long getTotalSolicitacoes() {
        return totalSolicitacoes;
    }

    public void setTotalSolicitacoes(Long totalSolicitacoes) {
        this.totalSolicitacoes = totalSolicitacoes;
    }

    public Long getTotalConcluidas() {
        return totalConcluidas;
    }

    public void setTotalConcluidas(Long totalConcluidas) {
        this.totalConcluidas = totalConcluidas;
    }

    public BigDecimal getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(BigDecimal mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public BigDecimal getMediaConfiabilidade() {
        return mediaConfiabilidade;
    }

    public void setMediaConfiabilidade(BigDecimal mediaConfiabilidade) {
        this.mediaConfiabilidade = mediaConfiabilidade;
    }

    public Map<String, Long> getTotalPorCategoria() {
        return totalPorCategoria;
    }

    public void setTotalPorCategoria(Map<String, Long> totalPorCategoria) {
        this.totalPorCategoria = totalPorCategoria;
    }
}
