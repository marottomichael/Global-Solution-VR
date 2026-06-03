# Global Solution

Backend de uma plataforma de experiências imersivas sob demanda. O usuário informa localização, categoria, descrição e parâmetros flexíveis; o sistema registra a solicitação, simula o processamento e retorna um resultado conceitual com métricas.

**Importante:** neste protótipo, IA generativa, satélites, mapas reais e modelos 3D são **simulados**. Não há integração com serviços externos.

## Tecnologias

- Java 17
- Spring Boot 3.4
- Spring Web, Spring Data JPA, Bean Validation
- MySQL 8
- Maven Wrapper (`mvnw.cmd`)

## Pré-requisitos

- JDK 17 ou superior
- MySQL rodando em `localhost:3306`

## Configuração do MySQL

1. Instale e inicie o MySQL Server.
2. Ajuste, se necessário, `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/global_solution?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=SUA_SENHA
server.port=8081
```

O banco `global_solution` é criado automaticamente na primeira conexão.

## Como executar

```powershell
cd c:\Projetos\Global-Solution-VR
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

A API fica disponível em **http://localhost:8081**.

## Base URL

```
http://localhost:8081
```

## Endpoints principais

| Recurso | Método | Rota |
|---------|--------|------|
| Usuários | POST | `/usuarios` |
| Usuários | GET | `/usuarios`, `/usuarios/{id}` |
| Usuários | PUT | `/usuarios/{id}` |
| Usuários | DELETE | `/usuarios/{id}` |
| Fontes de dados | POST | `/fontes-dados` |
| Fontes de dados | GET | `/fontes-dados`, `/fontes-dados/{id}`, `/fontes-dados/tipo/{tipoFonte}` |
| Fontes de dados | PUT | `/fontes-dados/{id}` |
| Fontes de dados | DELETE | `/fontes-dados/{id}` |
| Solicitações | POST | `/solicitacoes` |
| Solicitações | GET | `/solicitacoes/{id}`, `/solicitacoes/usuario/{usuarioId}`, `/solicitacoes/categoria/{categoria}`, `/solicitacoes/status/{status}` |
| Solicitações | PATCH | `/solicitacoes/{id}/status/{status}` |
| Avaliações | POST | `/avaliacoes` |
| Avaliações | GET | `/avaliacoes/solicitacao/{id}`, `/avaliacoes/usuario/{id}` |
| Estatísticas | GET | `/estatisticas` |

## Fluxo principal (demonstração)

1. Criar usuário
2. Cadastrar fontes de dados
3. Criar solicitação de experiência (climática ou histórica)
4. Consultar solicitação — retorna status `CONCLUIDA`, prompt, resultado e métricas
5. Registrar avaliação
6. Consultar estatísticas gerais

Solicitações são mantidas como **histórico** — não há exclusão física. Apenas o status pode ser atualizado via PATCH.

## CRUD e persistência

| Operação | Recursos |
|----------|----------|
| Create | POST em usuarios, fontes-dados, solicitacoes, avaliacoes |
| Read | GET nos endpoints listados acima |
| Update | PUT em usuarios e fontes-dados; PATCH de status em solicitacoes |
| Delete | DELETE em usuarios e fontes-dados (sem vínculos) |

Repositórios Spring Data JPA substituem DAOs manuais. Scripts SQL para documentação e demonstração:

- `docs/sql/schema.sql` — criação das tabelas
- `docs/sql/seed.sql` — dados de exemplo
- `docs/sql/consultas-demonstracao.sql` — consultas analíticas

## Testes manuais

Use o arquivo `requests/global-solution.http` com a extensão **REST Client** (VS Code/Cursor) ou importe os payloads no Postman/Insomnia.

Execute as requisições **na ordem** e ajuste os IDs (`usuarioId`, `fontesDadosIds`, `solicitacaoId`) conforme as respostas anteriores.

## Documentação complementar

- [Arquitetura do backend](docs/arquitetura-backend.md)
- [DER do banco](docs/der-banco.md)
- [Modelagem do banco](docs/modelagem-banco.md)
- [Scripts SQL](docs/sql/)
- [Roteiro do vídeo técnico](docs/roteiro-video-tecnico.md)

## Estrutura do projeto

```
src/main/java/br/com/globalsolution/
├── controller/     # Endpoints REST
├── service/        # Regras de negócio
├── repository/     # Acesso a dados (JPA)
├── model/          # Entidades e enums
├── dto/            # Request e response
└── exception/      # Tratamento global de erros
```
