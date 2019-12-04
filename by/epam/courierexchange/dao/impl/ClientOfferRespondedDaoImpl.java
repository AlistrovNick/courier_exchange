package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.ClientOfferRespondedDao;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.dao.ColumnName.*;

public enum ClientOfferRespondedDaoImpl implements ClientOfferRespondedDao {
    INSTANCE;

    private final static String SQL_SELECT_OFFER_ID_BY_COURIER_AND_STATUS = "SELECT offer_id FROM client_offer_responded WHERE courier_id=? AND status=?;";
    private final static String SQL_SELECT_BY_COURIER_AND_STATUS = "SELECT client_offer_responded.id, client_offer_responded.offer_id, users.user_id, users.first_name, users.last_name, users.email, client_offer.comment, client_offer.date, client_offer.start_time, client_offer.end_time FROM client_offer_responded INNER JOIN client_offer ON client_offer_responded.offer_id=client_offer.id INNER JOIN users ON client_offer.user_id=users.user_id WHERE client_offer_responded.courier_id=? AND client_offer_responded.status=?;";
    private final static String SQL_SELECT_BY_CLIENT_AND_STATUS = "SELECT client_offer_responded.offer_id, users.user_id, users.first_name, users.last_name, users.email, client_offer.comment, client_offer.date, client_offer.start_time, client_offer.end_time FROM client_offer_responded INNER JOIN client_offer ON client_offer_responded.offer_id=client_offer.id INNER JOIN users ON client_offer_responded.courier_id=users.user_id WHERE client_offer.user_id=? AND client_offer_responded.status=?;";
    private final static String SQL_INSERT = "INSERT INTO client_offer_responded (offer_id, courier_id) VALUES (?, ?);";
    private final static String SQL_DELETE_BY_DEAL_ID = "DELETE FROM client_offer_responded WHERE id=? ;";
    private final static String SQL_DELETE_BY_ID = "DELETE FROM client_offer_responded WHERE offer_id=? AND status='in_process';";
    private final static String SQL_UPDATE_STATUS = "UPDATE client_offer_responded SET status=? WHERE id=?;";
    private Logger logger = LogManager.getLogger();
    private Connection connection;

    @Override
    public List<Integer> findOfferId(User courier, OfferStatusType status) throws DaoException {
        List<Integer> clientOfferIdList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OFFER_ID_BY_COURIER_AND_STATUS)) {
            preparedStatement.setInt(1, courier.getId());
            preparedStatement.setString(2, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clientOfferIdList.add(resultSet.getInt(OFFER_ID));
            }
        } catch (SQLException e) {
            logger.error("findOfferId", e);
            throw new DaoException("findOfferId", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return clientOfferIdList;
    }

    @Override
    public List<ClientOffer> findByCourierAndStatus(User courier, OfferStatusType status) throws DaoException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_COURIER_AND_STATUS)) {
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
                String date = resultSet.getString(DATE);
                if (date != null) {
                    clientOffer.setDate(LocalDate.parse(date));
                }
                String startTime = resultSet.getString(START_TIME);
                if (startTime != null) {
                    clientOffer.setStartTime(LocalTime.parse(startTime));
                }
                String endTime = resultSet.getString(END_TIME);
                if (endTime != null) {
                    clientOffer.setEndTime(LocalTime.parse(endTime));
                }
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
    public List<ClientOffer> findByClient(User client, OfferStatusType status) throws DaoException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_CLIENT_AND_STATUS)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setString(2, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOffer clientOffer = new ClientOffer();
                clientOffer.setId(resultSet.getInt(OFFER_ID));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setEmail(resultSet.getString(EMAIL));
                clientOffer.setUser(user);
                clientOffer.setComment(resultSet.getString(COMMENT));
                clientOffer.setDate(LocalDate.parse(resultSet.getString(DATE)));
                clientOffer.setStartTime(LocalTime.parse(resultSet.getString(START_TIME)));
                clientOffer.setEndTime(LocalTime.parse(resultSet.getString(END_TIME)));
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
    public void insert(int offerId, User courier) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setInt(1, offerId);
            preparedStatement.setInt(2, courier.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insert", e);
            throw new DaoException("insert", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void deleteByOfferId(int offerId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setInt(1, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("delete", e);
            throw new DaoException("delete", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void deleteByDealId(int dealId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_DEAL_ID)) {
            preparedStatement.setInt(1, dealId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("delete", e);
            throw new DaoException("delete", e);
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
}
