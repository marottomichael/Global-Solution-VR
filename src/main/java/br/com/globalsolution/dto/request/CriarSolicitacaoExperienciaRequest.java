package br.com.globalsolution.dto.request;

import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.NivelDetalhamento;
import br.com.globalsolution.model.enums.TipoTemporal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CriarSolicitacaoExperienciaRequest {

    @NotNull
    private Long usuarioId;

    @NotNull
    @Valid
    private CriarLocalizacaoRequest localizacao;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricaoUsuario;

    @NotNull
    private CategoriaExperiencia categoriaExperiencia;

    @NotNull
    private TipoTemporal tipoTemporal;

    private Integer anoReferencia;

    @NotNull
    private NivelDetalhamento nivelDetalhamento;

    @Valid
    private List<CriarParametroSolicitacaoRequest> parametros = new ArrayList<>();

    private List<Long> fontesDadosIds = new ArrayList<>();

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public CriarLocalizacaoRequest getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(CriarLocalizacaoRequest localizacao) {
        this.localizacao = localizacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricaoUsuario() {
        return descricaoUsuario;
    }

    public void setDescricaoUsuario(String descricaoUsuario) {
        this.descricaoUsuario = descricaoUsuario;
    }

    public CategoriaExperiencia getCategoriaExperiencia() {
        return categoriaExperiencia;
    }

    public void setCategoriaExperiencia(CategoriaExperiencia categoriaExperiencia) {
        this.categoriaExperiencia = categoriaExperiencia;
    }

    public TipoTemporal getTipoTemporal() {
        return tipoTemporal;
    }

    public void setTipoTemporal(TipoTemporal tipoTemporal) {
        this.tipoTemporal = tipoTemporal;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public NivelDetalhamento getNivelDetalhamento() {
        return nivelDetalhamento;
    }

    public void setNivelDetalhamento(NivelDetalhamento nivelDetalhamento) {
        this.nivelDetalhamento = nivelDetalhamento;
    }

    public List<CriarParametroSolicitacaoRequest> getParametros() {
        return parametros;
    }

    public void setParametros(List<CriarParametroSolicitacaoRequest> parametros) {
        this.parametros = parametros;
    }

    public List<Long> getFontesDadosIds() {
        return fontesDadosIds;
    }

    public void setFontesDadosIds(List<Long> fontesDadosIds) {
        this.fontesDadosIds = fontesDadosIds;
    }
}
