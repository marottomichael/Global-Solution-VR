package br.com.globalsolution.model;

import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.NivelDetalhamento;
import br.com.globalsolution.model.enums.StatusGeracao;
import br.com.globalsolution.model.enums.TipoTemporal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "solicitacoes_experiencia")
public class SolicitacaoExperiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localizacao_id", nullable = false)
    private Localizacao localizacao;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(name = "descricao_usuario", columnDefinition = "TEXT")
    private String descricaoUsuario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_experiencia", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private CategoriaExperiencia categoriaExperiencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_temporal", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private TipoTemporal tipoTemporal;

    @Column(name = "ano_referencia")
    private Integer anoReferencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_detalhamento", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private NivelDetalhamento nivelDetalhamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private StatusGeracao status;

    @Column(name = "prompt_gerado", columnDefinition = "TEXT")
    private String promptGerado;

    @NotNull
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @ManyToMany
    @JoinTable(
            name = "solicitacao_fontes_dados",
            joinColumns = @JoinColumn(name = "solicitacao_id"),
            inverseJoinColumns = @JoinColumn(name = "fonte_dados_id")
    )
    private Set<FonteDados> fontesDados = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
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

    public Set<FonteDados> getFontesDados() {
        return fontesDados;
    }

    public void setFontesDados(Set<FonteDados> fontesDados) {
        this.fontesDados = fontesDados;
    }
}
