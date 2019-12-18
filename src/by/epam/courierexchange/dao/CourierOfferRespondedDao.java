package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface CourierOfferRespondedDao {
    List<ClientOffer> findByCourierAndStatus(User courier, OfferStatusType status) throws DaoException;

    List<ClientOffer> findByClientAndStatus(User client, OfferStatusType status) throws DaoException;

    void insert(int offerId, User client, String comment) throws DaoException;

    void changeStatus(int dealId, OfferStatusType newStatus) throws DaoException;

    void delete(int dealId) throws DaoException;
}
