package by.epam.courierexchange.dao;

import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface GoodsDao {
    String find(int id) throws DaoException;
    int find(String type) throws DaoException;
    List<String> findCourierGoodsByOfferId(int offerId) throws DaoException;
    List<String> findClientGoodsByOfferId(int offerId) throws DaoException;
    List<String> findAll() throws DaoException;
    void insertIntoCourierGoods(int idOffer, String goods) throws DaoException;
    void insertIntoClientGoods(int offerId, int goodsId) throws DaoException;
}
