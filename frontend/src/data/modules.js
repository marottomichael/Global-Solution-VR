/**
 * Dados estáticos dos módulos MEMORA.
 * Preparado para substituição por respostas da API Spring Boot.
 */

import { IMAGES } from './assets.js';

export const MODULES = {
    clima: {
        id: 'clima',
        view: 'view-clima',
        title: 'Memória Climática',
        subtitle: 'Visualize transformações ambientais através de dados espaciais e comparação temporal.',
        tag: 'Módulo principal',
        icon: '🌐',
        scenarios: [
            {
                id: 'amazonia',
                image: IMAGES.amazoniaDesmatada,
                title: 'Amazônia',
                region: 'Amazônia Legal',
                coords: { lat: '-3.4653', lng: '-62.2159', x: 42, y: 48 },
                timeline: {
                    1980: {
                        label: '1980',
                        title: 'Floresta intacta',
                        description: 'Cobertura florestal estimada em 98% da região. Bioma estável com alta biodiversidade registrada por satélites Landsat.',
                        metrics: { cobertura: '98%', co2: '1.2 Gt/ano', temperatura: '+0.2°C' }
                    },
                    2000: {
                        label: '2000',
                        title: 'Pressão antrópica',
                        description: 'Primeiros alertas de desmatamento em escala regional. Corredores de desmatamento visíveis nas imagens MODIS.',
                        metrics: { cobertura: '89%', co2: '0.9 Gt/ano', temperatura: '+0.6°C' }
                    },
                    2026: {
                        label: '2026',
                        title: 'Ponto crítico',
                        description: 'Mosaico de degradação e áreas regeneradas. IA identifica zonas de transição e risco de savanização.',
                        metrics: { cobertura: '76%', co2: '0.4 Gt/ano', temperatura: '+1.4°C' }
                    },
                    2050: {
                        label: '2050',
                        title: 'Projeção climática',
                        description: 'Cenário projetado com redução de 40% da umidade regional. Simulação de impacto em chuvas e biodiversidade.',
                        metrics: { cobertura: '58%', co2: '0.1 Gt/ano', temperatura: '+2.8°C' }
                    }
                }
            },
            {
                id: 'enchente-rs',
                image: IMAGES.enchentesRs,
                title: 'Enchentes RS',
                region: 'Rio Grande do Sul',
                coords: { lat: '-30.0346', lng: '-51.2177', x: 68, y: 72 },
                timeline: {
                    1980: { label: '1980', title: 'Padrão hidrológico estável', description: 'Níveis históricos dentro da média. Bacias hidrográficas sem eventos extremos recorrentes.', metrics: { nivel: 'Normal', areas: '0 km²', pop: '—' } },
                    2000: { label: '2000', title: 'Eventos isolados', description: 'Registros pontuais de cheias sazonais. Urbanização costeira e rios amplia áreas de risco.', metrics: { nivel: 'Moderado', areas: '120 km²', pop: '12 mil' } },
                    2026: { label: '2026', title: 'Recuperação pós-enchente', description: 'Análise comparativa de infraestrutura e manchas de inundação persistentes após eventos extremos.', metrics: { nivel: 'Elevado', areas: '2.400 km²', pop: '2.3 mi' } },
                    2050: { label: '2050', title: 'Risco climático ampliado', description: 'Projeção de precipitação extrema +18% na região sul. Digital twin de evacuação e resiliência.', metrics: { nivel: 'Crítico', areas: '4.100 km²', pop: '3.8 mi' } }
                }
            },
            {
                id: 'queimadas',
                image: IMAGES.incendioFlorestal,
                title: 'Queimadas',
                region: 'Pantanal & Cerrado',
                coords: { lat: '-17.7833', lng: '-57.4167', x: 55, y: 58 },
                timeline: {
                    1980: { label: '1980', title: 'Regime de fogo natural', description: 'Ciclos sazonais controlados. Focos térmicos esporádicos detectados por satélite.', metrics: { focos: '340/ano', area: '0.8M ha', co2: 'Baixo' } },
                    2000: { label: '2000', title: 'Expansão agrícola', description: 'Correlação entre desmatamento e focos de calor. Queimadas associadas ao avanço da fronteira.', metrics: { focos: '12k/ano', area: '2.1M ha', co2: 'Médio' } },
                    2026: { label: '2026', title: 'Mega incêndios', description: 'Eventos de grande escala com impacto transnacional na qualidade do ar e carbono liberado.', metrics: { focos: '89k/ano', area: '5.4M ha', co2: 'Alto' } },
                    2050: { label: '2050', title: 'Zonas de risco permanente', description: 'Modelo preditivo indica janelas críticas de 120 dias/ano com risco extremo de incêndio.', metrics: { focos: '120k/ano', area: '8.2M ha', co2: 'Crítico' } }
                }
            },
            {
                id: 'degelo',
                image: IMAGES.degelo,
                title: 'Degelo',
                region: 'Antártica / Groenlândia',
                coords: { lat: '-75.2500', lng: '-0.0750', x: 78, y: 22 },
                timeline: {
                    1980: { label: '1980', title: 'Geleira estável', description: 'Taxa de derretimento dentro dos parâmetros do século XX. Espessura de gelo mantida.', metrics: { perda: '0.1 mm/ano', nivelMar: '+0 mm', temp: '-12°C' } },
                    2000: { label: '2000', title: 'Aceleração inicial', description: 'Primeiras evidências de recuo glacial acelerado nas bordas da plataforma continental.', metrics: { perda: '1.2 mm/ano', nivelMar: '+3 mm', temp: '-10°C' } },
                    2026: { label: '2026', title: 'Derretimento acelerado', description: 'Perda de massa glacial 4× superior à média histórica. Satélites ICEsat-2 confirmam tendência.', metrics: { perda: '4.8 mm/ano', nivelMar: '+12 mm', temp: '-7°C' } },
                    2050: { label: '2050', title: 'Ponto de não retorno regional', description: 'Projeção de contribuição significativa para elevação do nível do mar em áreas costeiras globais.', metrics: { perda: '9.2 mm/ano', nivelMar: '+28 mm', temp: '-4°C' } }
                }
            }
        ]
    },
    patrimonio: {
        id: 'patrimonio',
        view: 'view-patrimonio',
        title: 'Patrimônio Digital',
        subtitle: 'Reconstrua e preserve patrimônio cultural ameaçado por desastres e conflitos.',
        tag: 'Heritage Twin',
        icon: '🏛️',
        scenarios: [
            {
                id: 'museu-nacional',
                image: IMAGES.museuChamas,
                title: 'Museu Nacional',
                region: 'Rio de Janeiro, Brasil',
                coords: { lat: '-22.9064', lng: '-43.1729', x: 72, y: 55 },
                timeline: {
                    1980: { label: '1980', title: 'Século XIX preservado', description: 'Acervo completo no Palácio de São Cristóvão. Reconstrução digital do estado original.', metrics: { acervo: '20 mi itens', estado: 'Íntegro', visitantes: '180k/ano' } },
                    2000: { label: '2000', title: 'Patrimônio vivo', description: 'Centro de pesquisa e memória nacional. Documentação 3D iniciada para peças prioritárias.', metrics: { acervo: '20 mi itens', estado: 'Preservado', visitantes: '420k/ano' } },
                    2026: { label: '2026', title: 'Memória reconstruída', description: 'Experiência imersiva reconstrói salas e exposições perdidas no incêndio de 2018 via IA generativa.', metrics: { acervo: 'Recuperação parcial', estado: 'Digital twin', visitantes: '1.2M VR/ano' } },
                    2050: { label: '2050', title: 'Arquivo imortal', description: 'Patrimônio 100% digitalizado e acessível globalmente em realidade mista.', metrics: { acervo: 'Completo (digital)', estado: 'Imersivo', visitantes: '8M/ano' } }
                }
            },
            {
                id: 'palmyra',
                image: IMAGES.palmyra,
                title: 'Palmyra',
                region: 'Síria',
                coords: { lat: '34.5560', lng: '38.2739', x: 58, y: 38 },
                timeline: {
                    1980: { label: '1980', title: 'Cidade oasis', description: 'Ruínas romanas e persas em excelente estado de conservação no deserto sírio.', metrics: { estruturas: '100%', turismo: 'Ativo', risco: 'Baixo' } },
                    2000: { label: '2000', title: 'Patrimônio UNESCO', description: 'Sítio tombado com monitoramento internacional e documentação fotogramétrica.', metrics: { estruturas: '95%', turismo: 'Moderado', risco: 'Médio' } },
                    2026: { label: '2026', title: 'Reconstrução digital', description: 'Modelo 3D reconstruído a partir de arquivos pré-conflito e imagens de drone.', metrics: { estruturas: '40% físico', turismo: 'VR only', risco: 'Alto' } },
                    2050: { label: '2050', title: 'Heritage cloud', description: 'Experiência educativa global sem necessidade de deslocamento físico ao território.', metrics: { estruturas: 'Digital 100%', turismo: 'Global VR', risco: 'Controlado' } }
                }
            },
            {
                id: 'machu-picchu',
                image: IMAGES.machuPicchu,
                title: 'Machu Picchu',
                region: 'Peru',
                coords: { lat: '-13.1631', lng: '-72.5450', x: 38, y: 52 },
                timeline: {
                    1980: { label: '1980', title: 'Cidadela isolada', description: 'Estruturas incas preservadas pelo isolamento geográfico e clima de montanha.', metrics: { erosao: 'Mínima', visitantes: '200k', risco: 'Baixo' } },
                    2000: { label: '2000', title: 'Turismo global', description: 'Pressão turística crescente. Monitoramento de encostas e estruturas de pedra.', metrics: { erosao: 'Moderada', visitantes: '800k', risco: 'Médio' } },
                    2026: { label: '2026', title: 'Clima e conservação', description: 'IA modela impacto de chuvas extremas e deslizamentos na integridade estrutural.', metrics: { erosao: 'Acelerada', visitantes: '1.5M', risco: 'Alto' } },
                    2050: { label: '2050', title: 'Preservação preditiva', description: 'Gêmeo digital com sensores virtuais previne colapsos e orienta intervenções.', metrics: { erosao: 'Gerenciada', visitantes: 'Cap controlada', risco: 'Mitigado' } }
                }
            }
        ]
    },
    simulacoes: {
        id: 'simulacoes',
        view: 'view-simulacoes',
        title: 'Simulações de Desastres',
        subtitle: 'Experiências imersivas de eventos extremos para conscientização e treinamento.',
        tag: 'Disaster Twin',
        icon: '⚡',
        scenarios: [
            {
                id: 'rs-2024',
                image: IMAGES.enchentesRs,
                title: 'RS 2024',
                region: 'Enchentes no Rio Grande do Sul',
                coords: { lat: '-30.0346', lng: '-51.2177', x: 68, y: 72 },
                timeline: {
                    1980: { label: 'Pré-evento', title: 'Baseline territorial', description: 'Modelo hidrológico histórico da região antes dos eventos extremos de 2024.', metrics: { casas: '—', agua: 'Normal', alerta: 'Verde' } },
                    2000: { label: 'Simulação T+0', title: 'Inundação em tempo real', description: 'Simulação imersiva do pico de cheia com projeção de áreas afetadas hora a hora.', metrics: { casas: '580k', agua: '+4.2m', alerta: 'Vermelho' } },
                    2026: { label: 'Reconstrução', title: 'Lições aprendidas', description: 'Comparativo de políticas de prevenção e infraestrutura de drenagem pós-desastre.', metrics: { casas: 'Recuperação', agua: 'Monitorado', alerta: 'Amarelo' } },
                    2050: { label: 'Resiliência', title: 'Cidade adaptativa', description: 'Digital twin urbano com sistemas de alerta precoce e evacuação simulada.', metrics: { casas: 'Protegidas', agua: 'Controlado', alerta: 'Verde' } }
                }
            },
            {
                id: 'katrina',
                image: IMAGES.furacao,
                title: 'Furacão Katrina',
                region: 'Nova Orleans, EUA',
                coords: { lat: '29.9511', lng: '-90.0715', x: 28, y: 45 },
                timeline: {
                    1980: { label: 'Antes', title: 'Delta do Mississippi', description: 'Ecossistema costeiro e sistema de diques antes do evento de 2005.', metrics: { vento: '—', mortes: '—', dano: '—' } },
                    2000: { label: 'T+12h', title: 'Impacto máximo', description: 'Simulação de vento, storm surge e falha de diques com dados históricos NOAA.', metrics: { vento: '280 km/h', mortes: '1.833', dano: 'US$ 125 bi' } },
                    2026: { label: 'Hoje', title: 'Legado e desigualdade', description: 'Análise socioespacial dos bairros afetados e políticas de reconstrução.', metrics: { vento: '—', pop: 'Deslocada', dano: 'Em recuperação' } },
                    2050: { label: 'Futuro', title: 'Costa resiliente', description: 'Cenário de nova mega tempestade com infraestrutura adaptativa projetada.', metrics: { vento: '300 km/h proj.', mortes: 'Mitigável', dano: 'Reduzido 60%' } }
                }
            },
            {
                id: 'brumadinho',
                image: IMAGES.terremotos,
                title: 'Brumadinho',
                region: 'Minas Gerais, Brasil',
                coords: { lat: '-20.1435', lng: '-44.1998', x: 62, y: 50 },
                timeline: {
                    1980: { label: 'Pré-barragem', title: 'Vale original', description: 'Topografia e comunidades antes da construção da barragem de rejeitos.', metrics: { volume: '—', vidas: '—', risco: 'Não mapeado' } },
                    2000: { label: 'T+5min', title: 'Rompimento', description: 'Simulação da onda de rejeitos com velocidade, volume e área de destruição.', metrics: { volume: '12M m³', vidas: '270', risco: 'Crítico' } },
                    2026: { label: 'Reparação', title: 'Memória e justiça', description: 'Documentação 3D do território afetado e rotas de fuga simuladas.', metrics: { volume: 'Remediado', vidas: 'Homenageadas', risco: 'Monitorado' } },
                    2050: { label: 'Prevenção', title: 'IA de risco', description: 'Sistema preditivo de integridade de barragens com alerta em tempo real.', metrics: { volume: 'Controlado', vidas: 'Protegidas', risco: 'Baixo' } }
                }
            }
        ]
    },
    labs: {
        id: 'labs',
        view: 'view-labs',
        title: 'Memora Labs',
        subtitle: 'Laboratório de cenários hipotéticos — explore futuros alternativos com IA.',
        tag: 'What-if Engine',
        icon: '🔬',
        scenarios: [
            {
                id: 'dinos',
                emoji: '🦕',
                title: 'E se os dinossauros não tivessem sido extintos?',
                region: 'Cenário global',
                coords: { lat: '0.0000', lng: '0.0000', x: 50, y: 50 },
                hint: 'Biosfera alternativa · 66M anos AP',
                timeline: {
                    1980: { label: 'Era', title: 'Mundo mesozoico estendido', description: 'IA projeta ecossistemas dominados por répteis inteligentes e flora adaptada.', metrics: { especies: '+40%', humanos: 'Não existem', co2: 'Estável' } },
                    2000: { label: 'Transição', title: 'Competição evolutiva', description: 'Mamíferos coexistem em nichos reduzidos. Cidades não existem — florestas dominam.', metrics: { especies: 'Diversas', humanos: '—', co2: 'Baixo' } },
                    2026: { label: 'Hoje alt.', title: 'Civilização réptil?', description: 'Especulação científica sobre sociedades não humanas e uso sustentável de recursos.', metrics: { especies: 'Dominantes', humanos: '—', co2: 'Variável' } },
                    2050: { label: 'Futuro alt.', title: 'Planeta verde profundo', description: 'Supercontinente coberto por biodiversidade réptil. Sem combustíveis fósseis.', metrics: { especies: 'Estáveis', humanos: '—', co2: '280 ppm' } }
                }
            },
            {
                id: 'amazonia-intacta',
                emoji: '🌳',
                title: 'E se a Amazônia nunca tivesse sido desmatada?',
                region: 'Amazônia',
                coords: { lat: '-3.4653', lng: '-62.2159', x: 42, y: 48 },
                hint: 'Clima global · bomba de umidade',
                timeline: {
                    1980: { label: 'Linha base', title: 'Floresta primordial', description: '100% de cobertura. Rio Negro e Amazonas em níveis históricos máximos.', metrics: { cobertura: '100%', chuva: '+20% BR', co2: 'Sequestrado' } },
                    2000: { label: 'Mundo sem corte', title: 'Clima estável', description: 'Correntes de umidade mantidas. Agricultura limitada a agroflorestas.', metrics: { cobertura: '100%', chuva: 'Estável', co2: '450 ppm evitado' } },
                    2026: { label: 'Hoje alt.', title: 'Pulmão do planeta', description: 'Temperatura global 0.5°C menor. Biodiversidade em níveis pré-industriais.', metrics: { cobertura: '100%', chuva: '+15%', co2: 'Em queda' } },
                    2050: { label: 'Futuro alt.', title: 'Bioeconomia global', description: 'Amazônia como centro de bioindústria sustentável e turismo científico.', metrics: { cobertura: '100%', chuva: 'Ótima', co2: '350 ppm' } }
                }
            },
            {
                id: 'nivel-mar',
                emoji: '🌊',
                title: 'E se o nível do mar subisse 5 metros?',
                region: 'Costas globais',
                coords: { lat: '-22.9068', lng: '-43.1729', x: 72, y: 55 },
                hint: 'Impacto em 800M pessoas',
                timeline: {
                    1980: { label: 'Referência', title: 'Costas atuais', description: 'Linha de costa de referência para comparação com cenário de +5m.', metrics: { afetados: '0', cidades: '—', perda: '—' } },
                    2000: { label: '+2m', title: 'Primeiras cidades', description: 'Bangladesh, Maldivas e delta do Mekong com inundações permanentes.', metrics: { afetados: '200M', cidades: '45', perda: 'US$ 4 tri' } },
                    2026: { label: '+5m', title: 'Mundo inundado', description: 'Rio, Miami, Amsterdam, Shanghai parcialmente submersas. Migração em massa.', metrics: { afetados: '800M', cidades: '136', perda: 'US$ 18 tri' } },
                    2050: { label: 'Adaptação', title: 'Cidades anfíbias', description: 'Arquitetura flutuante e barreiras verdes. Novos mapas de habitabilidade.', metrics: { afetados: 'Gerenciados', cidades: 'Adaptadas', perda: 'Mitigada' } }
                }
            }
        ]
    }
};

export const HOME_MODULES = [
    { id: 'clima', title: 'Memória Climática', description: 'Transformações ambientais via satélite e comparação temporal.', tag: 'Principal', image: IMAGES.amazoniaAntes, view: 'view-clima' },
    { id: 'patrimonio', title: 'Patrimônio Digital', description: 'Reconstrução imersiva de patrimônio cultural ameaçado.', tag: 'Heritage', image: IMAGES.museuNacional, view: 'view-patrimonio' },
    { id: 'simulacoes', title: 'Simulações de Desastres', description: 'Eventos extremos em experiências imersivas educativas.', tag: 'Disaster', image: IMAGES.enchentes, view: 'view-simulacoes' },
    { id: 'labs', title: 'Memora Labs', description: 'Cenários hipotéticos — e se o planeta fosse diferente?', tag: 'What-if', image: IMAGES.planetaTerra, view: 'view-labs' }
];

export const ENV_METRICS_DEMO = [
    { label: 'CO₂ atmosférico', value: '421 ppm', delta: '+2.1%', trend: 'up' },
    { label: 'Temperatura média', value: '+1.28°C', delta: 'vs pré-industrial', trend: 'up' },
    { label: 'Área florestal', value: '-4.2M ha', delta: 'perda anual', trend: 'up' },
    { label: 'Nível do mar', value: '+9.4 cm', delta: 'última década', trend: 'up' }
];

export const CHART_BARS = [42, 58, 71, 65, 88, 76, 92, 85, 78, 95, 82, 90];
