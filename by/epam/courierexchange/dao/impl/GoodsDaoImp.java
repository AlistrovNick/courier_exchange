package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.GoodsDao;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public enum GoodsDaoImp implements GoodsDao {
    INSTANCE;

    private static Logger logger = LogManager.getLogger();
    private final static String SQL_SELECT_BY_TYPE = "SELECT id FROM goods WHERE type=?;";
    private final static String SQL_SELECT_BY_ID = "SELECT type FROM goods WHERE id=?;";
    private final static String SQL_SELECT_COURIER_GOODS_BY_OFFER_ID = "SELECT type FROM goods INNER JOIN courier_goods ON goods.id=courier_goods.goods_id WHERE courier_goods.offer_id=?;";
    private final static String SQL_SELECT_CLIENT_GOODS_BY_OFFER_ID = "SELECT type FROM goods INNER JOIN client_goods ON goods.id=client_goods.goods_id WHERE client_goods.offer_id=?;";
    private final static String SQL_SELECT_ALL = "select type from goods where id>'0';";
    private final static String SQL_INSERT_INTO_COURIER_GOODS = "INSERT INTO courier_goods (offer_id, goods_id) SELECT ?, goods.id FROM goods WHERE goods.type=?;";
    private final static String SQL_INSERT_INTO_CLIENT_GOODS = "INSERT INTO client_goods (offer_id, goods_id) VALUES (?, ?);";
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
            logger.error("find", e);
            throw new DaoException("find", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return type;
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
    public List<String> findCourierGoodsByOfferId(int offerId) throws DaoException {
        List<String> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURIER_GOODS_BY_OFFER_ID)) {
            preparedStatement.setInt(1, offerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                findList.add(resultSet.getString(ColumnName.TYPE));
            }
        } catch (SQLException e) {
            logger.error("findCourierGoodsByOfferId", e);
            throw new DaoException("findCourierGoodsByOfferId", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return findList;
    }

    @Override
    public List<String> findClientGoodsByOfferId(int offerId) throws DaoException {
        List<String> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_CLIENT_GOODS_BY_OFFER_ID)) {
            preparedStatement.setInt(1, offerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                findList.add(resultSet.getString(ColumnName.TYPE));
            }
        } catch (SQLException e) {
            logger.error("findByOfferId", e);
            throw new DaoException("findByOfferId", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
        return findList;
    }

    @Override
    public List<String> findAll() throws DaoException {
        List<String> findList = new ArrayList<>();
        connection = ConnectionPool.INSTANCE.getConnection();
        try (Statement statement = connection.createStatement()){
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

    @Override
    public void insertIntoCourierGoods(int idOffer, String goods) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_COURIER_GOODS)) {
            preparedStatement.setInt(1, idOffer);
            preparedStatement.setString(2, goods);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insertIntoGoodsOffer", e);
            throw new DaoException("insertIntoGoodsOffer", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public void insertIntoClientGoods(int offerId, int goodsId) throws DaoException {
        connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_CLIENT_GOODS)) {
            preparedStatement.setInt(1, offerId);
            preparedStatement.setInt(2, goodsId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insertIntoGoodsOffer", e);
            throw new DaoException("insertIntoGoodsOffer", e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
