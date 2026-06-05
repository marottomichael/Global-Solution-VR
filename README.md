# MEMORA — Global Solution

Plataforma de conscientização climática (MEMORA) com experiências imersivas sob demanda. Frontend em Vite e API REST em Spring Boot.

**Protótipo:** IA, satélites e mapas reais são simulados nesta versão.

## Estrutura do repositório

```
Global-Solution-VR/
├── frontend/                 # UI MEMORA — npm install aqui
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   └── src/
├── memora-backend/           # API Spring Boot
│   ├── pom.xml
│   ├── mvnw.cmd
│   ├── requests/
│   └── src/main/java/...
├── docs/
├── docker-compose.yml
├── DEV_SETUP_GUIDE.md        # Passo a passo completo
└── .env.example
```

## Início rápido

```powershell
# 1. MySQL (opcional)
docker compose up -d

# 2. Backend
cd memora-backend
.\mvnw.cmd spring-boot:run

# 3. Frontend (outro terminal)
cd frontend
copy .env.example .env
npm install
npm run dev
```

- Interface (dev): http://localhost:5173  
- API: http://localhost:8081  

Detalhes: [DEV_SETUP_GUIDE.md](DEV_SETUP_GUIDE.md)

## Tecnologias

| Camada | Stack |
|--------|--------|
| Frontend | Vite 6, JavaScript ES modules, CSS |
| Backend | Java 17, Spring Boot 3.4, JPA, MySQL 8 |

## Endpoints principais

| Recurso | Método | Rota |
|---------|--------|------|
| Usuários | POST/GET/PUT/DELETE | `/usuarios` |
| Fontes de dados | CRUD | `/fontes-dados` |
| Solicitações | POST/GET/PATCH | `/solicitacoes` |
| Avaliações | POST/GET | `/avaliacoes` |
| Estatísticas | GET | `/estatisticas` |

Testes HTTP: `memora-backend/requests/global-solution.http`

## Documentação

- [Guia de desenvolvimento](DEV_SETUP_GUIDE.md)
- [Arquitetura do projeto](docs/project-architecture.md)
- [Arquitetura do backend](docs/arquitetura-backend.md)
- [Frontend](frontend/README.md)
- [Scripts SQL](docs/sql/)

## Build para demonstração (um único servidor)

```powershell
cd frontend
npm run build
cd ..\memora-backend
.\mvnw.cmd spring-boot:run
```

Acesse http://localhost:8081 — UI + API no mesmo processo.
