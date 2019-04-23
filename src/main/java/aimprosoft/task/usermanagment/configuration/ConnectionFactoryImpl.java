package aimprosoft.task.usermanagment.configuration;

import aimprosoft.task.usermanagment.exception.DataBaseException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactoryImpl implements ConnectionFactory {
   /* private String driver;
    private String url;
    private String user;
    private String password;
    private int initSize;
    private int maxTotal;*/

    private BasicDataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactoryImpl.class);


   /* public ConnectionFactoryImpl(Properties properties) {
        driver = properties.getProperty("db.driver");
        url = properties.getProperty("db.url");
        user = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
        initSize = Integer.parseInt(properties.getProperty("db.pool.initSize"));
        maxTotal = Integer.parseInt(properties.getProperty("db.pool.maxSize"));
    }

    public ConnectionFactoryImpl(String driver, String url, String user, String password, int initSize, int maxTotal) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        this.initSize = initSize;
        this.maxTotal = maxTotal;
    }*/

    public ConnectionFactoryImpl(Properties properties) {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("db.driver"));
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUsername(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
        LOGGER.info("DataSource was successful generated !");
    }

    @Override
    public Connection createConnection() throws DataBaseException, SQLException {
        return dataSource.getConnection();
        /*try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }*/
    }

    @Override
    public void closeQuietly(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error: Connection wasn't close!", e);
        }
    }

    @Override
    public void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Error: Failed to cancel!", e);
        }
    }
}
