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

        const mediaHtml = mod.video
            ? `<video class="module-card__video" src="${mod.video}" muted loop playsinline autoplay aria-hidden="true"></video>`
            : '';

        if (!mod.video && mod.image) {
            card.style.backgroundImage = `url("${mod.image}")`;
        }

        if (mod.video) {
            card.classList.add('module-card--video');
        }

        card.innerHTML = `
            ${mediaHtml}
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

        if (mod.video) {
            const video = card.querySelector('.module-card__video');
            video?.play().catch(() => {});
        }

        card.addEventListener('click', () => navigateTo(mod.view));
        container.appendChild(card);
    });
}
