import { setActiveYear } from '../core/appState.js';

const DEFAULT_YEARS = ['1980', '2000', '2026', '2050'];

export function renderTimeline(container, timelineData, { onSelect } = {}) {
    if (!container || !timelineData) {
        return;
    }

    const years = Object.keys(timelineData).sort((a, b) => {
        const na = parseInt(a, 10);
        const nb = parseInt(b, 10);
        if (!Number.isNaN(na) && !Number.isNaN(nb)) {
            return na - nb;
        }
        return String(a).localeCompare(String(b));
    });

    const initialYear = years[0] || DEFAULT_YEARS[0];
    let activeYear = initialYear;

    container.innerHTML = `
        <div class="timeline glass-card">
            <div class="timeline__track" role="group" aria-label="Linha do tempo">
                <div class="timeline__progress" data-progress style="width: 0%"></div>
            </div>
            <div class="timeline__detail" data-detail></div>
        </div>
    `;

    const track = container.querySelector('.timeline__track');
    const progress = container.querySelector('[data-progress]');
    const detail = container.querySelector('[data-detail]');

    years.forEach((year, index) => {
        const point = document.createElement('button');
        point.type = 'button';
        point.className = 'timeline__point';
        point.dataset.year = year;
        point.setAttribute('aria-label', `Ano ${timelineData[year]?.label || year}`);
        point.innerHTML = `
            <span class="timeline__dot"></span>
            <span>${timelineData[year]?.label || year}</span>
        `;
        point.addEventListener('click', () => selectYear(year, index));
        track.appendChild(point);
    });

    function updateProgress(index) {
        const percent = years.length > 1 ? (index / (years.length - 1)) * 100 : 0;
        progress.style.width = `${percent}%`;
    }

    function renderDetail(year) {
        const entry = timelineData[year];
        if (!entry) {
            detail.innerHTML = '<p>Sem dados para este período.</p>';
            return;
        }

        const metricsHtml = entry.metrics
            ? Object.entries(entry.metrics)
                  .map(([key, val]) => `<span><strong>${key}:</strong> ${val}</span>`)
                  .join(' · ')
            : '';

        detail.innerHTML = `
            <h4>${entry.title}</h4>
            <p>${entry.description}</p>
            ${metricsHtml ? `<p class="scenario-card__meta" style="margin-top: 0.75rem">${metricsHtml}</p>` : ''}
        `;
    }

    function selectYear(year, index) {
        activeYear = year;
        setActiveYear(year);
        track.querySelectorAll('.timeline__point').forEach((p) => {
            p.classList.toggle('is-active', p.dataset.year === year);
        });
        updateProgress(index);
        renderDetail(year);
        if (onSelect) {
            onSelect(year, timelineData[year]);
        }
    }

    const startIndex = 0;
    selectYear(initialYear, startIndex);
}
