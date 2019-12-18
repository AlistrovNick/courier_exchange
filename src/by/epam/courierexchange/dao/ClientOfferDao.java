package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface ClientOfferDao {
    int insert(ClientOffer clientOffer) throws DaoException;

    List<ClientOffer> findByUserAndStatus(User user, OfferStatusType status) throws DaoException;

    List<ClientOffer> findByStatus(OfferStatusType status) throws DaoException;

    void changeStatus(int offerId, OfferStatusType status) throws DaoException;

    void delete(int offerId) throws DaoException;

    void deleteGoods(int offerId) throws DaoException;
}
