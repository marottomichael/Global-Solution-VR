import { VIDEOS } from '../data/assets.js';
import { PRODUTO_FEATURES, PRODUTO_INTEGRACAO, PRODUTO_VIDEO } from '../data/produto.js';
import { navigateTo } from '../core/router.js';
import { scrollToSection } from '../core/uiHelpers.js';
import { initProdutoCarousel } from './produtoCarousel.js';

function bindNavButtons(root) {
    root.querySelectorAll('[data-nav-view]').forEach((el) => {
        el.addEventListener('click', (event) => {
            const viewId = el.getAttribute('data-nav-view');
            if (!viewId) {
                return;
            }
            event.preventDefault();
            navigateTo(viewId);
        });
    });

    root.querySelectorAll('[data-scroll]').forEach((el) => {
        el.addEventListener('click', (event) => {
            const target = el.getAttribute('data-scroll');
            if (!target) {
                return;
            }
            event.preventDefault();
            navigateTo('view-home', { scrollTop: false });
            requestAnimationFrame(() => scrollToSection(target));
        });
    });
}

export function renderProdutoShowcase(container) {
    if (!container) {
        return;
    }

    const featuresHtml = PRODUTO_FEATURES.map(
        (feature) => `
            <article class="glass-card produto-feature reveal-card">
                <h3>${feature.title}</h3>
                <p>${feature.description}</p>
            </article>
        `
    ).join('');

    container.innerHTML = `
        <div class="produto-layout">
            <div class="produto-viewer glass-card reveal-card">
                <div class="produto-viewer__header">
                    <span class="badge">${PRODUTO_VIDEO.badge}</span>
                    <span class="produto-viewer__note">${PRODUTO_VIDEO.note}</span>
                </div>
                <div class="produto-viewer__stage">
                    <video
                        class="produto-viewer__video"
                        src="${VIDEOS.memoraProduct}"
                        muted
                        loop
                        playsinline
                        controls
                        aria-label="Visualização 360 graus do óculos MEMORA"
                    ></video>
                </div>
                <p class="produto-viewer__caption">${PRODUTO_VIDEO.caption}</p>
            </div>

            <div class="produto-carousel-wrap reveal-card" data-produto-carousel></div>

            <div class="grid-3 produto-features">
                ${featuresHtml}
            </div>

            <div class="glass-card produto-integracao reveal-card">
                <header class="section-header">
                    <p class="section-eyebrow">Ecossistema MEMORA</p>
                    <h2>${PRODUTO_INTEGRACAO.title}</h2>
                    <p>${PRODUTO_INTEGRACAO.description}</p>
                </header>
                <div class="produto-integracao__actions">
                    <button type="button" class="btn btn-primary" data-nav-view="view-clima">${PRODUTO_INTEGRACAO.ctaPlatform}</button>
                    <button type="button" class="btn btn-secondary" data-scroll="#dashboard">${PRODUTO_INTEGRACAO.ctaStats}</button>
                </div>
            </div>
        </div>
    `;

    bindNavButtons(container);
    initProdutoCarousel(container.querySelector('[data-produto-carousel]'));

    const video = container.querySelector('.produto-viewer__video');
    if (video) {
        video.play().catch(() => {});
    }
}
