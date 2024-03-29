package by.epam.courierexchange.service.impl;

import by.epam.courierexchange.dao.impl.ClientOfferDaoImpl;
import by.epam.courierexchange.dao.impl.ClientOfferRespondedDaoImpl;
import by.epam.courierexchange.dao.impl.GoodsDaoImpl;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ClientOfferServiceImpl implements ClientOfferService {
    private static Logger logger = LogManager.getLogger();

    public void insert(ClientOffer clientOffer) throws ServiceException {
        try {
            int offerId = ClientOfferDaoImpl.INSTANCE.insert(clientOffer);
            for (String currentGoods : clientOffer.getGoods()) {
                int goodsId = GoodsDaoImpl.INSTANCE.find(currentGoods);
                GoodsDaoImpl.INSTANCE.insertIntoClientGoods(offerId, goodsId);
            }
        } catch (DaoException e) {
            logger.error("insert", e);
            throw new ServiceException("insert", e);
        }
    }

    public List<ClientOffer> findByUserAndStatus(User user, OfferStatusType status) throws ServiceException {
        List<ClientOffer> findList = new ArrayList<>();
        try {
            findList.addAll(ClientOfferDaoImpl.INSTANCE.findByUserAndStatus(user, status));
            putGoods(findList);
        } catch (DaoException e) {
            logger.error("find", e);
            throw new ServiceException("find", e);
        }
        return findList;
    }

    public List<ClientOffer> findByStatus(OfferStatusType status) throws ServiceException {
        List<ClientOffer> findList = new ArrayList<>();
        try {
            findList.addAll(ClientOfferDaoImpl.INSTANCE.findByStatus(status));
            putGoods(findList);

        } catch (DaoException e) {
            logger.error("find", e);
            throw new ServiceException("find", e);
        }
        return findList;
    }

    public void changeStatus(int offerId, OfferStatusType status) throws ServiceException {
        try {
            ClientOfferDaoImpl.INSTANCE.changeStatus(offerId, status);
        } catch (DaoException e) {
            logger.error("changeStatus", e);
            throw new ServiceException("changeStatus", e);
        }
    }

    public void delete(int offerId) throws ServiceException {
        try {
            ClientOfferRespondedDaoImpl.INSTANCE.deleteByOfferId(offerId);
            ClientOfferDaoImpl.INSTANCE.deleteGoods(offerId);
            ClientOfferDaoImpl.INSTANCE.delete(offerId);
        } catch (DaoException e) {
            logger.error("delete", e);
            throw new ServiceException("delete", e);
        }
    }

    private void putGoods(List<ClientOffer> list) throws DaoException {
        for (ClientOffer currentOffer : list) {
            currentOffer.setGoods(GoodsDaoImpl.INSTANCE.findClientGoodsByOfferId(currentOffer.getId()));
        }
    }
}
