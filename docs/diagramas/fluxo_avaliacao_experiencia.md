sequenceDiagram
participant Usuário (Frontend)
participant Sistema (API REST)
participant Banco de Dados (MySQL)

Usuário (Frontend)->>Usuário (Frontend): 1. Interage com a UI para fornecer uma nota (1-5) e um comentário
Usuário (Frontend)->>Sistema (API REST): 2. POST /avaliacoes com CriarAvaliacaoRequest (nota, comentário, usuario_id, solicitacao_id)

activate Sistema (API REST)
Sistema (API REST)->>Sistema (API REST): 3. Valida a requisição (@Valid) e os IDs
Sistema (API REST)->>Banco de Dados (MySQL): 4. Cria e persiste a nova entidade 'Avaliacao', associando-a ao Usuário e à Solicitação

Banco de Dados (MySQL)-->>Sistema (API REST): 5. Confirma a persistência
Sistema (API REST)-->>Usuário (Frontend): 6. Retorna status 201 Created e o DTO 'AvaliacaoResponse' da avaliação recém-criada
deactivate Sistema (API REST)

Usuário (Frontend)->>Usuário (Frontend): 7. Exibe mensagem de confirmação ("Obrigado pelo seu feedback!")