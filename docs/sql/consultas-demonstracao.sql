USE global_solution;

-- Listar usuarios
SELECT id, nome, email, tipo_usuario, data_criacao
FROM usuarios;

-- Solicitacoes com usuario e localizacao
SELECT
    s.id,
    s.titulo,
    s.categoria_experiencia,
    s.status,
    u.nome AS usuario,
    l.nome_referencia AS localizacao,
    l.cidade
FROM solicitacoes_experiencia s
INNER JOIN usuarios u ON u.id = s.usuario_id
INNER JOIN localizacoes l ON l.id = s.localizacao_id
ORDER BY s.id;

-- Parametros de uma solicitacao
SELECT p.id, p.nome, p.valor, p.unidade, p.tipo_parametro
FROM parametros_solicitacao p
WHERE p.solicitacao_id = 1;

-- Resultado e metricas de uma solicitacao
SELECT
    r.id AS resultado_id,
    r.descricao_resultado,
    r.indice_confiabilidade,
    m.nome AS metrica,
    m.valor,
    m.unidade
FROM resultados_geracao r
LEFT JOIN metricas_resultado m ON m.resultado_id = r.id
WHERE r.solicitacao_id = 1;

-- Media de avaliacoes
SELECT AVG(nota) AS media_avaliacoes
FROM avaliacoes;

-- Solicitacoes por categoria
SELECT categoria_experiencia, COUNT(*) AS total
FROM solicitacoes_experiencia
GROUP BY categoria_experiencia;

-- Solicitacoes concluidas
SELECT id, titulo, categoria_experiencia, data_processamento
FROM solicitacoes_experiencia
WHERE status = 'CONCLUIDA';
