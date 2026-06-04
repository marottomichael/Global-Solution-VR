const state = {
    estatisticas: null,
    estatisticasStatus: 'idle',
    activeModule: null,
    activeScenario: null,
    activeYear: null
};

export function getState() {
    return state;
}

export function setEstatisticasStatus(status) {
    state.estatisticasStatus = status;
}

export function setEstatisticas(data) {
    state.estatisticas = data;
    state.estatisticasStatus = 'loaded';
}

export function clearEstatisticas() {
    state.estatisticas = null;
    state.estatisticasStatus = 'idle';
}

export function setActiveModule(moduleId) {
    state.activeModule = moduleId;
}

export function setActiveScenario(scenario) {
    state.activeScenario = scenario;
}

export function setActiveYear(year) {
    state.activeYear = year;
}
