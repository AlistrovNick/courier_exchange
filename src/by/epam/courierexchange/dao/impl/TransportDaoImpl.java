package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.TransportDao;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum TransportDaoImpl implements TransportDao {
    INSTANCE;

    private static Logger logger = LogManager.getLogger();
    private final static String SQL_SELECT_ALL = "SELECT type FROM transport WHERE id>'0';";
    private Connection connection;

    @Override
    public List<String> findAll() throws DaoException {
        List<String> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                findList.add(resultSet.getString(ColumnName.TYPE));
            }
        } catch (SQLException e) {
            logger.error("findAll", e);
            throw new DaoException("findAll", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return findList;
    }
}
