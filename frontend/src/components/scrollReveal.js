export function setupScrollReveal() {
    const cards = document.querySelectorAll('.reveal-card');
    if (!cards.length) {
        return;
    }

    const observer = new IntersectionObserver(
        (entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('is-visible');
                }
            });
        },
        { threshold: 0.12, rootMargin: '0px 0px -40px 0px' }
    );

    cards.forEach((card, index) => {
        card.style.transitionDelay = `${index * 0.08}s`;
        observer.observe(card);
    });
}
