USE global_solution;

-- Dados de demonstracao. Execute apos schema.sql em banco vazio ou ajuste IDs se necessario.

INSERT INTO usuarios (id, nome, email, senha, tipo_usuario, data_criacao) VALUES
(1, 'Ana Silva', 'ana.silva@email.com', '123456', 'VISITANTE', '2026-06-02 18:00:00.000000');

INSERT INTO fontes_dados (id, nome, tipo_fonte, descricao, url_referencia, confiabilidade_base) VALUES
(1, 'Dados climaticos IPCC', 'BASE_CLIMATICA', 'Referencia simulada para projecoes climaticas', 'https://exemplo.org/ipcc', 85.00),
(2, 'Mapa historico SP', 'IMAGEM_HISTORICA', 'Imagens de arquivo municipal', NULL, 72.00);

INSERT INTO localizacoes (id, nome_referencia, latitude, longitude, pais, cidade, descricao) VALUES
(1, 'Orla de Santos', -23.9608000, -46.3332000, 'Brasil', 'Santos', 'Regiao costeira sujeita a elevacao do nivel do mar'),
(2, 'Centro de Sao Paulo', -23.5505000, -46.6333000, 'Brasil', 'Sao Paulo', 'Regiao central historica');

INSERT INTO solicitacoes_experiencia (
    id, titulo, descricao_usuario, categoria_experiencia, tipo_temporal, ano_referencia,
    nivel_detalhamento, status, prompt_gerado, data_solicitacao, data_processamento,
    usuario_id, localizacao_id
) VALUES
(1, 'Impacto do aumento do nivel do mar',
 'Visualizar como a regiao ficaria com 2 metros a mais de mar',
 'CONSCIENTIZACAO_CLIMATICA', 'FUTURO', 2100, 'ALTO', 'CONCLUIDA',
 'Gerar experiencia imersiva para Orla de Santos, categoria CONSCIENTIZACAO_CLIMATICA.',
 '2026-06-02 18:05:00.000000', '2026-06-02 18:05:01.000000', 1, 1),
(2, 'Sao Paulo no seculo XIX',
 'Mostrar como era a regiao central ha cerca de 200 anos',
 'RECONSTRUCAO_HISTORICA', 'PASSADO', 1825, 'MEDIO', 'CONCLUIDA',
 'Gerar experiencia imersiva para Centro de Sao Paulo, categoria RECONSTRUCAO_HISTORICA.',
 '2026-06-02 18:10:00.000000', '2026-06-02 18:10:01.000000', 1, 2);

INSERT INTO parametros_solicitacao (id, nome, valor, unidade, tipo_parametro, solicitacao_id) VALUES
(1, 'aumento_nivel_mar', '2', 'metros', 'NUMERICO', 1),
(2, 'deslocamento_temporal', '200', 'anos_atras', 'NUMERICO', 2);

INSERT INTO solicitacao_fontes_dados (solicitacao_id, fonte_dados_id) VALUES
(1, 1),
(2, 2);

INSERT INTO resultados_geracao (
    id, descricao_resultado, narrativa_imersiva, indice_confiabilidade,
    observacoes_tecnicas, data_criacao, solicitacao_id
) VALUES
(1, 'Simulacao de impactos climaticos na regiao de Orla de Santos.',
 'Projecao de elevacao do nivel do mar sobre a orla.', 87.00,
 'Resultado simulado para demonstracao academica.', '2026-06-02 18:05:01.000000', 1),
(2, 'Reconstrucao visual estimada do ambiente historico de Centro de Sao Paulo.',
 'Cenario passado da regiao central.', 74.00,
 'Estimativa visual com referencias historicas simuladas.', '2026-06-02 18:10:01.000000', 2);

INSERT INTO metricas_resultado (id, nome, valor, unidade, descricao, resultado_id) VALUES
(1, 'nivel_risco', 78.0000, 'pontos', 'Estimativa simulada de risco ambiental', 1),
(2, 'area_afetada_estimada', 42.0000, 'percentual', 'Percentual estimado de area impactada', 1),
(3, 'impacto_visual_estimado', 85.0000, 'pontos', 'Intensidade visual percebida', 1),
(4, 'confiabilidade_historica', 72.0000, 'percentual', 'Grau estimado de aderencia historica', 2),
(5, 'nivel_reconstrucao', 80.0000, 'pontos', 'Completude da reconstrucao visual', 2);

INSERT INTO avaliacoes (id, nota, comentario, data_avaliacao, usuario_id, solicitacao_id) VALUES
(1, 5, 'Experiencia muito didatica e visualmente impactante', '2026-06-02 18:15:00.000000', 1, 1);
