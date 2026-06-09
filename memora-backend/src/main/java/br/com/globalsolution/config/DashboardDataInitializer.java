package br.com.globalsolution.config;

import br.com.globalsolution.model.FonteDados;
import br.com.globalsolution.model.Localizacao;
import br.com.globalsolution.model.MetricaResultado;
import br.com.globalsolution.model.ResultadoGeracao;
import br.com.globalsolution.model.SolicitacaoExperiencia;
import br.com.globalsolution.model.Usuario;
import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.NivelDetalhamento;
import br.com.globalsolution.model.enums.StatusGeracao;
import br.com.globalsolution.model.enums.TipoFonteDados;
import br.com.globalsolution.model.enums.TipoTemporal;
import br.com.globalsolution.repository.FonteDadosRepository;
import br.com.globalsolution.repository.LocalizacaoRepository;
import br.com.globalsolution.repository.MetricaResultadoRepository;
import br.com.globalsolution.repository.ResultadoGeracaoRepository;
import br.com.globalsolution.repository.SolicitacaoExperienciaRepository;
import br.com.globalsolution.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DashboardDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DashboardDataInitializer.class);
    private static final String DASHBOARD_TITULO = "Dashboard Espacial da Amazônia";

    private final SolicitacaoExperienciaRepository solicitacaoRepository;
    private final ResultadoGeracaoRepository resultadoRepository;
    private final MetricaResultadoRepository metricaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalizacaoRepository localizacaoRepository;
    private final FonteDadosRepository fonteDadosRepository;

    public DashboardDataInitializer(
            SolicitacaoExperienciaRepository solicitacaoRepository,
            ResultadoGeracaoRepository resultadoRepository,
            MetricaResultadoRepository metricaRepository,
            UsuarioRepository usuarioRepository,
            LocalizacaoRepository localizacaoRepository,
            FonteDadosRepository fonteDadosRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.resultadoRepository = resultadoRepository;
        this.metricaRepository = metricaRepository;
        this.usuarioRepository = usuarioRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.fonteDadosRepository = fonteDadosRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!solicitacaoRepository.findByCategoriaExperiencia(CategoriaExperiencia.MONITORAMENTO_AMBIENTAL).isEmpty()) {
            log.info("Dashboard Espacial da Amazônia já carregado — seed automático ignorado.");
            return;
        }

        log.info("Carregando dados do Dashboard Espacial da Amazônia...");

        Usuario usuario = usuarioRepository.findByEmail("admin@globalsolution.com")
                .orElseGet(this::criarUsuarioAdmin);

        Localizacao localizacao = criarLocalizacaoAmazonia();
        FonteDados fonte = obterOuCriarFonteSatelite();

        SolicitacaoExperiencia solicitacao = new SolicitacaoExperiencia();
        solicitacao.setUsuario(usuario);
        solicitacao.setLocalizacao(localizacao);
        solicitacao.setTitulo(DASHBOARD_TITULO);
        solicitacao.setDescricaoUsuario(
                "Painel de monitoramento contínuo da Amazônia usando dados satelitais.");
        solicitacao.setCategoriaExperiencia(CategoriaExperiencia.MONITORAMENTO_AMBIENTAL);
        solicitacao.setTipoTemporal(TipoTemporal.PASSADO);
        solicitacao.setAnoReferencia(2026);
        solicitacao.setNivelDetalhamento(NivelDetalhamento.ALTO);
        solicitacao.setStatus(StatusGeracao.CONCLUIDA);
        solicitacao.setPromptGerado(
                "Gerar dashboard de monitoramento ambiental da Amazônia com dados satelitais.");
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        solicitacao.setDataProcessamento(LocalDateTime.now());

        Set<FonteDados> fontes = new HashSet<>();
        fontes.add(fonte);
        solicitacao.setFontesDados(fontes);

        solicitacao = solicitacaoRepository.save(solicitacao);

        ResultadoGeracao resultado = new ResultadoGeracao();
        resultado.setSolicitacao(solicitacao);
        resultado.setDescricaoResultado(
                "Dashboard de indicadores ambientais da Amazônia com séries temporais.");
        resultado.setNarrativaImersiva("Monitoramento histórico via dados satelitais simulados.");
        resultado.setIndiceConfiabilidade(new BigDecimal("91.00"));
        resultado.setObservacoesTecnicas(
                "Dados coerentes com relatórios INPE/NASA para demonstração.");
        resultado.setDataCriacao(LocalDateTime.now());
        resultado = resultadoRepository.save(resultado);

        salvarMetricas(resultado);

        log.info("Dashboard Espacial da Amazônia carregado com sucesso (solicitação id={}).",
                solicitacao.getId());
    }

    private Usuario criarUsuarioAdmin() {
        Usuario usuario = new Usuario();
        usuario.setNome("Admin");
        usuario.setEmail("admin@globalsolution.com");
        usuario.setSenha("admin123");
        usuario.setTipoUsuario("ADMIN");
        usuario.setDataCriacao(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    private Localizacao criarLocalizacaoAmazonia() {
        Localizacao localizacao = new Localizacao();
        localizacao.setNomeReferencia("Amazônia Legal");
        localizacao.setLatitude(new BigDecimal("-3.4653000"));
        localizacao.setLongitude(new BigDecimal("-62.2159000"));
        localizacao.setPais("Brasil");
        localizacao.setCidade("Manaus");
        localizacao.setDescricao("Região de monitoramento por satélite");
        return localizacaoRepository.save(localizacao);
    }

    private FonteDados obterOuCriarFonteSatelite() {
        List<FonteDados> satelites = fonteDadosRepository.findByTipoFonte(TipoFonteDados.SATELITE);
        if (!satelites.isEmpty()) {
            return satelites.get(0);
        }

        List<FonteDados> climaticas = fonteDadosRepository.findByTipoFonte(TipoFonteDados.BASE_CLIMATICA);
        if (!climaticas.isEmpty()) {
            return climaticas.get(0);
        }

        FonteDados fonte = new FonteDados();
        fonte.setNome("Imagens de satélite INPE/NASA");
        fonte.setTipoFonte(TipoFonteDados.SATELITE);
        fonte.setDescricao("Dados simulados de observação da Terra para o dashboard espacial");
        fonte.setUrlReferencia("https://exemplo.org/satelite");
        fonte.setConfiabilidadeBase(new BigDecimal("90.00"));
        return fonteDadosRepository.save(fonte);
    }

    private void salvarMetricas(ResultadoGeracao resultado) {
        Object[][] dados = {
                {"cobertura_florestal", 100.0, "%", "Cobertura florestal histórica", 1985},
                {"cobertura_florestal", 85.0, "%", "Cobertura florestal histórica", 2000},
                {"cobertura_florestal", 78.0, "%", "Cobertura florestal histórica", 2010},
                {"cobertura_florestal", 70.0, "%", "Cobertura florestal histórica", 2020},
                {"cobertura_florestal", 65.0, "%", "Cobertura florestal atual", 2026},
                {"temperatura_media", 24.0, "°C", "Temperatura média histórica", 1985},
                {"temperatura_media", 24.8, "°C", "Temperatura média histórica", 2000},
                {"temperatura_media", 25.5, "°C", "Temperatura média histórica", 2010},
                {"temperatura_media", 26.1, "°C", "Temperatura média histórica", 2020},
                {"temperatura_media", 26.8, "°C", "Temperatura média atual", 2026}
        };

        for (Object[] linha : dados) {
            MetricaResultado metrica = new MetricaResultado();
            metrica.setResultado(resultado);
            metrica.setNome((String) linha[0]);
            metrica.setValor(BigDecimal.valueOf((Double) linha[1]));
            metrica.setUnidade((String) linha[2]);
            metrica.setDescricao((String) linha[3]);
            metrica.setAnoReferencia((Integer) linha[4]);
            metricaRepository.save(metrica);
        }
    }
}
