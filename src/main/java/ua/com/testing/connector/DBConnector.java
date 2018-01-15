package ua.com.testing.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private static Connection connection;

    private static final Logger LOGGER = LogManager.getLogger(DBConnector.class);

    public static Connection getConnection() {
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/testing");
            connection = dataSource.getConnection();

        } catch (NamingException | SQLException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return connection;
    }
}