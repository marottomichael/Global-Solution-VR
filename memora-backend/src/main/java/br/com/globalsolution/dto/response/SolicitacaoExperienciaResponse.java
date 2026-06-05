package br.com.globalsolution.dto.response;

import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.NivelDetalhamento;
import br.com.globalsolution.model.enums.StatusGeracao;
import br.com.globalsolution.model.enums.TipoTemporal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoExperienciaResponse {

    private Long id;
    private Long usuarioId;
    private String nomeUsuario;
    private LocalizacaoResponse localizacao;
    private String titulo;
    private String descricaoUsuario;
    private CategoriaExperiencia categoriaExperiencia;
    private TipoTemporal tipoTemporal;
    private Integer anoReferencia;
    private NivelDetalhamento nivelDetalhamento;
    private StatusGeracao status;
    private String promptGerado;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataProcessamento;
    private List<ParametroSolicitacaoResponse> parametros = new ArrayList<>();
    private List<FonteDadosResponse> fontesDados = new ArrayList<>();
    private ResultadoGeracaoResponse resultado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalizacaoResponse getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(LocalizacaoResponse localizacao) {
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

    public StatusGeracao getStatus() {
        return status;
    }

    public void setStatus(StatusGeracao status) {
        this.status = status;
    }

    public String getPromptGerado() {
        return promptGerado;
    }

    public void setPromptGerado(String promptGerado) {
        this.promptGerado = promptGerado;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public List<ParametroSolicitacaoResponse> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroSolicitacaoResponse> parametros) {
        this.parametros = parametros;
    }

    public List<FonteDadosResponse> getFontesDados() {
        return fontesDados;
    }

    public void setFontesDados(List<FonteDadosResponse> fontesDados) {
        this.fontesDados = fontesDados;
    }

    public ResultadoGeracaoResponse getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoGeracaoResponse resultado) {
        this.resultado = resultado;
    }
}
