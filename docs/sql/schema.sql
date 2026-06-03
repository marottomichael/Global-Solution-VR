CREATE DATABASE IF NOT EXISTS global_solution
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE global_solution;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50),
    data_criacao DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS localizacoes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome_referencia VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 7) NOT NULL,
    longitude DECIMAL(10, 7) NOT NULL,
    pais VARCHAR(255),
    cidade VARCHAR(255),
    descricao TEXT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS fontes_dados (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    tipo_fonte VARCHAR(50) NOT NULL,
    descricao TEXT,
    url_referencia VARCHAR(255),
    confiabilidade_base DECIMAL(5, 2),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS solicitacoes_experiencia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descricao_usuario TEXT,
    categoria_experiencia VARCHAR(50) NOT NULL,
    tipo_temporal VARCHAR(50) NOT NULL,
    ano_referencia INT,
    nivel_detalhamento VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    prompt_gerado TEXT,
    data_solicitacao DATETIME(6) NOT NULL,
    data_processamento DATETIME(6),
    usuario_id BIGINT NOT NULL,
    localizacao_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_solicitacao_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_solicitacao_localizacao
        FOREIGN KEY (localizacao_id) REFERENCES localizacoes (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS parametros_solicitacao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    valor VARCHAR(255) NOT NULL,
    unidade VARCHAR(255),
    tipo_parametro VARCHAR(50) NOT NULL,
    solicitacao_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_parametro_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacoes_experiencia (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS solicitacao_fontes_dados (
    solicitacao_id BIGINT NOT NULL,
    fonte_dados_id BIGINT NOT NULL,
    PRIMARY KEY (solicitacao_id, fonte_dados_id),
    CONSTRAINT fk_sfd_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacoes_experiencia (id),
    CONSTRAINT fk_sfd_fonte
        FOREIGN KEY (fonte_dados_id) REFERENCES fontes_dados (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS resultados_geracao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    descricao_resultado TEXT,
    narrativa_imersiva TEXT,
    url_imagem_preview VARCHAR(255),
    url_modelo_3d VARCHAR(255),
    indice_confiabilidade DECIMAL(5, 2),
    observacoes_tecnicas TEXT,
    data_criacao DATETIME(6) NOT NULL,
    solicitacao_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_resultado_solicitacao (solicitacao_id),
    CONSTRAINT fk_resultado_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacoes_experiencia (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS metricas_resultado (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    valor DECIMAL(12, 4) NOT NULL,
    unidade VARCHAR(255),
    descricao VARCHAR(255),
    resultado_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_metrica_resultado
        FOREIGN KEY (resultado_id) REFERENCES resultados_geracao (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS avaliacoes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nota INT NOT NULL,
    comentario TEXT,
    data_avaliacao DATETIME(6) NOT NULL,
    usuario_id BIGINT NOT NULL,
    solicitacao_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_avaliacao_nota CHECK (nota BETWEEN 1 AND 5),
    CONSTRAINT fk_avaliacao_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_avaliacao_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacoes_experiencia (id)
) ENGINE=InnoDB;
