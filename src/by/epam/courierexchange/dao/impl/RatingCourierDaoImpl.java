package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.RatingCourierDao;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public enum RatingCourierDaoImpl implements RatingCourierDao {
    INSTANCE;

    private final Logger logger = LogManager.getLogger();
    private final static String SQL_INSERT = "INSERT INTO rating (deal_id, declarer, courier_id, rating) VALUES (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE rating SET rating=? WHERE deal_id=? AND declarer=?;";
    private final static String SQL_SELECT_BY_DECLARER = "SELECT deal_id, rating FROM rating WHERE declarer=?;";
    private final static String SQL_SELECT_ALL_COURIER = "SELECT courier_id, AVG(rating) AS rating FROM rating WHERE rating>'0' GROUP BY courier_id;";
    private final static String SQL_SELECT_BY_COURIER_ID = "SELECT AVG(rating) AS rating FROM rating WHERE courier_id=? AND rating>'0';";
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

    @Override
    public Map<Integer, Double> findAllCourier() throws DaoException {
        Map<Integer, Double> findMap = new HashMap<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COURIER);
            while (resultSet.next()) {
                findMap.put(resultSet.getInt(ColumnName.COURIER_ID), resultSet.getDouble(ColumnName.RATING));
            }
        } catch (SQLException e) {
            logger.error("findAllCourier", e);
            throw new DaoException("findAllCourier", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return findMap;
    }

    @Override
    public double findByCourierId(int id) throws DaoException {
        double rating = 0;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_COURIER_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rating = resultSet.getDouble(ColumnName.RATING);
            }
        } catch (SQLException e) {
            logger.error("findByCourierId", e);
            throw new DaoException("findByCourierId", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return rating;
    }
}

