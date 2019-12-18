package by.epam.courierexchange.service;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.ServiceException;

import java.util.List;

public interface CourierOfferRespondedService {
    void insert(int offerId, User client, String comment) throws ServiceException;

    List<ClientOffer> findByCourier(User courier, OfferStatusType status) throws ServiceException;

    List<ClientOffer> findByClient(User client, OfferStatusType status) throws ServiceException;

    void changeStatus(int dealId, OfferStatusType newStatus) throws ServiceException;

    void delete(int dealId) throws ServiceException;
}
