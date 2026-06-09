import Chart from 'chart.js/auto';
import { obterDashboardAmazonia } from '../services/dashboardService.js';
import { apiUrl } from '../core/apiClient.js';
import { onViewChange } from '../core/router.js';
import { observeRevealCards } from './scrollReveal.js';

const PROJECAO_2050 = {
    cobertura: 55,
    temperatura: 28.3,
    risco: 'Alto'
};

let charts = [];
let loaded = false;

function destroyCharts() {
    charts.forEach((chart) => chart.destroy());
    charts = [];
}

function filterMetricas(metricas, nome) {
    return metricas
        .filter((m) => m.nome === nome)
        .sort((a, b) => a.ano - b.ano);
}

function formatValor(valor, unidade) {
    if (unidade === '%') {
        return `${valor}%`;
    }
    if (unidade === '°C') {
        return `${valor}°C`;
    }
    return `${valor}${unidade || ''}`;
}

function buildChartOptions(title) {
    return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: { display: false },
            title: {
                display: true,
                text: title,
                color: '#F5F9FF',
                font: { family: "'Space Grotesk', sans-serif", size: 14, weight: '600' },
                padding: { bottom: 16 }
            }
        },
        scales: {
            x: {
                ticks: { color: 'rgba(245, 249, 255, 0.62)' },
                grid: { color: 'rgba(255, 255, 255, 0.06)' }
            },
            y: {
                ticks: { color: 'rgba(245, 249, 255, 0.62)' },
                grid: { color: 'rgba(255, 255, 255, 0.06)' }
            }
        }
    };
}

function createLineChart(canvas, labels, values, color, title, ySuffix = '') {
    return new Chart(canvas, {
        type: 'line',
        data: {
            labels,
            datasets: [{
                label: title,
                data: values,
                borderColor: color,
                backgroundColor: `${color}33`,
                fill: true,
                tension: 0.35,
                pointRadius: 5,
                pointHoverRadius: 7,
                pointBackgroundColor: color,
                pointBorderColor: '#071426',
                pointBorderWidth: 2
            }]
        },
        options: {
            ...buildChartOptions(title),
            scales: {
                ...buildChartOptions(title).scales,
                y: {
                    ...buildChartOptions(title).scales.y,
                    ticks: {
                        color: 'rgba(245, 249, 255, 0.62)',
                        callback: (value) => `${value}${ySuffix}`
                    }
                }
            }
        }
    });
}

function renderPainel(container, data) {
    const cobertura = filterMetricas(data.metricas, 'cobertura_florestal');
    const temperatura = filterMetricas(data.metricas, 'temperatura_media');
    const latestCobertura = cobertura[cobertura.length - 1];
    const latestTemp = temperatura[temperatura.length - 1];
    const perdaAcumulada = latestCobertura ? Math.round(100 - latestCobertura.valor) : 35;

    container.innerHTML = `
        <header class="painel-header reveal-card">
            <p class="section-eyebrow">Space Connect · ${data.titulo}</p>
            <h2>Painel de Indicadores Espaciais</h2>
            <p class="painel-header__subtitle">Dados históricos observados por satélites e projeções futuras para a Amazônia.</p>
            ${data.descricao ? `<p class="painel-header__desc">${data.descricao}</p>` : ''}
        </header>

        <div class="grid-stats painel-stats reveal-card">
            <article class="stat-card glass-card">
                <p class="stat-card__label">Cobertura Florestal</p>
                <p class="stat-card__value">${formatValor(latestCobertura?.valor ?? 65, '%')}</p>
                <p class="stat-card__hint">Referência ${latestCobertura?.ano ?? 2026}</p>
            </article>
            <article class="stat-card glass-card">
                <p class="stat-card__label">Temperatura Média</p>
                <p class="stat-card__value">${formatValor(latestTemp?.valor ?? 26.8, '°C')}</p>
                <p class="stat-card__hint">Referência ${latestTemp?.ano ?? 2026}</p>
            </article>
            <article class="stat-card glass-card">
                <p class="stat-card__label">Perda Acumulada</p>
                <p class="stat-card__value">${perdaAcumulada}%</p>
                <p class="stat-card__hint">Desde 1985 (baseline 100%)</p>
            </article>
            <article class="stat-card glass-card">
                <p class="stat-card__label">Projeção para 2050</p>
                <p class="stat-card__value">${PROJECAO_2050.cobertura}%</p>
                <p class="stat-card__hint">Cobertura florestal estimada</p>
            </article>
        </div>

        <div class="painel-charts">
            <div class="glass-card painel-chart-card reveal-card">
                <canvas data-chart-cobertura aria-label="Evolução da cobertura florestal"></canvas>
            </div>
            <div class="glass-card painel-chart-card reveal-card">
                <canvas data-chart-temperatura aria-label="Aumento da temperatura média"></canvas>
            </div>
        </div>

        <section class="painel-projecao glass-card reveal-card" aria-labelledby="painel-projecao-title">
            <p class="section-eyebrow">Projeção futura</p>
            <h3 id="painel-projecao-title">Cenário Futuro: Amazônia 2050</h3>
            <p class="painel-projecao__text">Com base nas tendências observadas por satélites, o MEMORA permite visualizar possíveis cenários futuros caso os padrões atuais continuem.</p>
            <div class="painel-projecao__grid">
                <div class="painel-projecao__item">
                    <span class="painel-projecao__label">Cobertura Florestal Projetada</span>
                    <strong>${PROJECAO_2050.cobertura}%</strong>
                </div>
                <div class="painel-projecao__item">
                    <span class="painel-projecao__label">Temperatura Média Projetada</span>
                    <strong>${PROJECAO_2050.temperatura}°C</strong>
                </div>
                <div class="painel-projecao__item">
                    <span class="painel-projecao__label">Risco Climático</span>
                    <strong class="painel-projecao__risk">${PROJECAO_2050.risco}</strong>
                </div>
            </div>
        </section>

        <section class="painel-info glass-card reveal-card" aria-labelledby="painel-info-title">
            <h3 id="painel-info-title">Como o Global Solution utiliza tecnologias espaciais?</h3>
            <p>Nossa plataforma transforma dados observados por satélites em experiências visuais e educativas. Utilizando informações ambientais coletadas ao longo do tempo, o sistema permite compreender transformações territoriais e impactos climáticos, aproximando a população das tecnologias espaciais e de sua importância para a sociedade.</p>
        </section>
    `;

    destroyCharts();

    const coberturaCanvas = container.querySelector('[data-chart-cobertura]');
    const tempCanvas = container.querySelector('[data-chart-temperatura]');

    if (coberturaCanvas && cobertura.length) {
        charts.push(createLineChart(
            coberturaCanvas,
            cobertura.map((m) => m.ano),
            cobertura.map((m) => m.valor),
            '#00D4FF',
            'Evolução da Cobertura Florestal',
            '%'
        ));
    }

    if (tempCanvas && temperatura.length) {
        charts.push(createLineChart(
            tempCanvas,
            temperatura.map((m) => m.ano),
            temperatura.map((m) => m.valor),
            '#7B61FF',
            'Aumento da Temperatura Média',
            '°C'
        ));
    }

    observeRevealCards(container);
}

async function loadPainelEspacial(container) {
    if (!container) {
        return;
    }

    container.innerHTML = '<p class="message message--loading">Carregando dados de satélite…</p>';

    try {
        const data = await obterDashboardAmazonia();
        renderPainel(container, data);
        loaded = true;
    } catch (error) {
        container.innerHTML = `
            <div class="message message--error painel-error">
                <p><strong>Não foi possível carregar o painel espacial.</strong></p>
                <p>${formatPainelError(error)}</p>
                <p class="painel-error__hint">
                    Endpoint: <code>GET ${apiUrl('/dashboard/amazonia')}</code>
                </p>
                <button type="button" class="btn btn-secondary painel-error__retry" data-painel-retry>
                    Tentar novamente
                </button>
            </div>
        `;
        container.querySelector('[data-painel-retry]')?.addEventListener('click', () => {
            loaded = false;
            loadPainelEspacial(container);
        });
    }
}

function formatPainelError(error) {
    if (error?.code === 'NETWORK_ERROR') {
        return 'Backend inacessível. Inicie o Spring Boot na porta 8081 (memora-backend) e confirme que o MySQL está ativo.';
    }
    if (error?.status === 404) {
        const msg = error.body?.mensagem || error.body?.message;
        return msg || 'Dashboard não encontrado no banco. Reinicie o backend para executar o seed automático.';
    }
    if (error?.status) {
        const msg = error.body?.mensagem || error.body?.message;
        return msg || `O servidor respondeu com status ${error.status}.`;
    }
    return 'Erro inesperado ao buscar os dados do dashboard.';
}

export function initPainelEspacial(container) {
    if (!container) {
        return;
    }

    onViewChange((viewId) => {
        if (viewId === 'view-painel-espacial' && !loaded) {
            loadPainelEspacial(container);
        }
    });
}
