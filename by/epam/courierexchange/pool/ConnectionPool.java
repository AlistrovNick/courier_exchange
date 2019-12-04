package by.epam.courierexchange.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private Logger logger = LogManager.getLogger();
    private final static int POOL_SIZE = 10;
    private BlockingQueue<ProxyConnection> freeConnection;
    private Queue<ProxyConnection> givenConnection;

    ConnectionPool() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.error("DB exception ", e);
            throw new RuntimeException("DB exception ", e);
        }
        freeConnection = new LinkedBlockingQueue<>(POOL_SIZE);
        givenConnection = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnection.add(new ProxyConnection(DriverManager.getConnection(Property.URL, Property.USER, Property.PASS)));
            } catch (SQLException e) {
                logger.fatal("DB exception ", e);
                throw new RuntimeException("DB exception ", e);
            }
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenConnection.offer(connection);
        } catch (InterruptedException e) {
            logger.error("DB exception ", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                givenConnection.remove(connection);
                freeConnection.offer((ProxyConnection) connection);
            } catch (SQLException e) {
                logger.error("DB exception ", e);
                throw new RuntimeException("DB exception ", e);
            }
        } else {
            throw new IllegalArgumentException("Wrong connection");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (SQLException e) {
                logger.error("DB exception ", e);
                throw new RuntimeException("DB exception", e);
            } catch (InterruptedException e) {
                logger.error("DB exception ", e);
                throw new RuntimeException("DB exception", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("DB exception ", e);
                throw new RuntimeException("DB exception", e);
            }
        });
    }
}
