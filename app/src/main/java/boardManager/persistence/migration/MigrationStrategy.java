package boardManager.persistence.migration;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MigrationStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MigrationStrategy.class);

    private final Connection connection;

    public void executeMigration() {
        try {
            var jdbcConnection = new JdbcConnection(connection);
            var liquibase = new Liquibase("db/changelog/db.changelog-master.yml",
                                          new ClassLoaderResourceAccessor(),
                                          jdbcConnection);

            liquibase.update("");
            logger.info("Database migration completed successfully.");
        } catch ( LiquibaseException e) {
            logger.error("Error during database migration", e);
        }
    }
}
