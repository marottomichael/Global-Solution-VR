import { ALEPO_PLAZA } from '../data/alepoPlaza.js';

function clamp(value, min, max) {
    return Math.min(max, Math.max(min, value));
}

function indexFromRatio(ratio, count) {
    if (count <= 1) {
        return 0;
    }
    return clamp(Math.round(ratio * (count - 1)), 0, count - 1);
}

function markerPercent(index, count) {
    if (count <= 1) {
        return 0;
    }
    return (index / (count - 1)) * 100;
}

export function initProdutoCarousel(container) {
    if (!container) {
        return;
    }

    const { location, scenarios } = ALEPO_PLAZA;
    const markersHtml = scenarios
        .map(
            (item, index) => `
                <button
                    type="button"
                    class="produto-carousel__marker"
                    data-index="${index}"
                    style="left: ${markerPercent(index, scenarios.length)}%"
                    aria-label="Cenário ${item.year}"
                >
                    <span class="produto-carousel__dot"></span>
                    <span class="produto-carousel__year">${item.year}</span>
                </button>
            `
        )
        .join('');

    container.innerHTML = `
        <header class="produto-carousel__header">
            <div>
                <p class="section-eyebrow">${location.subtitle}</p>
                <h2>${location.name}</h2>
                <p class="produto-carousel__region">${location.region}</p>
            </div>
            <div class="produto-carousel__counter" data-counter>01 / 0${scenarios.length}</div>
        </header>

        <div class="produto-carousel__viewport">
            <img
                class="produto-carousel__image"
                data-carousel-image
                src="${scenarios[0].image}"
                alt="Cenário ${scenarios[0].year} — ${location.name}"
                draggable="false"
            >
            <div class="produto-carousel__phase" data-carousel-phase>${scenarios[0].phase}</div>
            <div class="produto-carousel__scrubber produto-carousel__scrubber--overlay" aria-label="Linha do tempo — arraste para ver a transformação do local">
                <div class="produto-carousel__scrubber-inner">
                    <span class="produto-carousel__active-year" data-active-year>${scenarios[0].year}</span>
                    <div class="produto-carousel__track" data-carousel-track role="slider" aria-valuemin="0" aria-valuemax="${scenarios.length - 1}" aria-valuenow="0" tabindex="0">
                        <div class="produto-carousel__rail"></div>
                        <div class="produto-carousel__fill" data-carousel-fill style="width: 0%"></div>
                        <div class="produto-carousel__thumb" data-carousel-thumb></div>
                        <div class="produto-carousel__markers">${markersHtml}</div>
                    </div>
                </div>
            </div>
        </div>

        <div class="produto-carousel__detail glass-card" data-carousel-detail>
            <h3 data-detail-title>${scenarios[0].title}</h3>
            <p data-detail-text>${scenarios[0].description}</p>
        </div>
    `;

    const image = container.querySelector('[data-carousel-image]');
    const phase = container.querySelector('[data-carousel-phase]');
    const fill = container.querySelector('[data-carousel-fill]');
    const thumb = container.querySelector('[data-carousel-thumb]');
    const track = container.querySelector('[data-carousel-track]');
    const detailTitle = container.querySelector('[data-detail-title]');
    const detailText = container.querySelector('[data-detail-text]');
    const activeYear = container.querySelector('[data-active-year]');
    const counter = container.querySelector('[data-counter]');
    const markers = container.querySelectorAll('.produto-carousel__marker');

    let activeIndex = -1;
    let isDragging = false;

    function updateVisuals(index) {
        const scenario = scenarios[index];
        if (!scenario) {
            return;
        }

        if (index === activeIndex) {
            return;
        }

        activeIndex = index;
        const percent = markerPercent(index, scenarios.length);

        fill.style.width = `${percent}%`;
        thumb.style.left = `${percent}%`;

        markers.forEach((marker, markerIndex) => {
            marker.classList.toggle('is-active', markerIndex === index);
        });

        track.setAttribute('aria-valuenow', String(index));

        image.src = scenario.image;
        image.alt = `Cenário ${scenario.year} — ${location.name}`;
        phase.textContent = scenario.phase;
        detailTitle.textContent = scenario.title;
        detailText.textContent = scenario.description;
        activeYear.textContent = scenario.year;
        counter.textContent = `${String(index + 1).padStart(2, '0')} / 0${scenarios.length}`;
    }

    function setIndexFromClientX(clientX) {
        const rect = track.getBoundingClientRect();
        const ratio = clamp((clientX - rect.left) / rect.width, 0, 1);
        updateVisuals(indexFromRatio(ratio, scenarios.length));
    }

    track.addEventListener('pointerdown', (event) => {
        isDragging = true;
        track.setPointerCapture(event.pointerId);
        track.classList.add('is-dragging');
        setIndexFromClientX(event.clientX);
    });

    track.addEventListener('pointermove', (event) => {
        if (!isDragging) {
            return;
        }
        setIndexFromClientX(event.clientX);
    });

    function endDrag(event) {
        if (!isDragging) {
            return;
        }
        isDragging = false;
        track.classList.remove('is-dragging');
        if (event.pointerId !== undefined) {
            track.releasePointerCapture(event.pointerId);
        }
    }

    track.addEventListener('pointerup', endDrag);
    track.addEventListener('pointercancel', endDrag);

    track.addEventListener('keydown', (event) => {
        if (event.key === 'ArrowRight' || event.key === 'ArrowUp') {
            event.preventDefault();
            updateVisuals(clamp(activeIndex + 1, 0, scenarios.length - 1));
        }
        if (event.key === 'ArrowLeft' || event.key === 'ArrowDown') {
            event.preventDefault();
            updateVisuals(clamp(activeIndex - 1, 0, scenarios.length - 1));
        }
    });

    markers.forEach((marker) => {
        marker.addEventListener('click', () => {
            const index = Number(marker.dataset.index);
            updateVisuals(index);
        });
    });

    updateVisuals(0);
}
