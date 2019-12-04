package by.epam.courierexchange.service;

import by.epam.courierexchange.dao.impl.*;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourierOfferService {
    private static Logger logger = LogManager.getLogger();

    public List<CourierOffer> find(int  offerId) throws ServiceException {
        List<CourierOffer> findOfferList;
        try {
            findOfferList = CourierOfferDaoImp.INSTANCE.find(offerId);
            putGoods(findOfferList);
        } catch (DaoException e) {
            logger.error("find", e);
            throw new ServiceException("find", e);
        }
        return findOfferList;
    }

    public List<CourierOffer> find(OfferStatusType status) throws ServiceException {
        List<CourierOffer> findOfferList;
        try {
            findOfferList = CourierOfferDaoImp.INSTANCE.find(status);
            putGoods(findOfferList);
        } catch (DaoException e) {
            logger.error("find", e);
            throw new ServiceException("find", e);
        }
        return findOfferList;
    }

    public List<CourierOffer> find(User user, OfferStatusType status) throws ServiceException {
        List<CourierOffer> findOfferList;
        try {
            findOfferList = CourierOfferDaoImp.INSTANCE.find(user, status);
            putGoods(findOfferList);
        } catch (DaoException e) {
            logger.error("find", e);
            throw new ServiceException("find", e);
        }
        return findOfferList;
    }

    public void insert(CourierOffer offer) throws ServiceException {
        try {
            int idOffer = CourierOfferDaoImp.INSTANCE.insert(offer);
            for (String currentGoods : offer.getGoods()) {
                GoodsDaoImp.INSTANCE.insertIntoCourierGoods(idOffer, currentGoods);
            }
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public void changeStatus(int offerId, OfferStatusType newStatus) throws ServiceException {
        try {
            CourierOfferDaoImp.INSTANCE.changeStatus(offerId, newStatus);
        } catch (DaoException e) {
            logger.error("changeStatus", e);
            throw new ServiceException("changeStatus", e);
        }
    }

    private void putGoods(List<CourierOffer> list) throws DaoException {
        for (CourierOffer currentOffer : list) {
            currentOffer.setGoods(GoodsDaoImp.INSTANCE.findCourierGoodsByOfferId(currentOffer.getId()));
        }
    }
}
