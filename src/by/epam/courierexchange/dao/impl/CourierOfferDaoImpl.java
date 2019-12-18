package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.CourierOfferDao;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.dao.ColumnName.*;

public enum CourierOfferDaoImpl implements CourierOfferDao {
    INSTANCE;

    private static Logger logger = LogManager.getLogger();
    private final static String SQL_SELECT_BY_STATUS = "SELECT courier_offer.id, courier_offer.user_id, users.first_name, users.last_name, transport.type FROM courier_offer INNER JOIN users ON courier_offer.user_id=users.user_id INNER JOIN transport ON courier_offer.transport=transport.id WHERE courier_offer.status=?;";
    private final static String SQL_SELECT_BY_USER_AND_STATUS = "SELECT courier_offer.id, transport.type FROM courier_offer INNER JOIN transport ON courier_offer.transport=transport.id WHERE courier_offer.user_id=? AND courier_offer.status=?;";
    private final static String SQL_UPDATE_OFFER_STATUS = "UPDATE courier_offer SET status=? WHERE id=?;";
    private final static String SQL_INSERT_INTO_COURIER_OFFER = "INSERT INTO courier_offer (user_id, transport) SELECT ?, transport.id FROM transport WHERE transport.type=?;";
    private Connection connection;

    @Override
    public List<CourierOffer> findByStatus(OfferStatusType status) throws DaoException {
        List<CourierOffer> courierOfferList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_STATUS)) {
            preparedStatement.setString(1, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer();
                courierOffer.setId(resultSet.getInt(ID));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                courierOffer.setUser(user);
                courierOffer.setTransport(resultSet.getString(TYPE));
                courierOffer.setStatus(status);
                courierOfferList.add(courierOffer);
            }
        } catch (SQLException e) {
            logger.error("find", e);
            throw new DaoException("find", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return courierOfferList;
    }

    @Override
    public List<CourierOffer> findByUserAndStatus(User user, OfferStatusType status) throws DaoException {
        List<CourierOffer> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USER_AND_STATUS)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer();
                courierOffer.setId(resultSet.getInt(ID));
                courierOffer.setUser(user);
                courierOffer.setTransport(resultSet.getString(TYPE));
                courierOffer.setStatus(status);
                findList.add(courierOffer);
            }
        } catch (SQLException e) {
            logger.error("find", e);
            throw new DaoException("find", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return findList;
    }

    @Override
    public void changeStatus(int offerId, OfferStatusType newStatus) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_OFFER_STATUS)) {
            preparedStatement.setString(1, newStatus.getStatus());
            preparedStatement.setInt(2, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("changeStatus", e);
            throw new DaoException("changeStatus", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public int insert(CourierOffer offer) throws DaoException {
        int offerId = 0;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_COURIER_OFFER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, offer.getUser().getId());
            preparedStatement.setString(2, offer.getTransport());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Insert offer failed");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                offerId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("insert", e);
            throw new DaoException("insert", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return offerId;
    }
}
