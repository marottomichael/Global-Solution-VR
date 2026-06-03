import { setEstatisticas, setEstatisticasStatus } from './core/appState.js';
import { scrollToSection } from './core/uiHelpers.js';
import { renderStats, renderStatsError, renderStatsLoading } from './components/statCards.js';
import { showToast } from './components/toast.js';
import { obterEstatisticas } from './services/estatisticaService.js';

function setupNavigation() {
    document.querySelectorAll('[data-scroll]').forEach((element) => {
        element.addEventListener('click', (event) => {
            const target = element.getAttribute('data-scroll');
            if (!target) {
                return;
            }
            event.preventDefault();
            scrollToSection(target);
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
            'Não foi possível carregar os indicadores. Verifique se a API está em execução.'
        );
        showToast('Falha ao carregar estatísticas.', 'error');
    }
}

function init() {
    setupNavigation();
    loadEstatisticas();
}

init();
