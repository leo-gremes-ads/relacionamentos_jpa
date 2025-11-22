package mesmo.eu.relacionamento.dto.relatorios;

import java.time.LocalDate;

public record Exclusoes
(
    String tabela,
    String registro_id,
    String usuario,
    LocalDate data_exclusao
) {}
