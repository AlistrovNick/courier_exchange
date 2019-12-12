package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.UserDao;
import by.epam.courierexchange.entity.RoleStatusType;
import by.epam.courierexchange.entity.RoleType;
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

public enum UserDaoImp implements UserDao {
    INSTANCE;

    private static Logger logger = LogManager.getLogger();
    private final static String SQL_SELECT_BY_ID = "SELECT email, first_name, last_name, user_role FROM users WHERE user_id=?;";
    private final static String SQL_SELECT_BY_EMAIL = "SELECT user_id, email, first_name, last_name, user_role, user_status FROM users WHERE email=?;";
    private final static String SQL_INSERT_INTO_USERS = "INSERT INTO users (email, first_name, last_name, user_password, user_role) VALUE (?, ?, ?, ?, ?);";
    private final static String SQL_SELECT_BY_ROLE = "SELECT user_id, email, first_name, last_name, user_role, user_status FROM users WHERE user_role=?;";
    private final static String SQL_SELECT_PASSWORD_BY_ID = "SELECT user_password FROM users WHERE user_id=?;";
    private final static String SQL_UPDATE_FIRST_NAME = "UPDATE users SET first_name=? WHERE user_id=?";
    private final static String SQL_UPDATE_LAST_NAME = "UPDATE users SET last_name=? WHERE user_id=?";
    private final static String SQL_UPDATE_PASSWORD = "UPDATE users SET user_password=? WHERE user_id=?";
    private Connection connection = null;

    @Override
    public List<User> findById(int id) throws DaoException {
        List<User> userList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(id);
                user.setEmail(resultSet.getString(EMAIL));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setRole(RoleType.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("findById", e);
            throw new DaoException("findById", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }

        return userList;
    }

    @Override
    public List<User> findByEmail(String email) throws DaoException {
        List<User> userList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            preparedStatement.setString(1, email.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setEmail(resultSet.getString(EMAIL));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setRole(RoleType.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
                user.setStatus(RoleStatusType.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("findByEmail", e);
            throw new DaoException("findByEmail", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return userList;
    }

    @Override
    public List<User> findByRole(RoleType role) throws DaoException {
        List<User> userList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ROLE)) {
            preparedStatement.setString(1, role.getRole());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(USER_ID));
                user.setEmail(resultSet.getString(EMAIL));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setRole(RoleType.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
                user.setStatus(RoleStatusType.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("findByRole", e);
            throw new DaoException("findByRole", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return userList;
    }

    @Override
    public String findPasswordById(int id) throws DaoException {
        String password = null;
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(USER_PASSWORD);
            }
        } catch (SQLException e) {
            logger.error("findPasswordById", e);
            throw new DaoException("findPasswordById", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return password;
    }

    @Override
    public void insert(User user, String password) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_USERS)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, user.getRole().getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insert", e);
            throw new DaoException("insert", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void changeFirstName(User user, String firstName) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_FIRST_NAME)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("changeFirstName", e);
            throw new DaoException("changeFirstName", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void changeLastName(User user, String lastName) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LAST_NAME)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("changeLastName", e);
            throw new DaoException("changeLastName", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void changePassword(User user, String password) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("changePassword", e);
            throw new DaoException("changePassword", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
