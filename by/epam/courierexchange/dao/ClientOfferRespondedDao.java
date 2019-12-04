package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface ClientOfferRespondedDao {
    List<Integer> findOfferId(User courier, OfferStatusType status) throws DaoException;
    List<ClientOffer> findByCourierAndStatus(User courier, OfferStatusType status) throws DaoException;
    List<ClientOffer> findByClient(User client, OfferStatusType status) throws DaoException;
    void insert(int offerId, User courier) throws DaoException;
    void deleteByOfferId(int offerId) throws DaoException;
    void deleteByDealId(int dealId) throws DaoException;
    void changeStatus(int dealId, OfferStatusType newStatus) throws DaoException;
}
