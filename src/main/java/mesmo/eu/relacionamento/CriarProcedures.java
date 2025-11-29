package mesmo.eu.relacionamento;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CriarProcedures
{
    @Bean
    CommandLineRunner carregarProcedures(JdbcTemplate jdbc)
    {
        return args -> {
            try {
                String sql = Files.readString(
                    Path.of("src/main/resources/procedures.sql"),
                    StandardCharsets.UTF_8
                );
                for (String stmt : sql.split("&&")) {
                    String trimmed = stmt.trim();
                    if (!trimmed.isEmpty())
                        jdbc.execute(trimmed);
                }
                log.info("Procedures Carregadas");
            } catch (Exception e) {
                log.error("Erro ao carregar procedures", e);
            }
        };
    }

}
