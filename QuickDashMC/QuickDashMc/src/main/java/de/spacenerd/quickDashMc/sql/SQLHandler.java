package de.spacenerd.quickDashMc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import de.spacenerd.quickDashMc.QuickDashMc;

public class SQLHandler {
    private final QuickDashMc instance;
    private HikariDataSource dataSource;

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
        
        dataSource = new HikariDataSource(config);

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prepState = conn.prepareStatement(
                """
                CREATE TABLE IF NOT EXISTS users {
                    username: varchar(255) NOT NULL,
                    token: varchar(255) PRIMARY KEY
                };
                """
            );
            prepState.execute();
            conn.close();
        } catch (SQLException e) {
            // TODO: Handle Exception
        }
    }

    public void addUser(String username, String token) {
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement prepState = conn.prepareStatement(
                """
                INSERT INTO users(username, token) VALUES(?, ?);
                """
            );
            
            prepState.setString(1, username);
            prepState.setString(2, token);
            prepState.execute();
            conn.close();
        } catch (SQLException e) {
            // TODO: Handle Exception
        }
    }

    public void removeUser(String username) {
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement prepState = conn.prepareStatement(
                """
                DELETE FROM users WHERE username = ?;
                """
            );

            prepState.setString(1, username);
            prepState.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            // TODO: Handle Exception
        }
    }

    public String getToken(String username) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prepState = conn.prepareStatement(
                """
                SELECT token FROM users WHERE username = ?;
                """
            );

            prepState.setString(1, username);
            ResultSet resultSet = prepState.executeQuery();
            conn.close();

            if (resultSet.next()) {
                return resultSet.getString("token");
            }
            
            // TODO: not good fix this 
            return "";
        } catch (SQLException e) {
            // TODO: Handle Exception
            return "";
        }
    }

    public String getUsername(String token) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement prepState = conn.prepareStatement(
                """
                SELECT username FROM users WHERE token = ?;
                """
            );

            prepState.setString(1, token);
            ResultSet resultSet = prepState.executeQuery();
            conn.close();

            if (resultSet.next()) {
                return resultSet.getString("username");
            }
            
            // TODO: not good fix this 
            return "";
        } catch (SQLException e) {
            // TODO: Handle Exception
            return "";
        }
    }
}
