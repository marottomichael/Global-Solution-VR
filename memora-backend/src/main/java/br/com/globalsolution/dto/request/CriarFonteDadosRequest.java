package br.com.globalsolution.dto.request;

import br.com.globalsolution.model.enums.TipoFonteDados;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CriarFonteDadosRequest {

    @NotBlank
    private String nome;

    @NotNull
    private TipoFonteDados tipoFonte;

    private String descricao;

    private String urlReferencia;

    @DecimalMin("0")
    @DecimalMax("100")
    private BigDecimal confiabilidadeBase;

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
