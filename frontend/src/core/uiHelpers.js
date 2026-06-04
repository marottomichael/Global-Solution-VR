export function formatInteger(value) {
    if (value === null || value === undefined) {
        return '—';
    }
    return Number(value).toLocaleString('pt-BR');
}

export function formatDecimal(value, digits = 2) {
    if (value === null || value === undefined) {
        return '—';
    }
    const number = Number(value);
    if (Number.isNaN(number)) {
        return '—';
    }
    return number.toLocaleString('pt-BR', {
        minimumFractionDigits: digits,
        maximumFractionDigits: digits
    });
}

export function scrollToSection(selector) {
    const target = document.querySelector(selector);
    if (target) {
        target.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}
