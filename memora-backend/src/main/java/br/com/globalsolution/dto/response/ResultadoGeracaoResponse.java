package br.com.globalsolution.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResultadoGeracaoResponse {

    private Long id;
    private Long solicitacaoId;
    private String descricaoResultado;
    private String narrativaImersiva;
    private String urlImagemPreview;
    private String urlModelo3d;
    private BigDecimal indiceConfiabilidade;
    private String observacoesTecnicas;
    private LocalDateTime dataCriacao;
    private List<MetricaResultadoResponse> metricas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(Long solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public String getDescricaoResultado() {
        return descricaoResultado;
    }

    public void setDescricaoResultado(String descricaoResultado) {
        this.descricaoResultado = descricaoResultado;
    }

    public String getNarrativaImersiva() {
        return narrativaImersiva;
    }

    public void setNarrativaImersiva(String narrativaImersiva) {
        this.narrativaImersiva = narrativaImersiva;
    }

    public String getUrlImagemPreview() {
        return urlImagemPreview;
    }

    public void setUrlImagemPreview(String urlImagemPreview) {
        this.urlImagemPreview = urlImagemPreview;
    }

    public String getUrlModelo3d() {
        return urlModelo3d;
    }

    public void setUrlModelo3d(String urlModelo3d) {
        this.urlModelo3d = urlModelo3d;
    }

    public BigDecimal getIndiceConfiabilidade() {
        return indiceConfiabilidade;
    }

    public void setIndiceConfiabilidade(BigDecimal indiceConfiabilidade) {
        this.indiceConfiabilidade = indiceConfiabilidade;
    }

    public String getObservacoesTecnicas() {
        return observacoesTecnicas;
    }

    public void setObservacoesTecnicas(String observacoesTecnicas) {
        this.observacoesTecnicas = observacoesTecnicas;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<MetricaResultadoResponse> getMetricas() {
        return metricas;
    }

    public void setMetricas(List<MetricaResultadoResponse> metricas) {
        this.metricas = metricas;
    }
}
