package br.com.globalsolution.model;

import br.com.globalsolution.model.enums.TipoFonteDados;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "fontes_dados")
public class FonteDados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_fonte", nullable = false)
    private TipoFonteDados tipoFonte;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "url_referencia")
    private String urlReferencia;

    @DecimalMin("0")
    @DecimalMax("100")
    @Column(name = "confiabilidade_base", precision = 5, scale = 2)
    private BigDecimal confiabilidadeBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoFonteDados getTipoFonte() {
        return tipoFonte;
    }

    public void setTipoFonte(TipoFonteDados tipoFonte) {
        this.tipoFonte = tipoFonte;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlReferencia() {
        return urlReferencia;
    }

    public void setUrlReferencia(String urlReferencia) {
        this.urlReferencia = urlReferencia;
    }

    public BigDecimal getConfiabilidadeBase() {
        return confiabilidadeBase;
    }

    public void setConfiabilidadeBase(BigDecimal confiabilidadeBase) {
        this.confiabilidadeBase = confiabilidadeBase;
    }
}
