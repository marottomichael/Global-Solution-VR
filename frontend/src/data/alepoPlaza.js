import { IMAGES } from './assets.js';

export const ALEPO_PLAZA = {
    location: {
        name: 'Praça Al-Nur',
        region: 'Centro histórico de Alepo, Síria',
        subtitle: 'Simulação imersiva · Heritage Twin'
    },
    scenarios: [
        {
            year: 2008,
            image: IMAGES.alepo2008,
            phase: 'Antes da guerra',
            title: 'Vida na praça',
            description: 'A fonte central reúne famílias e crianças brincando ao entardecer. O comércio local e o convívio público ainda definem o ritmo do centro histórico.'
        },
        {
            year: 2010,
            image: IMAGES.alepo2010,
            phase: 'Pré-conflito',
            title: 'O silêncio que antecede',
            description: 'O mesmo ângulo, porém com poucas pessoas na praça. A rotina se retrai, lojas fecham mais cedo e a tensão política começa a esvaziar o coração da cidade.'
        },
        {
            year: 2025,
            image: IMAGES.alepo2025,
            phase: 'Conflito ativo',
            title: 'Cidade em guerra',
            description: 'Edificações destruídas, fachadas desmoronadas e ruínas no lugar do comércio. A praça deixa de ser encontro e passa a registrar o impacto direto do conflito urbano.'
        },
        {
            year: 2050,
            image: IMAGES.alepo2050,
            phase: 'Abandono',
            title: 'Memória em ruínas',
            description: 'Cenário de devastação e abandono sobre um local que já foi lar de uma população vibrante. A simulação evidencia o custo humano e cultural de décadas de violência.'
        }
    ]
};
