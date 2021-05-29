package jdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jdbc.util.PropertiesConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private HikariDataSource ds;

    public DatabaseConnectionManager(PropertiesConfig propertiesConfig) {
        initDataSource(propertiesConfig);
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
    }

    private void initDataSource(PropertiesConfig propertiesConfig) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:postgresql://%s/%s", propertiesConfig.getProperty("host"),
                propertiesConfig.getProperty("database.name")));
        config.setUsername(propertiesConfig.getProperty("username"));
        config.setPassword(propertiesConfig.getProperty("password"));
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(10_000);
        config.setConnectionTimeout(10_000);
        ds = new HikariDataSource(config);
    }
}
