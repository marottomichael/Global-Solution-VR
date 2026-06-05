import { ENV_METRICS_DEMO, CHART_BARS } from '../data/modules.js';

export function renderEnvDashboard(container) {
    if (!container) {
        return;
    }

    const metricsHtml = ENV_METRICS_DEMO.map(
        (m) => `
        <article class="stat-card">
            <p class="stat-card__label">${m.label}</p>
            <p class="stat-card__value">${m.value}</p>
            <p class="stat-card__delta stat-card__delta--${m.trend}">${m.delta}</p>
        </article>
    `
    ).join('');

    const barsHtml = CHART_BARS.map(
        (h) => `<div class="metric-chart__bar" style="height: ${h}%" title="${h}%"></div>`
    ).join('');

    container.innerHTML = `
        <div class="grid-stats">${metricsHtml}</div>
        <div class="glass-card" style="margin-top: 1.5rem">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; flex-wrap: wrap; gap: 0.5rem">
                <h3 style="color: var(--color-text)">Índice de transformação ambiental</h3>
                <span class="badge badge--live">Dados simulados</span>
            </div>
            <div class="metric-chart" role="img" aria-label="Gráfico de tendência ambiental">${barsHtml}</div>
        </div>
    `;
}
