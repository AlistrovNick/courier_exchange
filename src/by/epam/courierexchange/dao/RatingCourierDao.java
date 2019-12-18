package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.Map;

public interface RatingCourierDao {
    void insert(int dealId, String declarer, int courierId, int rating) throws DaoException;

    void update(int dealId, String declarer, int rating) throws DaoException;

    Map<Integer, Integer> findByDeclarer(String declarer) throws DaoException;

    Map<Integer, Double> findAllCourier() throws DaoException;

    double findByCourierId(int id) throws DaoException;
}
