import { MODULES } from '../data/modules.js';
import { setActiveModule, setActiveScenario } from '../core/appState.js';
import { renderTimeline } from './timeline.js';
import { renderMapPanel } from './mapPanel.js';

const moduleInstances = new Map();
const VISUAL_MODULES = new Set(['clima', 'patrimonio', 'simulacoes']);

function buildScenarioCard(scenario, moduleId, isLab) {
    const card = document.createElement('button');
    card.type = 'button';
    card.className = 'glass-card scenario-card';
    card.dataset.scenarioId = scenario.id;

    if (isLab) {
        card.classList.add('lab-card');
        card.innerHTML = `
            <p class="lab-card__question">${scenario.title}</p>
            <p class="lab-card__hint">${scenario.hint || scenario.region}</p>
            <span class="scenario-card__meta">Explorar cenário →</span>
        `;
    } else if (VISUAL_MODULES.has(moduleId) && scenario.image) {
        card.classList.add('visual-card', 'scenario-card--visual');
        card.style.backgroundImage = `url("${scenario.image}")`;
        card.innerHTML = `
            <span class="visual-card__overlay" aria-hidden="true"></span>
            <span class="visual-card__glow" aria-hidden="true"></span>
            <span class="visual-card__content visual-card__content--scenario">
                <h3>${scenario.title}</h3>
                <p>${scenario.region}</p>
            </span>
        `;
    } else {
        card.innerHTML = `
            <h3>${scenario.title}</h3>
            <p>${scenario.region}</p>
            <span class="scenario-card__meta">Abrir linha do tempo</span>
        `;
    }

    card.addEventListener('click', () => selectScenario(moduleId, scenario.id));
    return card;
}

function renderMetrics(container, entry) {
    if (!container || !entry?.metrics) {
        container.innerHTML = '';
        return;
    }

    container.innerHTML = '';
    const grid = document.createElement('div');
    grid.className = 'grid-3';

    Object.entries(entry.metrics).forEach(([key, value]) => {
        const card = document.createElement('article');
        card.className = 'stat-card';
        card.innerHTML = `
            <p class="stat-card__label">${key}</p>
            <p class="stat-card__value" style="font-size: 1.25rem">${value}</p>
        `;
        grid.appendChild(card);
    });

    container.appendChild(grid);
}

function selectScenario(moduleId, scenarioId) {
    const mod = MODULES[moduleId];
    const scenario = mod?.scenarios?.find((s) => s.id === scenarioId);
    if (!scenario) {
        return;
    }

    setActiveScenario(scenario);
    const instance = moduleInstances.get(moduleId);
    if (!instance) {
        return;
    }

    instance.cards.querySelectorAll('.scenario-card').forEach((c) => {
        c.classList.toggle('is-selected', c.dataset.scenarioId === scenarioId);
    });

    if (instance.empty) {
        instance.empty.hidden = true;
    }
    if (instance.workspace) {
        instance.workspace.hidden = false;
    }

    renderMapPanel(instance.map, scenario);
    renderTimeline(instance.timeline, scenario.timeline, {
        onSelect: (_year, entry) => {
            renderMetrics(instance.metrics, entry);
            if (instance.vizTitle) {
                instance.vizTitle.textContent = entry?.title || scenario.title;
            }
            if (instance.vizDesc) {
                instance.vizDesc.textContent = entry?.description || '';
            }
        }
    });

    if (instance.vizTitle) {
        const firstYear = Object.keys(scenario.timeline)[0];
        const first = scenario.timeline[firstYear];
        instance.vizTitle.textContent = first?.title || scenario.title;
        instance.vizDesc.textContent = first?.description || '';
    }
}

export function initModuleView(moduleId) {
    const mod = MODULES[moduleId];
    if (!mod) {
        return;
    }

    setActiveModule(moduleId);

    const root = document.querySelector(`[data-module="${moduleId}"]`);
    if (!root) {
        return;
    }

    const cardsContainer = root.querySelector('[data-scenarios]');
    const empty = root.querySelector('[data-workspace-empty]');
    const workspace = root.querySelector('[data-workspace]');
    const map = root.querySelector('[data-map]');
    const timeline = root.querySelector('[data-timeline]');
    const metrics = root.querySelector('[data-scenario-metrics]');
    const vizTitle = root.querySelector('[data-viz-title]');
    const vizDesc = root.querySelector('[data-viz-desc]');

    if (!cardsContainer) {
        return;
    }

    cardsContainer.innerHTML = '';
    const isLab = moduleId === 'labs';

    mod.scenarios.forEach((scenario) => {
        cardsContainer.appendChild(buildScenarioCard(scenario, moduleId, isLab));
    });

    moduleInstances.set(moduleId, {
        cards: cardsContainer,
        empty,
        workspace,
        map,
        timeline,
        metrics,
        vizTitle,
        vizDesc
    });
}

export function selectScenarioById(moduleId, scenarioId) {
    selectScenario(moduleId, scenarioId);
}
