package br.com.globalsolution.service;

import br.com.globalsolution.dto.request.CriarLocalizacaoRequest;
import br.com.globalsolution.dto.request.CriarParametroSolicitacaoRequest;
import br.com.globalsolution.dto.request.CriarSolicitacaoExperienciaRequest;
import br.com.globalsolution.dto.response.FonteDadosResponse;
import br.com.globalsolution.dto.response.LocalizacaoResponse;
import br.com.globalsolution.dto.response.MetricaResultadoResponse;
import br.com.globalsolution.dto.response.ParametroSolicitacaoResponse;
import br.com.globalsolution.dto.response.ResultadoGeracaoResponse;
import br.com.globalsolution.dto.response.SolicitacaoExperienciaResponse;
import br.com.globalsolution.exception.RecursoNaoEncontradoException;
import br.com.globalsolution.exception.RegraNegocioException;
import br.com.globalsolution.model.FonteDados;
import br.com.globalsolution.model.Localizacao;
import br.com.globalsolution.model.MetricaResultado;
import br.com.globalsolution.model.ParametroSolicitacao;
import br.com.globalsolution.model.ResultadoGeracao;
import br.com.globalsolution.model.SolicitacaoExperiencia;
import br.com.globalsolution.model.Usuario;
import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.StatusGeracao;
import br.com.globalsolution.repository.FonteDadosRepository;
import br.com.globalsolution.repository.LocalizacaoRepository;
import br.com.globalsolution.repository.MetricaResultadoRepository;
import br.com.globalsolution.repository.ParametroSolicitacaoRepository;
import br.com.globalsolution.repository.ResultadoGeracaoRepository;
import br.com.globalsolution.repository.SolicitacaoExperienciaRepository;
import br.com.globalsolution.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SolicitacaoExperienciaService {

    private static final BigDecimal CONFIABILIDADE_BASE = new BigDecimal("70.0");

    private final SolicitacaoExperienciaRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalizacaoRepository localizacaoRepository;
    private final ParametroSolicitacaoRepository parametroRepository;
    private final FonteDadosRepository fonteDadosRepository;
    private final ResultadoGeracaoRepository resultadoRepository;
    private final MetricaResultadoRepository metricaRepository;

    public SolicitacaoExperienciaService(SolicitacaoExperienciaRepository solicitacaoRepository,
                                         UsuarioRepository usuarioRepository,
                                         LocalizacaoRepository localizacaoRepository,
                                         ParametroSolicitacaoRepository parametroRepository,
                                         FonteDadosRepository fonteDadosRepository,
                                         ResultadoGeracaoRepository resultadoRepository,
                                         MetricaResultadoRepository metricaRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.parametroRepository = parametroRepository;
        this.fonteDadosRepository = fonteDadosRepository;
        this.resultadoRepository = resultadoRepository;
        this.metricaRepository = metricaRepository;
    }

    @Transactional
    public SolicitacaoExperienciaResponse criar(CriarSolicitacaoExperienciaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Localizacao localizacao = localizacaoRepository.save(criarLocalizacao(request.getLocalizacao()));

        SolicitacaoExperiencia solicitacao = new SolicitacaoExperiencia();
        solicitacao.setUsuario(usuario);
        solicitacao.setLocalizacao(localizacao);
        solicitacao.setTitulo(request.getTitulo());
        solicitacao.setDescricaoUsuario(request.getDescricaoUsuario());
        solicitacao.setCategoriaExperiencia(request.getCategoriaExperiencia());
        solicitacao.setTipoTemporal(request.getTipoTemporal());
        solicitacao.setAnoReferencia(request.getAnoReferencia());
        solicitacao.setNivelDetalhamento(request.getNivelDetalhamento());
        solicitacao.setStatus(StatusGeracao.SOLICITADA);
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        solicitacao = solicitacaoRepository.save(solicitacao);

        List<ParametroSolicitacao> parametros = salvarParametros(solicitacao, request.getParametros());
        Set<FonteDados> fontes = associarFontes(solicitacao, request.getFontesDadosIds());

        solicitacao.setPromptGerado(montarPrompt(solicitacao, localizacao, parametros));
        solicitacao.setStatus(StatusGeracao.PROCESSANDO);
        solicitacaoRepository.save(solicitacao);

        BigDecimal indiceConfiabilidade = calcularIndiceConfiabilidade(fontes, parametros.size());
        ResultadoGeracao resultado = gerarResultadoSimulado(solicitacao, localizacao, indiceConfiabilidade);
        resultado = resultadoRepository.save(resultado);

        List<MetricaResultado> metricas = criarMetricasSimuladas(resultado, solicitacao.getCategoriaExperiencia());
        metricaRepository.saveAll(metricas);

        solicitacao.setStatus(StatusGeracao.CONCLUIDA);
        solicitacao.setDataProcessamento(LocalDateTime.now());
        solicitacaoRepository.save(solicitacao);

        return montarResponse(solicitacao);
    }

    @Transactional(readOnly = true)
    public SolicitacaoExperienciaResponse buscarPorId(Long id) {
        SolicitacaoExperiencia solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada"));
        return montarResponse(solicitacao);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoExperienciaResponse> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }
        return solicitacaoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::montarResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoExperienciaResponse> listarPorCategoria(CategoriaExperiencia categoriaExperiencia) {
        return solicitacaoRepository.findByCategoriaExperiencia(categoriaExperiencia).stream()
                .map(this::montarResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoExperienciaResponse> listarPorStatus(StatusGeracao status) {
        return solicitacaoRepository.findByStatus(status).stream()
                .map(this::montarResponse)
                .toList();
    }

    @Transactional
    public SolicitacaoExperienciaResponse atualizarStatus(Long id, StatusGeracao status) {
        if (status == null) {
            throw new RegraNegocioException("Status inválido");
        }

        SolicitacaoExperiencia solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada"));

        solicitacao.setStatus(status);
        if (status == StatusGeracao.CONCLUIDA || status == StatusGeracao.ERRO) {
            solicitacao.setDataProcessamento(LocalDateTime.now());
        }

        solicitacaoRepository.save(solicitacao);
        return montarResponse(solicitacao);
    }

    private Localizacao criarLocalizacao(CriarLocalizacaoRequest request) {
        Localizacao localizacao = new Localizacao();
        localizacao.setNomeReferencia(request.getNomeReferencia());
        localizacao.setLatitude(request.getLatitude());
        localizacao.setLongitude(request.getLongitude());
        localizacao.setPais(request.getPais());
        localizacao.setCidade(request.getCidade());
        localizacao.setDescricao(request.getDescricao());
        return localizacao;
    }

    private List<ParametroSolicitacao> salvarParametros(SolicitacaoExperiencia solicitacao,
                                                        List<CriarParametroSolicitacaoRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return List.of();
        }
        List<ParametroSolicitacao> parametros = new ArrayList<>();
        for (CriarParametroSolicitacaoRequest req : requests) {
            ParametroSolicitacao parametro = new ParametroSolicitacao();
            parametro.setSolicitacao(solicitacao);
            parametro.setNome(req.getNome());
            parametro.setValor(req.getValor());
            parametro.setUnidade(req.getUnidade());
            parametro.setTipoParametro(req.getTipoParametro());
            parametros.add(parametroRepository.save(parametro));
        }
        return parametros;
    }

    private Set<FonteDados> associarFontes(SolicitacaoExperiencia solicitacao, List<Long> fontesIds) {
        if (fontesIds == null || fontesIds.isEmpty()) {
            return Set.of();
        }
        Set<FonteDados> fontes = new HashSet<>();
        for (Long fonteId : fontesIds) {
            FonteDados fonte = fonteDadosRepository.findById(fonteId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Fonte de dados não encontrada: " + fonteId));
            fontes.add(fonte);
        }
        solicitacao.setFontesDados(fontes);
        solicitacaoRepository.save(solicitacao);
        return fontes;
    }

    private String montarPrompt(SolicitacaoExperiencia solicitacao, Localizacao localizacao,
                                List<ParametroSolicitacao> parametros) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Gerar uma experiência imersiva para ")
                .append(localizacao.getNomeReferencia());

        if (localizacao.getCidade() != null && !localizacao.getCidade().isBlank()) {
            prompt.append(" (").append(localizacao.getCidade()).append(")");
        }

        prompt.append(", considerando a categoria ")
                .append(solicitacao.getCategoriaExperiencia())
                .append(", perspectiva temporal ")
                .append(solicitacao.getTipoTemporal());

        if (solicitacao.getAnoReferencia() != null) {
            prompt.append(" e ano de referência ").append(solicitacao.getAnoReferencia());
        }

        prompt.append(", nível de detalhamento ")
                .append(solicitacao.getNivelDetalhamento())
                .append(". Título: ").append(solicitacao.getTitulo())
                .append(". Descrição do usuário: ").append(solicitacao.getDescricaoUsuario());

        if (!parametros.isEmpty()) {
            String params = parametros.stream()
                    .map(p -> p.getNome() + "=" + p.getValor()
                            + (p.getUnidade() != null ? " " + p.getUnidade() : ""))
                    .collect(Collectors.joining(", "));
            prompt.append(". Parâmetros: ").append(params);
        }

        prompt.append(". A simulação deve priorizar uma narrativa visual coerente e dados conceituais para apresentação.");
        return prompt.toString();
    }

    private BigDecimal calcularIndiceConfiabilidade(Set<FonteDados> fontes, int quantidadeParametros) {
        BigDecimal indice;

        if (fontes.isEmpty()) {
            indice = CONFIABILIDADE_BASE;
        } else {
            BigDecimal soma = fontes.stream()
                    .map(f -> f.getConfiabilidadeBase() != null ? f.getConfiabilidadeBase() : CONFIABILIDADE_BASE)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            indice = soma.divide(BigDecimal.valueOf(fontes.size()), 2, RoundingMode.HALF_UP);
        }

        if (quantidadeParametros > 0) {
            indice = indice.add(BigDecimal.valueOf(Math.min(quantidadeParametros * 2L, 10L)));
        }

        if (indice.compareTo(new BigDecimal("100")) > 0) {
            return new BigDecimal("100");
        }
        if (indice.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return indice.setScale(2, RoundingMode.HALF_UP);
    }

    private ResultadoGeracao gerarResultadoSimulado(SolicitacaoExperiencia solicitacao,
                                                     Localizacao localizacao,
                                                     BigDecimal indiceConfiabilidade) {
        CategoriaExperiencia categoria = solicitacao.getCategoriaExperiencia();
        String local = localizacao.getNomeReferencia();

        ResultadoGeracao resultado = new ResultadoGeracao();
        resultado.setSolicitacao(solicitacao);
        resultado.setIndiceConfiabilidade(indiceConfiabilidade);
        resultado.setDataCriacao(LocalDateTime.now());
        resultado.setUrlImagemPreview(null);
        resultado.setUrlModelo3d(null);

        switch (categoria) {
            case CONSCIENTIZACAO_CLIMATICA -> {
                resultado.setDescricaoResultado(
                        "Simulação conceitual de impactos climáticos na região de " + local
                                + ", com foco em alterações ambientais e riscos associados.");
                resultado.setNarrativaImersiva(
                        "A experiência apresenta a projeção de elevação do nível do mar e mudanças no padrão climático "
                                + "sobre " + local + ", destacando áreas potencialmente afetadas e cenários de risco.");
                resultado.setObservacoesTecnicas(
                        "Resultado simulado com base em parâmetros informados e fontes conceituais. "
                                + "Não representa previsão científica real.");
            }
            case RECONSTRUCAO_HISTORICA -> {
                resultado.setDescricaoResultado(
                        "Reconstrução visual estimada do ambiente histórico de " + local
                                + ", removendo elementos contemporâneos da cena.");
                resultado.setNarrativaImersiva(
                        "A simulação recria o cenário passado de " + local
                                + ", sugerindo paisagem, ocupação e características visuais de épocas anteriores.");
                resultado.setObservacoesTecnicas(
                        "Estimativa visual baseada em referências históricas simuladas. Lacunas documentais podem existir.");
            }
            case CENARIO_FUTURO -> {
                resultado.setDescricaoResultado(
                        "Projeção futura de transformações possíveis em " + local
                                + ", considerando tendências urbanas e ambientais.");
                resultado.setNarrativaImersiva(
                        "A experiência projeta " + local + " em um cenário futuro, "
                                + "evidenciando mudanças na infraestrutura, uso do solo e dinâmica regional.");
                resultado.setObservacoesTecnicas(
                        "Projeção hipotética para fins educacionais. Incertezas naturais do modelamento foram simplificadas.");
            }
            case EVENTO_ESPACIAL -> {
                resultado.setDescricaoResultado(
                        "Visualização simulada de fenômeno espacial relacionado ao pedido sobre " + local + ".");
                resultado.setNarrativaImersiva(
                        "A simulação posiciona o observador para acompanhar o evento astronômico ou espacial solicitado, "
                                + "com ênfase em escala, movimento e contexto orbital.");
                resultado.setObservacoesTecnicas(
                        "Representação conceitual de fenômeno espacial. Dados orbitais reais não foram integrados.");
            }
            case TRANSFORMACAO_URBANA -> {
                resultado.setDescricaoResultado(
                        "Simulação de transformações urbanas em " + local
                                + ", incluindo alterações de infraestrutura e ocupação.");
                resultado.setNarrativaImersiva(
                        "A experiência mostra como " + local + " poderia evoluir com novas edificações, "
                                + "vias e padrões de uso do espaço urbano.");
                resultado.setObservacoesTecnicas(
                        "Modelagem urbana simplificada. Não substitui estudo técnico de planejamento.");
            }
            case EXPERIENCIA_LIVRE -> {
                resultado.setDescricaoResultado(
                        "Experiência personalizada baseada no pedido: " + solicitacao.getDescricaoUsuario());
                resultado.setNarrativaImersiva(
                        "A simulação traduz a descrição livre do usuário em uma narrativa imersiva sobre "
                                + local + ", priorizando os elementos solicitados.");
                resultado.setObservacoesTecnicas(
                        "Resultado adaptado à descrição informada pelo usuário, com interpretação conceitual da solicitação.");
            }
        }

        return resultado;
    }

    private List<MetricaResultado> criarMetricasSimuladas(ResultadoGeracao resultado,
                                                          CategoriaExperiencia categoria) {
        List<MetricaResultado> metricas = new ArrayList<>();

        switch (categoria) {
            case CONSCIENTIZACAO_CLIMATICA -> {
                metricas.add(criarMetrica(resultado, "nivel_risco", new BigDecimal("78"), "pontos",
                        "Estimativa simulada de risco ambiental"));
                metricas.add(criarMetrica(resultado, "area_afetada_estimada", new BigDecimal("42"), "percentual",
                        "Percentual estimado de área impactada"));
                metricas.add(criarMetrica(resultado, "impacto_visual_estimado", new BigDecimal("85"), "pontos",
                        "Intensidade visual percebida na simulação"));
            }
            case RECONSTRUCAO_HISTORICA -> {
                metricas.add(criarMetrica(resultado, "confiabilidade_historica", new BigDecimal("72"), "percentual",
                        "Grau estimado de aderência histórica"));
                metricas.add(criarMetrica(resultado, "nivel_reconstrucao", new BigDecimal("80"), "pontos",
                        "Completude da reconstrução visual"));
                metricas.add(criarMetrica(resultado, "densidade_estimativa_visual", new BigDecimal("65"), "pontos",
                        "Riqueza de detalhes na cena reconstruída"));
            }
            case CENARIO_FUTURO -> {
                metricas.add(criarMetrica(resultado, "indice_transformacao", new BigDecimal("74"), "pontos",
                        "Magnitude estimada de transformação futura"));
                metricas.add(criarMetrica(resultado, "incerteza_projecao", new BigDecimal("38"), "percentual",
                        "Incerteza simulada da projeção"));
            }
            case EVENTO_ESPACIAL -> {
                metricas.add(criarMetrica(resultado, "intensidade_visual", new BigDecimal("88"), "pontos",
                        "Impacto visual do fenômeno simulado"));
                metricas.add(criarMetrica(resultado, "complexidade_simulacao", new BigDecimal("70"), "pontos",
                        "Complexidade técnica da representação"));
            }
            case TRANSFORMACAO_URBANA -> {
                metricas.add(criarMetrica(resultado, "indice_urbanizacao", new BigDecimal("76"), "pontos",
                        "Grau estimado de urbanização projetada"));
                metricas.add(criarMetrica(resultado, "impacto_infraestrutura", new BigDecimal("68"), "pontos",
                        "Impacto simulado sobre infraestrutura existente"));
            }
            case EXPERIENCIA_LIVRE -> {
                metricas.add(criarMetrica(resultado, "aderencia_ao_pedido", new BigDecimal("82"), "pontos",
                        "Aderência da simulação à descrição do usuário"));
                metricas.add(criarMetrica(resultado, "impacto_visual_estimado", new BigDecimal("79"), "pontos",
                        "Impacto visual percebido na experiência"));
            }
        }

        return metricas;
    }

    private MetricaResultado criarMetrica(ResultadoGeracao resultado, String nome, BigDecimal valor,
                                          String unidade, String descricao) {
        MetricaResultado metrica = new MetricaResultado();
        metrica.setResultado(resultado);
        metrica.setNome(nome);
        metrica.setValor(valor);
        metrica.setUnidade(unidade);
        metrica.setDescricao(descricao);
        return metrica;
    }

    private SolicitacaoExperienciaResponse montarResponse(SolicitacaoExperiencia solicitacao) {
        SolicitacaoExperienciaResponse response = new SolicitacaoExperienciaResponse();
        response.setId(solicitacao.getId());
        response.setUsuarioId(solicitacao.getUsuario().getId());
        response.setNomeUsuario(solicitacao.getUsuario().getNome());
        response.setLocalizacao(toLocalizacaoResponse(solicitacao.getLocalizacao()));
        response.setTitulo(solicitacao.getTitulo());
        response.setDescricaoUsuario(solicitacao.getDescricaoUsuario());
        response.setCategoriaExperiencia(solicitacao.getCategoriaExperiencia());
        response.setTipoTemporal(solicitacao.getTipoTemporal());
        response.setAnoReferencia(solicitacao.getAnoReferencia());
        response.setNivelDetalhamento(solicitacao.getNivelDetalhamento());
        response.setStatus(solicitacao.getStatus());
        response.setPromptGerado(solicitacao.getPromptGerado());
        response.setDataSolicitacao(solicitacao.getDataSolicitacao());
        response.setDataProcessamento(solicitacao.getDataProcessamento());

        response.setParametros(parametroRepository.findBySolicitacaoId(solicitacao.getId()).stream()
                .map(this::toParametroResponse)
                .toList());

        response.setFontesDados(solicitacao.getFontesDados().stream()
                .map(this::toFonteResponse)
                .toList());

        resultadoRepository.findBySolicitacaoId(solicitacao.getId())
                .ifPresent(resultado -> response.setResultado(toResultadoResponse(resultado)));

        return response;
    }

    private LocalizacaoResponse toLocalizacaoResponse(Localizacao localizacao) {
        LocalizacaoResponse response = new LocalizacaoResponse();
        response.setId(localizacao.getId());
        response.setNomeReferencia(localizacao.getNomeReferencia());
        response.setLatitude(localizacao.getLatitude());
        response.setLongitude(localizacao.getLongitude());
        response.setPais(localizacao.getPais());
        response.setCidade(localizacao.getCidade());
        response.setDescricao(localizacao.getDescricao());
        return response;
    }

    private ParametroSolicitacaoResponse toParametroResponse(ParametroSolicitacao parametro) {
        ParametroSolicitacaoResponse response = new ParametroSolicitacaoResponse();
        response.setId(parametro.getId());
        response.setNome(parametro.getNome());
        response.setValor(parametro.getValor());
        response.setUnidade(parametro.getUnidade());
        response.setTipoParametro(parametro.getTipoParametro());
        return response;
    }

    private FonteDadosResponse toFonteResponse(FonteDados fonte) {
        FonteDadosResponse response = new FonteDadosResponse();
        response.setId(fonte.getId());
        response.setNome(fonte.getNome());
        response.setTipoFonte(fonte.getTipoFonte());
        response.setDescricao(fonte.getDescricao());
        response.setUrlReferencia(fonte.getUrlReferencia());
        response.setConfiabilidadeBase(fonte.getConfiabilidadeBase());
        return response;
    }

    private ResultadoGeracaoResponse toResultadoResponse(ResultadoGeracao resultado) {
        ResultadoGeracaoResponse response = new ResultadoGeracaoResponse();
        response.setId(resultado.getId());
        response.setSolicitacaoId(resultado.getSolicitacao().getId());
        response.setDescricaoResultado(resultado.getDescricaoResultado());
        response.setNarrativaImersiva(resultado.getNarrativaImersiva());
        response.setUrlImagemPreview(resultado.getUrlImagemPreview());
        response.setUrlModelo3d(resultado.getUrlModelo3d());
        response.setIndiceConfiabilidade(resultado.getIndiceConfiabilidade());
        response.setObservacoesTecnicas(resultado.getObservacoesTecnicas());
        response.setDataCriacao(resultado.getDataCriacao());
        response.setMetricas(metricaRepository.findByResultadoId(resultado.getId()).stream()
                .map(this::toMetricaResponse)
                .toList());
        return response;
    }

    private MetricaResultadoResponse toMetricaResponse(MetricaResultado metrica) {
        MetricaResultadoResponse response = new MetricaResultadoResponse();
        response.setId(metrica.getId());
        response.setNome(metrica.getNome());
        response.setValor(metrica.getValor());
        response.setUnidade(metrica.getUnidade());
        response.setDescricao(metrica.getDescricao());
        return response;
    }
}
