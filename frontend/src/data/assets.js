/**
 * Assets locais em /public/images e /public/videos.
 * TODO: adicionar /public/images/ondas-de-calor.jpg para card de ondas de calor (usa Incêndios.jpg como fallback).
 * TODO: adicionar /public/images/brumadinho.jpg para cenário Brumadinho (usa Terremotos.jpg como fallback).
 */

export function assetUrl(folder, filename) {
    return `/${folder}/${encodeURIComponent(filename)}`;
}

export const VIDEOS = {
    earthHero: assetUrl('videos', 'planeta terra visto do espaço.mp4')
};

export const IMAGES = {
    planetaTerra: assetUrl('images', 'planeta terra.jpg'),
    amazoniaAntes: assetUrl('images', 'Amazônia antes.jpg'),
    amazoniaDesmatada: assetUrl('images', 'amazônia desmatada.jpg'),
    enchentes: assetUrl('images', 'Enchentes.jpg'),
    enchentesRs: assetUrl('images', 'Enchentes RS.jpg'),
    incendioFlorestal: assetUrl('images', 'incêndio florestal.jpg'),
    incendios: assetUrl('images', 'Incêndios.jpg'),
    degelo: assetUrl('images', 'Degelo.jpg'),
    furacao: assetUrl('images', 'furacao.jpg'),
    museuNacional: assetUrl('images', 'Museu Nacional.jpg'),
    museuChamas: assetUrl('images', 'MUSEU-NACIONAL em chamas.jpg'),
    palmyra: assetUrl('images', 'Palmyra.jpg'),
    machuPicchu: assetUrl('images', 'Machu Picchu.jpg'),
    terremotos: assetUrl('images', 'Terremotos.jpg'),
    pompeia: assetUrl('images', 'Pompeia.jpg')
};

export const NEWS_ITEMS = [
    {
        id: 'news-rs',
        category: 'Desastre',
        title: 'Enchentes no Rio Grande do Sul',
        subtitle: 'Maior evento hidrológico da história recente do Brasil afeta milhões e expõe vulnerabilidade climática.',
        image: IMAGES.enchentesRs,
        view: 'view-clima'
    },
    {
        id: 'news-amazonia',
        category: 'Clima',
        title: 'Queimadas na Amazônia',
        subtitle: 'Focos de calor em escala recorde comprometem o maior bioma tropical do planeta.',
        image: IMAGES.amazoniaDesmatada,
        view: 'view-clima'
    },
    {
        id: 'news-degelo',
        category: 'Ártico',
        title: 'Degelo das Calotas Polares',
        subtitle: 'Geleiras perdem massa em ritmo acelerado e elevam o nível dos oceanos globalmente.',
        image: IMAGES.degelo,
        view: 'view-clima'
    },
    {
        id: 'news-calor',
        category: 'Alerta',
        title: 'Ondas de Calor Extremas',
        subtitle: 'Temperaturas recordes em cidades costeiras e continentais redefinem zonas de risco humano.',
        image: IMAGES.incendios,
        view: 'view-clima'
    }
];
