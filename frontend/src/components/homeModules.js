import { HOME_MODULES } from '../data/modules.js';
import { navigateTo } from '../core/router.js';

export function renderHomeModules(container) {
    if (!container) {
        return;
    }

    container.innerHTML = '';

    HOME_MODULES.forEach((mod, index) => {
        const card = document.createElement('button');
        card.type = 'button';
        card.className = 'glass-card module-card reveal-card';
        card.dataset.moduleNav = mod.view;

        const iconClass = index % 2 === 1 ? 'module-card__icon module-card__icon--purple' : 'module-card__icon';

        card.innerHTML = `
            <span class="module-card__tag">${mod.tag}</span>
            <div class="${iconClass}">${mod.icon}</div>
            <h3>${mod.title}</h3>
            <p>${mod.description}</p>
            <span class="module-card__arrow" aria-hidden="true">→</span>
        `;

        card.addEventListener('click', () => navigateTo(mod.view));
        container.appendChild(card);
    });
}
