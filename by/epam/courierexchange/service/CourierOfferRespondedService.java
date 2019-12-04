package by.epam.courierexchange.service;

import by.epam.courierexchange.dao.impl.CourierOfferRespondedDaoImpl;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CourierOfferRespondedService {
    private static Logger logger = LogManager.getLogger();

    public void insert(int offerId, User client, String comment) throws ServiceException {
        try {
            CourierOfferRespondedDaoImpl.INSTANCE.insert(offerId, client, comment);
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public List<ClientOffer> findByCourier(User courier, OfferStatusType status) throws ServiceException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        try {
            clientOfferList.addAll(CourierOfferRespondedDaoImpl.INSTANCE.findByCourierAndStatus(courier, status));
        } catch (DaoException e) {
            logger.error("findByCourier", e);
            throw new ServiceException("findByCourier", e);
        }
        return clientOfferList;
    }

    public List<ClientOffer> findByClient(User client, OfferStatusType status) throws ServiceException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        try {
            clientOfferList.addAll(CourierOfferRespondedDaoImpl.INSTANCE.findByClient(client, status));
        } catch (DaoException e) {
            logger.error("findByClient", e);
            throw new ServiceException("findByClient", e);
        }
        return clientOfferList;
    }

    public void changeStatus(int dealId, OfferStatusType newStatus) throws ServiceException {
        try {
            CourierOfferRespondedDaoImpl.INSTANCE.changeStatus(dealId, newStatus);
        } catch (DaoException e) {
            logger.error("changeStatus", e);
            throw new ServiceException("changeStatus", e);
        }
    }

    public void delete(int dealId) throws ServiceException {
        try {
            CourierOfferRespondedDaoImpl.INSTANCE.delete(dealId);
        } catch (DaoException e) {
            logger.error("delete", e);
            throw new ServiceException("delete", e);
        }
    }
}
