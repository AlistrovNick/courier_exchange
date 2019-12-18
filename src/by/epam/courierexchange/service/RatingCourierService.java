package by.epam.courierexchange.service;

import by.epam.courierexchange.exception.ServiceException;

import java.util.Map;

public interface RatingCourierService {
    void insert(int dealId, String declarer, int courierId, int rating) throws ServiceException;

    void update(int dealId, String declarer, int rating) throws ServiceException;

    Map<Integer, Integer> findByDeclarer(String declarer) throws ServiceException;

    Map<Integer, Double> findAllCourier() throws ServiceException;

    double findByCourierId(int id) throws ServiceException;
}
