package br.com.globalsolution.dto.request;

import br.com.globalsolution.model.enums.TipoParametro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CriarParametroSolicitacaoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String valor;

    private String unidade;

    @NotNull
    private TipoParametro tipoParametro;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public TipoParametro getTipoParametro() {
        return tipoParametro;
    }

    public void setTipoParametro(TipoParametro tipoParametro) {
        this.tipoParametro = tipoParametro;
    }
}
