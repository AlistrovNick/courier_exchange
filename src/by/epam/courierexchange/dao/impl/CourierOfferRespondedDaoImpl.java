package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.CourierOfferRespondedDao;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.dao.ColumnName.*;

public enum CourierOfferRespondedDaoImpl implements CourierOfferRespondedDao {
    INSTANCE;

    private final static String SQL_INSERT = "INSERT INTO courier_offer_responded (offer_id, client_id, comment) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_RESPONDED_BY_COURIER_AND_STATUS = "SELECT courier_offer_responded.id, courier_offer_responded.offer_id, users.user_id, users.first_name, users.last_name, users.email, courier_offer_responded.comment FROM courier_offer_responded INNER JOIN users ON courier_offer_responded.client_id=users.user_id INNER JOIN courier_offer ON courier_offer_responded.offer_id=courier_offer.id WHERE courier_offer.user_id=? AND courier_offer_responded.status=?;";
    private final static String SQL_SELECT_RESPONDED_BY_CLIENT_AND_STATUS = "SELECT courier_offer_responded.id, courier_offer_responded.offer_id, courier_offer_responded.comment, users.user_id, users.first_name, users.last_name, users.email FROM courier_offer_responded INNER JOIN courier_offer ON courier_offer_responded.offer_id=courier_offer.id INNER JOIN users ON courier_offer.user_id=users.user_id WHERE courier_offer_responded.client_id=? AND courier_offer_responded.status=?;";
    private final static String SQL_UPDATE_STATUS = "UPDATE courier_offer_responded SET status=? WHERE id=?;";
    private final static String SQL_DELETE = "DELETE FROM courier_offer_responded WHERE id=?;";
    private Logger logger = LogManager.getLogger();
    private Connection connection;

    @Override
    public List<ClientOffer> findByCourierAndStatus(User courier, OfferStatusType status) throws DaoException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_RESPONDED_BY_COURIER_AND_STATUS)) {
            preparedStatement.setInt(1, courier.getId());
            preparedStatement.setString(2, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOffer clientOffer = new ClientOffer();
                clientOffer.setDealId(resultSet.getInt(ID));
                clientOffer.setId(resultSet.getInt(OFFER_ID));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setEmail(resultSet.getString(EMAIL));
                clientOffer.setUser(user);
                clientOffer.setComment(resultSet.getString(COMMENT));
                clientOfferList.add(clientOffer);
            }
        } catch (SQLException e) {
            logger.error("findByCourier", e);
            throw new DaoException("findByCourier", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return clientOfferList;
    }

    @Override
    public List<ClientOffer> findByClientAndStatus(User client, OfferStatusType status) throws DaoException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_RESPONDED_BY_CLIENT_AND_STATUS)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setString(2, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOffer clientOffer = new ClientOffer();
                clientOffer.setDealId(resultSet.getInt(ID));
                clientOffer.setId(resultSet.getInt(OFFER_ID));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setEmail(resultSet.getString(EMAIL));
                clientOffer.setUser(user);
                clientOffer.setComment(resultSet.getString(COMMENT));
                clientOfferList.add(clientOffer);
            }
        } catch (SQLException e) {
            logger.error("findByClient", e);
            throw new DaoException("findByClient", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }

        return clientOfferList;
    }

    @Override
    public void insert(int offerId, User client, String comment) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, offerId);
            preparedStatement.setInt(2, client.getId());
            preparedStatement.setString(3, comment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insert", e);
            throw new DaoException("insert", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void changeStatus(int dealId, OfferStatusType newStatus) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            preparedStatement.setString(1, newStatus.getStatus());
            preparedStatement.setInt(2, dealId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("changeStatus", e);
            throw new DaoException("changeStatus", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void delete(int dealId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, dealId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("delete", e);
            throw new DaoException("delete", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
