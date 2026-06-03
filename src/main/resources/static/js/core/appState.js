const state = {
    estatisticas: null,
    estatisticasStatus: 'idle'
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
