# Reorganização de pastas — o que mudou (nada foi apagado)

O `git status` mostra muitos arquivos como **deleted** na raiz porque eles foram **movidos** para novas pastas. O Git só registra “delete + arquivo novo” quando a movimentação não usa `git mv`.

## Mapa de movimentação

| Antes (caminho antigo) | Agora (caminho novo) |
|------------------------|----------------------|
| `pom.xml`, `mvnw`, `mvnw.cmd`, `.mvn/` | `memora-backend/` |
| `src/main/java/...` (todo o backend) | `memora-backend/src/main/java/...` |
| `src/main/resources/application.properties` | `memora-backend/src/main/resources/application.properties` |
| `src/test/...` | `memora-backend/src/test/...` |
| `requests/global-solution.http` | `memora-backend/requests/global-solution.http` |
| `src/main/resources/static/index.html` | `frontend/index.html` (fonte) |
| `src/main/resources/static/css/` | `frontend/src/styles/` |
| `src/main/resources/static/js/` | `frontend/src/` (`components`, `core`, `data`, `services`, `main.js`) |
| `docs/` (sql, modelagem, etc.) | **inalterado** na raiz |
| `docs/diagramas/` | **inalterado** na raiz |

## O que significam os “deleted” no git status

- **Controllers, Services, Models, DTOs, Repositories** → código da equipe no backend; agora em `memora-backend/`.
- **pom.xml / mvnw** → Maven do projeto; agora em `memora-backend/`.
- **static/css e static/js** → interface; fonte em `frontend/src/`; após `npm run build`, cópia compilada em `memora-backend/src/main/resources/static/`.

## Único arquivo novo no backend

- `memora-backend/.../config/WebConfig.java` — CORS para desenvolvimento com Vite (`localhost:5173`). Não substitui nada existente.

## Como o Git deve tratar isso no commit

Na raiz do repositório:

```powershell
git add -A
git status
```

O Git costuma detectar **renames** automaticamente. Após o commit, o histórico dos arquivos da equipe permanece ligado ao conteúdo (só mudou o caminho).

## Verificação rápida

- Backend Java: ~55 classes em `memora-backend/src/main/java/`
- Frontend fonte: `frontend/src/`
- Documentação da equipe: `docs/` (incl. `docs/diagramas/`)
