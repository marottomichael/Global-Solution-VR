import { HOME_MODULES } from '../data/modules.js';
import { navigateTo } from '../core/router.js';

export function renderHomeModules(container) {
    if (!container) {
        return;
    }

    container.innerHTML = '';

    HOME_MODULES.forEach((mod) => {
        const card = document.createElement('button');
        card.type = 'button';
        card.className = 'visual-card module-card reveal-card';
        card.dataset.moduleNav = mod.view;
        card.style.backgroundImage = `url("${mod.image}")`;

        card.innerHTML = `
            <span class="visual-card__overlay" aria-hidden="true"></span>
            <span class="visual-card__glow" aria-hidden="true"></span>
            <span class="visual-card__content module-card__content">
                <span class="module-card__panel">
                    <span class="module-card__tag">${mod.tag}</span>
                    <h3>${mod.title}</h3>
                    <p>${mod.description}</p>
                    <span class="module-card__arrow" aria-hidden="true">→</span>
                </span>
            </span>
        `;

        card.addEventListener('click', () => navigateTo(mod.view));
        container.appendChild(card);
    });
}
