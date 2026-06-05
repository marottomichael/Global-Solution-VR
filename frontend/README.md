# MEMORA Frontend

Interface da plataforma MEMORA (Vite + JavaScript vanilla modular).

## Setup

```powershell
cd frontend
copy .env.example .env
npm install
npm run dev
```

- Dev: http://localhost:5173
- API proxy: http://localhost:8081 (Spring Boot deve estar rodando)

## Scripts

| Script | Ação |
|--------|------|
| `npm run dev` | Servidor de desenvolvimento |
| `npm run build` | Gera assets em `../memora-backend/src/main/resources/static` |
| `npm run preview` | Preview do build |

## Estrutura `src/`

```
src/
├── main.js           # Entrada da aplicação
├── styles/           # CSS (variáveis, layout, componentes, páginas)
├── components/       # UI (timeline, mapa, dashboard, …)
├── core/             # router, apiClient, appState
├── data/             # módulos e cenários (mock → API futura)
└── services/         # chamadas REST
```
