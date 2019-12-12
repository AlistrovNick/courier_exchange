package by.epam.courierexchange.service;

import by.epam.courierexchange.dao.impl.RatingCourierDaoImpl;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RatingCourierService {
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
}
