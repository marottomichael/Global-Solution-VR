const VLIBRAS_PLUGIN_URL = 'https://vlibras.gov.br/app/vlibras-plugin.js';
const VLIBRAS_APP_URL = 'https://vlibras.gov.br/app';

function injectWidgetMarkup() {
    if (document.querySelector('[vw].enabled')) {
        return;
    }

    const container = document.createElement('div');
    container.innerHTML = `
        <div vw class="enabled">
            <div vw-access-button class="active"></div>
            <div vw-plugin-wrapper>
                <div class="vw-plugin-top-wrapper"></div>
            </div>
        </div>
    `;

    document.body.appendChild(container.firstElementChild);
}

function loadPluginScript() {
    return new Promise((resolve, reject) => {
        if (window.VLibras?.Widget) {
            resolve();
            return;
        }

        const existing = document.querySelector(`script[src="${VLIBRAS_PLUGIN_URL}"]`);
        if (existing) {
            existing.addEventListener('load', () => resolve(), { once: true });
            existing.addEventListener('error', () => reject(new Error('Falha ao carregar VLibras')), { once: true });
            return;
        }

        const script = document.createElement('script');
        script.src = VLIBRAS_PLUGIN_URL;
        script.async = true;
        script.onload = () => resolve();
        script.onerror = () => reject(new Error('Falha ao carregar VLibras'));
        document.body.appendChild(script);
    });
}

export function initVLibras() {
    injectWidgetMarkup();

    loadPluginScript()
        .then(() => {
            if (!window.VLibras?.Widget) {
                return;
            }

            new window.VLibras.Widget(VLIBRAS_APP_URL);
        })
        .catch(() => {});
}
