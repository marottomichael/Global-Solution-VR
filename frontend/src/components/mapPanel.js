export function renderMapPanel(container, scenario) {
    if (!container) {
        return;
    }

    const coords = scenario?.coords || { lat: '0.0000', lng: '0.0000', x: 50, y: 50 };
    const region = scenario?.region || 'Região não definida';

    container.innerHTML = `
        <div class="map-panel" role="img" aria-label="Mapa interativo de ${region}">
            <div class="map-panel__grid"></div>
            <div class="map-panel__scan"></div>
            <div class="map-hotspot" style="left: ${coords.x}%; top: ${coords.y}%" title="${scenario?.title || ''}"></div>
            <span class="map-panel__label">SAT-VIEW · ${region}</span>
            <div class="map-panel__coords">
                LAT ${coords.lat}<br>
                LNG ${coords.lng}<br>
                ZOOM 1:250.000
            </div>
        </div>
    `;
}
