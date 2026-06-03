package br.com.globalsolution.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados_geracao")
public class ResultadoGeracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false, unique = true)
    private SolicitacaoExperiencia solicitacao;

    @Column(name = "descricao_resultado", columnDefinition = "TEXT")
    private String descricaoResultado;

    @Column(name = "narrativa_imersiva", columnDefinition = "TEXT")
    private String narrativaImersiva;

    @Column(name = "url_imagem_preview")
    private String urlImagemPreview;

    @Column(name = "url_modelo_3d")
    private String urlModelo3d;

    @DecimalMin("0")
    @DecimalMax("100")
    @Column(name = "indice_confiabilidade", precision = 5, scale = 2)
    private BigDecimal indiceConfiabilidade;

    @Column(name = "observacoes_tecnicas", columnDefinition = "TEXT")
    private String observacoesTecnicas;

    @NotNull
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitacaoExperiencia getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoExperiencia solicitacao) {
        this.solicitacao = solicitacao;
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
}
