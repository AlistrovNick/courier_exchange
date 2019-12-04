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

public enum TransportDaoImp implements TransportDao {
    INSTANCE;

    private static Logger logger = LogManager.getLogger();
    private final static String SQL_SELECT_BY_TYPE = "SELECT id FROM transport WHERE type=?;";
    private final static String SQL_SELECT_BY_OFFER_ID = "SELECT type FROM transport INNER JOIN transport_offer ON transport.id=transport_offer.transport_id WHERE transport_offer.offer_id=?;";
    private final static String SQL_SELECT_BY_ID = "SELECT type FROM transport WHERE id=?;";
    private final static String SQL_SELECT_ALL = "SELECT type FROM transport WHERE id>'0';";
    private final static String SQL_INSERT_INTO_TRANSPORT_OFFER = "INSERT INTO transport_offer (offer_id, transport_id) VALUES (?, ?);";
    private Connection connection;

    @Override
    public String find(int id) throws DaoException {
        String type = null;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type = resultSet.getString(ColumnName.TYPE);
            }
        } catch (SQLException e) {
            logger.error("find ", e);
            throw new DaoException("find ", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return type;
    }

    @Override
    public String findByOfferId(int offerId) throws DaoException {
        String transport = null;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_OFFER_ID)) {
            preparedStatement.setInt(1, offerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                transport = resultSet.getString(ColumnName.TYPE);
            }
        } catch (SQLException e) {
            logger.error("findByOfferId", e);
            throw new DaoException("findByOfferId", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return transport;
    }

    @Override
    public int find(String type) throws DaoException {
        int id = 0;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_TYPE)){
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(ColumnName.ID);
            }
        } catch (SQLException e) {
            logger.error("find", e);
            throw new DaoException("find", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return id;
    }

    @Override
    public List<String> findAll() throws DaoException {
        List<String> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (Statement statement =connection.createStatement()){
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
