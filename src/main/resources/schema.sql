-- =============================
--  TABELA DE LOG
-- =============================

CREATE TABLE IF NOT EXISTS log_deletes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tabela VARCHAR(255),
    registro_id VARCHAR(255),
    usuario VARCHAR(255),
    data_exclusao DATETIME
);