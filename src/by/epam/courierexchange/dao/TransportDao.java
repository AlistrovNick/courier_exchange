package by.epam.courierexchange.dao;

import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface TransportDao {
    List<String> findAll() throws DaoException;
}
