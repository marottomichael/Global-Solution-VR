import { formatDecimal, formatInteger } from '../core/uiHelpers.js';

const STAT_FIELDS = [
    { key: 'totalSolicitacoes', label: 'Solicitações' },
    { key: 'totalConcluidas', label: 'Concluídas' },
    { key: 'mediaAvaliacoes', label: 'Média de avaliações', decimal: true },
    { key: 'mediaConfiabilidade', label: 'Confiabilidade média', decimal: true }
];

function buildStatCard(label, value) {
    const card = document.createElement('article');
    card.className = 'stat-card';
    card.innerHTML = `
        <p class="stat-card__label">${label}</p>
        <p class="stat-card__value">${value}</p>
    `;
    return card;
}

export function renderStatsLoading(container) {
    container.innerHTML = '';
    const message = document.createElement('p');
    message.className = 'message message--loading';
    message.textContent = 'Carregando indicadores...';
    container.appendChild(message);
}

export function renderStatsError(container, detail) {
    container.innerHTML = '';
    const message = document.createElement('p');
    message.className = 'message message--error';
    message.textContent = detail || 'Não foi possível carregar os indicadores. Tente novamente em instantes.';
    container.appendChild(message);
}

export function renderStats(container, data) {
    container.innerHTML = '';
    const grid = document.createElement('div');
    grid.className = 'grid-stats';

    STAT_FIELDS.forEach(({ key, label, decimal }) => {
        const raw = data[key];
        const value = decimal ? formatDecimal(raw) : formatInteger(raw);
        grid.appendChild(buildStatCard(label, value));
    });

    container.appendChild(grid);
}
