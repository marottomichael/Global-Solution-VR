# MEMORA — Guia de desenvolvimento

Projeto monorepo com **frontend** (Vite + JavaScript) e **memora-backend** (Spring Boot + MySQL).

## Estrutura

```
Global-Solution-VR/
├── frontend/              ← npm install / npm run dev (aqui!)
├── memora-backend/        ← mvnw spring-boot:run
├── docs/
├── docker-compose.yml     ← MySQL opcional
└── DEV_SETUP_GUIDE.md
```

## Pré-requisitos

- Node.js 18+
- JDK 17+
- MySQL 8 (local ou Docker)

## 1. Banco de dados

```powershell
# Na raiz do repositório
docker compose up -d
```

Ou use MySQL local e ajuste `memora-backend/src/main/resources/application.properties`.

## 2. Backend (API — porta 8081)

```powershell
cd memora-backend
.\mvnw.cmd spring-boot:run
```

API: http://localhost:8081

## 3. Frontend (interface — porta 5173)

```powershell
cd frontend
copy .env.example .env
npm install
npm run dev
```

Abra: http://localhost:5173

O Vite faz **proxy** das rotas `/estatisticas`, `/solicitacoes`, etc. para o backend.

## Build de produção (frontend embutido no JAR)

```powershell
cd frontend
npm run build
```

Isso gera os arquivos em `memora-backend/src/main/resources/static/`. Depois:

```powershell
cd ..\memora-backend
.\mvnw.cmd spring-boot:run
```

Acesse tudo em http://localhost:8081 (sem Vite).

## Comandos úteis

| Onde | Comando | Descrição |
|------|---------|-----------|
| `frontend/` | `npm install` | Instala dependências |
| `frontend/` | `npm run dev` | Dev server com hot reload |
| `frontend/` | `npm run build` | Build para o backend |
| `memora-backend/` | `.\mvnw.cmd test` | Testes Java |

## Problema comum

**`npm install` na raiz não funciona** — o `package.json` está em `frontend/`, não na raiz do repositório (igual ao projeto ZUrbi).
