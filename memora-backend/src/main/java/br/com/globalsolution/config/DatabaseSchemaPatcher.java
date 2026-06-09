package br.com.globalsolution.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class DatabaseSchemaPatcher implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSchemaPatcher.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaPatcher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        ensureVarcharColumn("solicitacoes_experiencia", "categoria_experiencia", 50);
        ensureVarcharColumn("solicitacoes_experiencia", "tipo_temporal", 50);
        ensureVarcharColumn("solicitacoes_experiencia", "nivel_detalhamento", 50);
        ensureVarcharColumn("solicitacoes_experiencia", "status", 50);
        ensureVarcharColumn("fontes_dados", "tipo_fonte", 50);
        ensureAnoReferenciaColumn();
    }

    private void ensureVarcharColumn(String table, String column, int length) {
        try {
            String currentType = jdbcTemplate.queryForObject(
                    """
                            SELECT COLUMN_TYPE
                            FROM INFORMATION_SCHEMA.COLUMNS
                            WHERE TABLE_SCHEMA = DATABASE()
                              AND TABLE_NAME = ?
                              AND COLUMN_NAME = ?
                            """,
                    String.class,
                    table,
                    column);

            if (currentType != null && !currentType.toLowerCase().startsWith("varchar")) {
                String sql = String.format(
                        "ALTER TABLE %s MODIFY COLUMN %s VARCHAR(%d) NOT NULL",
                        table,
                        column,
                        length);
                jdbcTemplate.execute(sql);
                log.info("Coluna {}.{} convertida de {} para VARCHAR({}).", table, column, currentType, length);
            }
        } catch (Exception ex) {
            log.warn("Não foi possível ajustar {}.{}: {}", table, column, ex.getMessage());
        }
    }

    private void ensureAnoReferenciaColumn() {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    """
                            SELECT COUNT(*)
                            FROM INFORMATION_SCHEMA.COLUMNS
                            WHERE TABLE_SCHEMA = DATABASE()
                              AND TABLE_NAME = 'metricas_resultado'
                              AND COLUMN_NAME = 'ano_referencia'
                            """,
                    Integer.class);

            if (count != null && count == 0) {
                jdbcTemplate.execute(
                        "ALTER TABLE metricas_resultado ADD COLUMN ano_referencia INT NULL");
                log.info("Coluna metricas_resultado.ano_referencia criada.");
            }
        } catch (Exception ex) {
            log.warn("Não foi possível garantir metricas_resultado.ano_referencia: {}", ex.getMessage());
        }
    }
}
