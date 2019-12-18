package by.epam.courierexchange.service.impl;

import by.epam.courierexchange.dao.impl.RatingCourierDaoImpl;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.RatingCourierService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class RatingCourierServiceImpl implements RatingCourierService {
    private static Logger logger = LogManager.getLogger();

    public void insert(int dealId, String declarer, int courierId, int rating) throws ServiceException {
        try {
            RatingCourierDaoImpl.INSTANCE.insert(dealId, declarer, courierId, rating);
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public void update(int dealId, String declarer, int rating) throws ServiceException {
        try {
            RatingCourierDaoImpl.INSTANCE.update(dealId, declarer, rating);
        } catch (DaoException e) {
            logger.error("update", e);
            throw new ServiceException("update", e);
        }
    }

    public Map<Integer, Integer> findByDeclarer(String declarer) throws ServiceException {
        Map<Integer, Integer> findMap;
        try {
            findMap = RatingCourierDaoImpl.INSTANCE.findByDeclarer(declarer);
        } catch (DaoException e) {
            logger.error("findByDeclarer", e);
            throw new ServiceException("findByDeclarer", e);
        }
        return findMap;
    }

    @Override
    public Map<Integer, Double> findAllCourier() throws ServiceException {
        Map<Integer, Double> findMap;
        try {
            findMap = RatingCourierDaoImpl.INSTANCE.findAllCourier();
        } catch (DaoException e) {
            logger.error("findAllCourier", e);
            throw new ServiceException("findAllCourier", e);
        }
        return findMap;
    }

    @Override
    public double findByCourierId(int id) throws ServiceException {
        double rating = 0;
        try {
            rating = RatingCourierDaoImpl.INSTANCE.findByCourierId(id);
        } catch (DaoException e) {
            logger.error("findByCourierId", e);
            throw new ServiceException("findByCourierId", e);
        }
        return rating;
    }
}
