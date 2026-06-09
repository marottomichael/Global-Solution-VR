const VIEWS = ['view-home', 'view-clima', 'view-patrimonio', 'view-simulacoes', 'view-labs', 'view-painel-espacial', 'view-produto'];

let currentView = 'view-home';
const listeners = new Set();

export function getCurrentView() {
    return currentView;
}

export function onViewChange(callback) {
    listeners.add(callback);
    return () => listeners.delete(callback);
}

function notify() {
    listeners.forEach((fn) => fn(currentView));
}

export function navigateTo(viewId, { scrollTop = true } = {}) {
    if (!VIEWS.includes(viewId)) {
        return;
    }

    currentView = viewId;

    document.querySelectorAll('.view').forEach((el) => {
        el.classList.toggle('is-active', el.id === viewId);
    });

    document.querySelectorAll('[data-nav-view]').forEach((el) => {
        const target = el.getAttribute('data-nav-view');
        el.classList.toggle('is-active', target === viewId);
    });

    if (scrollTop) {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    notify();
}

export function setupRouter() {
    document.querySelectorAll('[data-nav-view]').forEach((el) => {
        el.addEventListener('click', (event) => {
            const viewId = el.getAttribute('data-nav-view');
            if (!viewId) {
                return;
            }
            event.preventDefault();
            navigateTo(viewId);
        });
    });
}
