sequenceDiagram
participant Usuário (Frontend)
participant Sistema (API REST)
participant Banco de Dados (MySQL)

Usuário (Frontend)->>Sistema (API REST): 1. POST /solicitacoes com CriarSolicitacaoExperienciaRequest (local, período, parâmetros)

activate Sistema (API REST)
Sistema (API REST)->>Sistema (API REST): 2. Valida a requisição (@Valid) e o usuário (JWT/ID)

Sistema (API REST)->>Banco de Dados (MySQL): 3. Persiste entidades iniciais (Localizacao, ParametroSolicitacao)
Sistema (API REST)->>Banco de Dados (MySQL): 4. Cria e persiste 'SolicitacaoExperiencia' com status "SOLICITADA"

Sistema (API REST)-->>Usuário (Frontend): 5. Retorna um DTO inicial com o ID da solicitação e status "SOLICITADA" (Resposta Rápida)
deactivate Sistema (API REST)

Note over Usuário (Frontend), Sistema (API REST): O processamento pesado ocorre de forma assíncrona/separada.

activate Sistema (API REST)
Sistema (API REST)->>Banco de Dados (MySQL): 6. Atualiza status para "PROCESSANDO"

Sistema (API REST)->>Sistema (API REST): 7. [SIMULAÇÃO DA IA] Monta 'promptGerado' com base nos dados da solicitação
Sistema (API REST)->>Sistema (API REST): 8. [SIMULAÇÃO DA IA] Gera 'ResultadoGeracao' (descrição textual, URL de modelo 3D simulado)
Sistema (API REST)->>Sistema (API REST): 9. [SIMULAÇÃO DA IA] Gera 'MetricaResultado' (confiabilidade histórica, nível de risco, etc.)

Sistema (API REST)->>Banco de Dados (MySQL): 10. Persiste 'ResultadoGeracao' e 'MetricaResultado'
Sistema (API REST)->>Banco de Dados (MySQL): 11. Atualiza status para "CONCLUIDA" e dataProcessamento
deactivate Sistema (API REST)

Note over Usuário (Frontend), Sistema (API REST): Mais tarde, o frontend busca o resultado final.

Usuário (Frontend)->>Sistema (API REST): 12. GET /solicitacoes/{id} para verificar o status

activate Sistema (API REST)
Sistema (API REST)->>Banco de Dados (MySQL): 13. Consulta a solicitação completa com todos os seus relacionamentos (resultado, métricas, etc.)

Banco de Dados (MySQL)-->>Sistema (API REST): 14. Retorna os dados completos
Sistema (API REST)-->>Usuário (Frontend): 15. Retorna o DTO 'SolicitacaoExperienciaResponse' completo com status "CONCLUIDA" e todos os dados gerados
deactivate Sistema (API REST)

Usuário (Frontend)->>Usuário (Frontend): 16. Renderiza a experiência imersiva em VR/3D com base nos dados recebidos