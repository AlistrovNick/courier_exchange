package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface ClientOfferDao {
    int insert(ClientOffer clientOffer) throws DaoException;
    List<ClientOffer> find(User user, OfferStatusType status) throws DaoException;
    List<ClientOffer> find(OfferStatusType status) throws DaoException;
    List<ClientOffer> findByDealId(int dealId) throws DaoException;
    List<User> find(int offerId) throws DaoException;
    void changeStatus(int offerId, OfferStatusType status) throws DaoException;
    void delete(int offerId) throws DaoException;
    void deleteDealClientOffer(int offerId) throws DaoException;
    void deleteGoods(int offerId) throws DaoException;
}
