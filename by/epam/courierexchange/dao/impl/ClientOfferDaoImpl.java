package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.ClientOfferDao;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.dao.ColumnName.*;

public enum ClientOfferDaoImpl implements ClientOfferDao {
    INSTANCE;

    private Logger logger = LogManager.getLogger();
    private final static String SQL_INSERT = "INSERT INTO client_offer (user_id, comment, date, start_time, end_time) VALUES (?, ?, ?, ?, ?);";
    private final static String SQL_SELECT_BY_USER_AND_STATUS = "SELECT id, comment, date, start_time, end_time FROM client_offer WHERE user_id=? AND status=?;";
    private final static String SQL_SELECT_BY_STATUS = "SELECT id, client_offer.user_id, users.first_name, users.last_name, comment, date, start_time, end_time FROM client_offer INNER JOIN users ON users.user_id=client_offer.user_id WHERE status=?;";
    private final static String SQL_SELECT_CLIENT_OFFER_BY_DEAL_ID = "SELECT client_offer.id, users.user_id," +
            " users.first_name, users.last_name, client_offer.comment, client_offer.start_term, client_offer.start_time," +
            " client_offer.end_time, payment FROM client_offer INNER JOIN users ON client_offer.user_id=users.user_id " +
            "WHERE client_offer.id IN (SELECT client_offer FROM deal_client_offer WHERE deal=?);";
    private final static String SQL_SELECT_USER_BY_OFFER_ID = "SELECT users.user_id, users.first_name, users.last_name, users.user_role FROM users INNER JOIN client_offer ON users.user_id=client_offer.user_id WHERE client_offer.id=?;";
    private final static String SQL_UPDATE_STATUS = "UPDATE client_offer SET status=? WHERE id=?;";
    private final static String SQL_DELETE = "DELETE FROM client_offer WHERE id=?;";
    private final static String SQL_DELETE_DEAL_CLIENT_OFFER = "DELETE FROM deal_client_offer WHERE client_offer=?;";
    private final static String SQL_DELETE_GOODS_BY_OFFER_ID = "DELETE FROM client_goods WHERE offer_id=?;";
    private Connection connection;

    @Override
    public int insert(ClientOffer clientOffer) throws DaoException {
        int idClientOffer = 0;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, clientOffer.getUser().getId());
            preparedStatement.setString(2, clientOffer.getComment());
            preparedStatement.setObject(3, clientOffer.getDate());
            preparedStatement.setObject(4, clientOffer.getStartTime());
            preparedStatement.setObject(5, clientOffer.getEndTime());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("failed insert");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idClientOffer = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("insert", e);
            throw new DaoException("insert", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return idClientOffer;
    }

    @Override
    public List<ClientOffer> find(User user, OfferStatusType status) throws DaoException {
        List<ClientOffer> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USER_AND_STATUS)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOffer clientOffer = new ClientOffer();
                clientOffer.setUser(user);
                clientOffer.setStatus(status);
                clientOffer.setId(resultSet.getInt(ID));
                clientOffer.setComment(resultSet.getString(COMMENT));
                String date = resultSet.getString(DATE);
                if (date == null) {
                    clientOffer.setDate(null);
                } else {
                    clientOffer.setDate(LocalDate.parse(date));
                }
                String startTime = resultSet.getString(START_TIME);
                if (startTime == null) {
                    clientOffer.setStartTime(null);
                } else {
                    clientOffer.setStartTime(LocalTime.parse(startTime));
                }
                String endTime = resultSet.getString(END_TIME);
                if (endTime == null) {
                    clientOffer.setEndTime(null);
                } else {
                    clientOffer.setEndTime(LocalTime.parse(endTime));
                }
                findList.add(clientOffer);
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
    public List<ClientOffer> find(OfferStatusType status) throws DaoException {
        List<ClientOffer> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_STATUS)) {
            preparedStatement.setString(1, status.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOffer clientOffer = new ClientOffer();
                clientOffer.setId(resultSet.getInt(ID));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                clientOffer.setUser(user);
                clientOffer.setComment(resultSet.getString(COMMENT));
                String date = resultSet.getString(DATE);
                if (date == null) {
                    clientOffer.setDate(null);
                } else {
                    clientOffer.setDate(LocalDate.parse(date));
                }
                String startTime = resultSet.getString(START_TIME);
                if (startTime == null) {
                    clientOffer.setStartTime(null);
                } else {
                    clientOffer.setStartTime(LocalTime.parse(startTime));
                }
                String endTime = resultSet.getString(END_TIME);
                if (endTime == null) {
                    clientOffer.setEndTime(null);
                } else {
                    clientOffer.setEndTime(LocalTime.parse(endTime));
                }
                clientOffer.setStatus(status);
                findList.add(clientOffer);
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
    public List<ClientOffer> findByDealId(int dealId) throws DaoException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_CLIENT_OFFER_BY_DEAL_ID)) {
            preparedStatement.setInt(1, dealId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOffer clientOffer = new ClientOffer();
                clientOffer.setId(resultSet.getInt(ID));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                clientOffer.setUser(user);
                clientOffer.setComment(resultSet.getString(COMMENT));
                clientOffer.setDate(LocalDate.parse(resultSet.getString(START_TERM)));
                clientOffer.setStartTime(LocalTime.parse(resultSet.getString(START_TIME)));
                clientOffer.setEndTime(LocalTime.parse(resultSet.getString(END_TIME)));
                clientOfferList.add(clientOffer);
            }
        } catch (SQLException e) {
            logger.error("findByDealId", e);
            throw new DaoException("findByDealId", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return clientOfferList;
    }

    @Override
    public List<User> find(int offerId) throws DaoException {
        List<User> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_OFFER_ID)) {
            preparedStatement.setInt(1, offerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(FIRST_NAME);
                user.setLastName(LAST_NAME);
                user.setRole(RoleType.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
                findList.add(user);
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
    public void changeStatus(int offerId, OfferStatusType status) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            preparedStatement.setString(1, status.getStatus());
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
    public void delete(int offerId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
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
    public void deleteDealClientOffer(int offerId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DEAL_CLIENT_OFFER)) {
            preparedStatement.setInt(1, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("deleteDealClientOffer", e);
            throw new DaoException("deleteDealClientOffer", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void deleteGoods(int offerId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_GOODS_BY_OFFER_ID)) {
            preparedStatement.setInt(1, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("deleteGoods", e);
            throw new DaoException("deleteGoods", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
