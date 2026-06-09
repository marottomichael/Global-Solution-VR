import './styles/variables.css';
import './styles/base.css';
import './styles/layout.css';
import './styles/components.css';
import './styles/pages.css';

import { setEstatisticas, setEstatisticasStatus } from './core/appState.js';
import { setupRouter, navigateTo } from './core/router.js';
import { scrollToSection } from './core/uiHelpers.js';
import { renderStats, renderStatsError, renderStatsLoading } from './components/statCards.js';
import { showToast } from './components/toast.js';
import { obterEstatisticas } from './services/estatisticaService.js';
import { setupNavbar } from './components/navbar.js';
import { renderHomeModules } from './components/homeModules.js';
import { renderEnvDashboard } from './components/dashboard.js';
import { setupScrollReveal, observeRevealCards } from './components/scrollReveal.js';
import { initModuleView } from './components/moduleView.js';
import { renderNewsSection } from './components/newsSection.js';
import { renderImpactCounters } from './components/impactCounters.js';
import { renderProdutoShowcase } from './components/produtoShowcase.js';
import { initPainelEspacial } from './components/painelEspacial.js';
import { VIDEOS, IMAGES } from './data/assets.js';

function setupScrollLinks() {
    document.querySelectorAll('[data-scroll]').forEach((element) => {
        element.addEventListener('click', (event) => {
            const target = element.getAttribute('data-scroll');
            if (!target) {
                return;
            }
            event.preventDefault();
            navigateTo('view-home', { scrollTop: false });
            requestAnimationFrame(() => scrollToSection(target));
        });
    });
}

function setupCasePills() {
    document.querySelectorAll('.case-pill').forEach((pill, index) => {
        pill.style.cursor = 'pointer';
        pill.addEventListener('click', () => {
            const views = ['view-clima', 'view-clima', 'view-clima', 'view-patrimonio', 'view-simulacoes', 'view-clima'];
            navigateTo(views[index] || 'view-clima');
        });
    });
}

async function loadEstatisticas() {
    const container = document.getElementById('stats-container');
    if (!container) {
        return;
    }

    setEstatisticasStatus('loading');
    renderStatsLoading(container);

    try {
        const data = await obterEstatisticas();
        setEstatisticas(data);
        renderStats(container, data);
    } catch {
        setEstatisticasStatus('error');
        renderStatsError(
            container,
            'API offline — métricas demonstrativas exibidas acima. Inicie o backend na porta 8081.'
        );
    }
}

function setupHeroVideo() {
    const video = document.querySelector('[data-hero-video]');
    const source = document.querySelector('[data-hero-source]');
    if (!video || !source) {
        return;
    }

    source.src = VIDEOS.earthHero;
    video.poster = IMAGES.planetaTerra;
    video.load();
}

function init() {
    setupHeroVideo();
    setupRouter();
    setupNavbar();
    setupScrollLinks();
    setupScrollReveal();
    setupCasePills();

    renderHomeModules(document.querySelector('[data-home-modules]'));
    renderImpactCounters(document.querySelector('[data-impact-counters]'));
    renderNewsSection(document.querySelector('[data-news-section]'));
    observeRevealCards(document.getElementById('view-home'));
    renderEnvDashboard(document.querySelector('[data-env-dashboard]'));

    ['clima', 'patrimonio', 'simulacoes', 'labs'].forEach(initModuleView);

    renderProdutoShowcase(document.querySelector('[data-produto-showcase]'));
    observeRevealCards(document.getElementById('view-produto'));

    initPainelEspacial(document.querySelector('[data-painel-espacial]'));

    loadEstatisticas();
}

init();
