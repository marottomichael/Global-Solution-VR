let revealObserver;

function createRevealObserver() {
    return new IntersectionObserver(
        (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('is-visible');
                }
            });
        },
        { threshold: 0.12, rootMargin: '0px 0px -40px 0px' }
    );
}

export function observeRevealCards(root = document) {
    if (!revealObserver) {
        revealObserver = createRevealObserver();
    }

    const cards = root.querySelectorAll('.reveal-card:not([data-reveal-observed])');
    if (!cards.length) {
        return;
    }

    cards.forEach((card, index) => {
        card.dataset.revealObserved = 'true';
        card.style.transitionDelay = `${index * 0.08}s`;
        revealObserver.observe(card);
    });
}

export function setupScrollReveal() {
    observeRevealCards();
}
