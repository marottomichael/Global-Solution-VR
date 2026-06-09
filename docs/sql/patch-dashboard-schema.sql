
USE global_solution;

ALTER TABLE solicitacoes_experiencia
    MODIFY COLUMN categoria_experiencia VARCHAR(50) NOT NULL,
    MODIFY COLUMN tipo_temporal VARCHAR(50) NOT NULL,
    MODIFY COLUMN nivel_detalhamento VARCHAR(50) NOT NULL,
    MODIFY COLUMN status VARCHAR(50) NOT NULL;

ALTER TABLE fontes_dados
    MODIFY COLUMN tipo_fonte VARCHAR(50) NOT NULL;

ALTER TABLE metricas_resultado
    ADD COLUMN IF NOT EXISTS ano_referencia INT NULL;
