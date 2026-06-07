import { NEWS_ITEMS } from '../data/assets.js';
import { navigateTo } from '../core/router.js';

export function renderNewsSection(container) {
    if (!container) {
        return;
    }

    container.innerHTML = '';

    NEWS_ITEMS.forEach((item) => {
        const card = document.createElement('article');
        card.className = 'news-card reveal-card';
        card.tabIndex = 0;
        card.style.backgroundImage = `url("${item.image}")`;

        card.innerHTML = `
            <span class="visual-card__overlay visual-card__overlay--news" aria-hidden="true"></span>
            <div class="news-card__body">
                <span class="news-card__category">${item.category}</span>
                <h3 class="news-card__title">${item.title}</h3>
                <p class="news-card__subtitle">${item.subtitle}</p>
            </div>
        `;

        const open = () => navigateTo(item.view);
        card.addEventListener('click', open);
        card.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                open();
            }
        });

        container.appendChild(card);
    });
}
