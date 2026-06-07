const COUNTERS = [
    {
        id: 'rs-cities',
        value: 460,
        prefix: '+',
        label: 'cidades afetadas no RS'
    },
    {
        id: 'museu-loss',
        value: 90,
        prefix: '+',
        suffix: '%',
        label: 'do acervo do Museu Nacional perdido'
    },
    {
        id: 'amazon-hectares',
        text: 'Milhões de hectares transformados na Amazônia'
    },
    {
        id: 'heat-records',
        text: 'Recordes globais de temperatura'
    }
];

function animateValue(element, target, duration = 1800) {
    const start = performance.now();

    function tick(now) {
        const progress = Math.min((now - start) / duration, 1);
        const eased = 1 - (1 - progress) ** 3;
        const current = Math.round(target * eased);
        element.textContent = String(current);

        if (progress < 1) {
            requestAnimationFrame(tick);
        }
    }

    requestAnimationFrame(tick);
}

export function renderImpactCounters(container) {
    if (!container) {
        return;
    }

    container.innerHTML = COUNTERS.map((item) => {
        if (item.text) {
            return `
                <div class="impact-counter impact-counter--text reveal-card" data-counter-id="${item.id}">
                    <span class="impact-counter__value">${item.text}</span>
                </div>
            `;
        }

        return `
            <div class="impact-counter reveal-card" data-counter-id="${item.id}" data-counter-value="${item.value}">
                <span class="impact-counter__value">
                    <span class="impact-counter__prefix">${item.prefix || ''}</span>
                    <span data-counter-number>0</span>
                    <span class="impact-counter__suffix">${item.suffix || ''}</span>
                </span>
                <span class="impact-counter__label">${item.label}</span>
            </div>
        `;
    }).join('');

    const observer = new IntersectionObserver(
        (entries) => {
            entries.forEach((entry) => {
                if (!entry.isIntersecting || entry.target.dataset.counterAnimated === 'true') {
                    return;
                }

                entry.target.dataset.counterAnimated = 'true';
                entry.target.classList.add('is-visible');

                const value = Number(entry.target.dataset.counterValue);
                const numberEl = entry.target.querySelector('[data-counter-number]');
                if (numberEl && value) {
                    animateValue(numberEl, value);
                }
            });
        },
        { threshold: 0.35 }
    );

    container.querySelectorAll('.impact-counter').forEach((counter, index) => {
        counter.style.transitionDelay = `${index * 0.1}s`;
        observer.observe(counter);
    });
}
