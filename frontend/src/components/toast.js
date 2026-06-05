let container = null;

function ensureContainer() {
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        container.setAttribute('aria-live', 'polite');
        document.body.appendChild(container);
    }
    return container;
}

export function showToast(message, type = 'error', duration = 4500) {
    const root = ensureContainer();
    const toast = document.createElement('div');
    toast.className = `toast toast--${type}`;
    toast.textContent = message;
    root.appendChild(toast);

    setTimeout(() => {
        toast.remove();
        if (root.childElementCount === 0) {
            root.remove();
            container = null;
        }
    }, duration);
}
