package br.com.globalsolution.dto.response;

import br.com.globalsolution.model.enums.TipoFonteDados;

import java.math.BigDecimal;

public class FonteDadosResponse {

    private Long id;
    private String nome;
    private TipoFonteDados tipoFonte;
    private String descricao;
    private String urlReferencia;
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
