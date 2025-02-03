package de.spacenerd.quickDashMc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import de.spacenerd.quickDashMc.QuickDashMc;

public class SQLHandler {
    private final QuickDashMc instance;

    public SQLHandler(QuickDashMc instance) {
        this.instance = instance;
    }

    public void initDB() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/quickDash");
        config.setUsername(instance.getConfig().getString("db.username"));
        config.setPassword(instance.getConfig().getString("db.password"));
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setMaximumPoolSize(10);
        
        HikariDataSource dataSource = new HikariDataSource(config);

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prepStat = conn.prepareStatement(
                """
                CREATE TABLE IF NOT EXISTS users {
                    username: varchar(255) NOT NULL,
                    token: varchar(255) PRIMARY KEY
                }
                """
            );
        } catch (Exception e) {
            // Handle Exception
        }
    }
}
