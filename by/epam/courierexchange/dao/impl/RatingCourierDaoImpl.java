package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.RatingCourierDao;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public enum RatingCourierDaoImpl implements RatingCourierDao {
    INSTANCE;

    private final Logger logger = LogManager.getLogger();
    private final static String SQL_INSERT = "INSERT INTO rating (deal_id, declarer, courier_id, rating) VALUES (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE rating SET rating=? WHERE deal_id=? AND declarer=?;";
    private final static String SQL_SELECT_BY_DECLARER = "SELECT deal_id, rating FROM rating WHERE declarer=?;";
    private Connection connection;

    @Override
    public void insert(int dealId, String declarer, int courierId, int rating) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, dealId);
            preparedStatement.setString(2, declarer);
            preparedStatement.setInt(3, courierId);
            preparedStatement.setInt(4, rating);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insert", e);
            throw new DaoException("insert", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void update(int dealId, String declarer, int rating) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, dealId);
            preparedStatement.setString(3, declarer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("update", e);
            throw new DaoException("update", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public Map<Integer, Integer> findByDeclarer(String declarer) throws DaoException {
        Map<Integer, Integer> findMap = new HashMap<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_DECLARER)) {
            preparedStatement.setString(1, declarer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int dealId = resultSet.getInt(ColumnName.DEAL_ID);
                int rating = resultSet.getInt(ColumnName.RATING);
                findMap.put(dealId, rating);
            }
        } catch (SQLException e) {
            logger.error("findByDeclarer", e);
            throw new DaoException("findByDeclarer", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return findMap;
    }
}

