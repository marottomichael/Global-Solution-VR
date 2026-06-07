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
    logoIcon: assetUrl('images', 'satelite icone.png'),
    planetaTerra: assetUrl('images', 'planeta terra.jpg'),
    amazoniaAntes: assetUrl('images', 'Amazônia antes.jpg'),
    amazoniaDesmatada: assetUrl('images', 'amazônia desmatada.jpg'),
    enchentes: assetUrl('images', 'Enchentes.jpg'),
    enchentesRs: assetUrl('images', 'Enchentes RS.jpg'),
    incendioFlorestal: assetUrl('images', 'incêndio florestal.jpg'),
    incendios: assetUrl('images', 'Incêndios.jpg'),
    degelo: assetUrl('images', 'Degelo.jpg'),
    calorGlobal: assetUrl('images', 'aquecimento global desgelo.jpg'),
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
        category: 'Mudanças Climáticas',
        title: 'Enchentes no Rio Grande do Sul impactam centenas de cidades',
        subtitle: 'Eventos extremos afetaram mais de 460 municípios e deslocaram centenas de milhares de pessoas, evidenciando os desafios da adaptação climática.',
        image: IMAGES.enchentesRs,
        view: 'view-simulacoes'
    },
    {
        id: 'news-amazonia',
        category: 'Desmatamento',
        title: 'Amazônia perde milhões de hectares ao longo das últimas décadas',
        subtitle: 'Imagens de satélite revelam a transformação contínua da floresta e os impactos ambientais associados ao avanço do desmatamento.',
        image: IMAGES.amazoniaDesmatada,
        view: 'view-clima'
    },
    {
        id: 'news-degelo',
        category: 'Aquecimento Global',
        title: 'Degelo acelera em diversas regiões do planeta',
        subtitle: 'O recuo de geleiras e calotas polares tornou-se um dos principais indicadores das mudanças climáticas globais.',
        image: IMAGES.degelo,
        view: 'view-clima'
    },
    {
        id: 'news-calor',
        category: 'Eventos Extremos',
        title: 'Ondas de calor batem recordes históricos em vários continentes',
        subtitle: 'Temperaturas extremas impactam cidades, sistemas de energia e a qualidade de vida de milhões de pessoas.',
        image: IMAGES.calorGlobal,
        view: 'view-clima'
    }
];
