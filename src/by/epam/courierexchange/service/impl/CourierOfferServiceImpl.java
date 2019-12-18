package by.epam.courierexchange.service.impl;

import by.epam.courierexchange.dao.impl.CourierOfferDaoImpl;
import by.epam.courierexchange.dao.impl.GoodsDaoImpl;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.CourierOfferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourierOfferServiceImpl implements CourierOfferService {
    private static Logger logger = LogManager.getLogger();

    public List<CourierOffer> findByStatus(OfferStatusType status) throws ServiceException {
        List<CourierOffer> findOfferList;
        try {
            findOfferList = CourierOfferDaoImpl.INSTANCE.findByStatus(status);
            putGoods(findOfferList);
        } catch (DaoException e) {
            logger.error("findByStatus", e);
            throw new ServiceException("findByStatus", e);
        }
        return findOfferList;
    }

    public List<CourierOffer> findByUserAndStatus(User user, OfferStatusType status) throws ServiceException {
        List<CourierOffer> findOfferList;
        try {
            findOfferList = CourierOfferDaoImpl.INSTANCE.findByUserAndStatus(user, status);
            putGoods(findOfferList);
        } catch (DaoException e) {
            logger.error("findByUserAndStatus", e);
            throw new ServiceException("findByUserAndStatus", e);
        }
        return findOfferList;
    }

    public void insert(CourierOffer offer) throws ServiceException {
        try {
            int idOffer = CourierOfferDaoImpl.INSTANCE.insert(offer);
            for (String currentGoods : offer.getGoods()) {
                GoodsDaoImpl.INSTANCE.insertIntoCourierGoods(idOffer, currentGoods);
            }
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public void changeStatus(int offerId, OfferStatusType newStatus) throws ServiceException {
        try {
            CourierOfferDaoImpl.INSTANCE.changeStatus(offerId, newStatus);
        } catch (DaoException e) {
            logger.error("changeStatus", e);
            throw new ServiceException("changeStatus", e);
        }
    }

    private void putGoods(List<CourierOffer> list) throws DaoException {
        for (CourierOffer currentOffer : list) {
            currentOffer.setGoods(GoodsDaoImpl.INSTANCE.findCourierGoodsByOfferId(currentOffer.getId()));
        }
    }
}
