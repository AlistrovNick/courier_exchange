package by.epam.courierexchange.service;

import by.epam.courierexchange.dao.impl.ClientOfferRespondedDaoImpl;
import by.epam.courierexchange.dao.impl.GoodsDaoImp;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ClientOfferRespondedService {
    private static Logger logger = LogManager.getLogger();

    public List<Integer> findOfferId(User courier, OfferStatusType status) throws ServiceException {
        List<Integer> clientOfferList = new ArrayList<>();
        try {
            clientOfferList.addAll(ClientOfferRespondedDaoImpl.INSTANCE.findOfferId(courier, status));
        } catch (DaoException e) {
            logger.error("findOfferId", e);
            throw new ServiceException("findOfferId", e);
        }
        return clientOfferList;
    }

    public List<ClientOffer> findByCourier(User courier, OfferStatusType status) throws ServiceException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        try {
            clientOfferList.addAll(ClientOfferRespondedDaoImpl.INSTANCE.findByCourierAndStatus(courier, status));
            for (int i = 0; i < clientOfferList.size(); i++) {
                clientOfferList.get(i).setGoods(GoodsDaoImp.INSTANCE.findClientGoodsByOfferId(clientOfferList.get(i).getId()));
            }
        } catch (DaoException e) {
            logger.error("findByCourier", e);
            throw new ServiceException("findByCourier", e);
        }
        return clientOfferList;
    }

    public List<ClientOffer> findByClient(User client, OfferStatusType status) throws ServiceException {
        List<ClientOffer> clientOfferList = new ArrayList<>();
        try {
            clientOfferList.addAll(ClientOfferRespondedDaoImpl.INSTANCE.findByClient(client, status));
            for (int i = 0; i < clientOfferList.size(); i++) {
                clientOfferList.get(i).setGoods(GoodsDaoImp.INSTANCE.findClientGoodsByOfferId(clientOfferList.get(i).getId()));
            }
        } catch (DaoException e) {
            logger.error("findByClient", e);
            throw new ServiceException("findByClient", e);
        }
        return clientOfferList;
    }

    public void insert(int offerId, User courier) throws ServiceException {
        try {
            ClientOfferRespondedDaoImpl.INSTANCE.insert(offerId, courier);
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public void deleteByOfferId(int offerId) throws ServiceException {
        try {
            ClientOfferRespondedDaoImpl.INSTANCE.deleteByOfferId(offerId);
        } catch (DaoException e) {
            logger.error("delete", e);
            throw new ServiceException("delete", e);
        }
    }

    public void deleteByDealId(int dealId) throws ServiceException {
        try {
            ClientOfferRespondedDaoImpl.INSTANCE.deleteByDealId(dealId);
        } catch (DaoException e) {
            logger.error("delete", e);
            throw new ServiceException("delete", e);
        }
    }

    public void changeStatus(int dealId, OfferStatusType newStatus) throws ServiceException {
        try {
            ClientOfferRespondedDaoImpl.INSTANCE.changeStatus(dealId, newStatus);
        } catch (DaoException e) {
            logger.error("changeStatus", e);
            throw new ServiceException("changeStatus", e);
        }
    }
}
