package by.epam.courierexchange.dao;

import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface TransportDao {
    String find(int id) throws DaoException;
    int find(String type) throws DaoException;
    String findByOfferId(int offerId) throws DaoException;
    List<String> findAll() throws DaoException;
}
