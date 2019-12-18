package by.epam.courierexchange.service;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.ServiceException;

import java.util.List;

public interface ClientOfferService {
    void insert(ClientOffer clientOffer) throws ServiceException;

    List<ClientOffer> findByUserAndStatus(User user, OfferStatusType status) throws ServiceException;

    List<ClientOffer> findByStatus(OfferStatusType status) throws ServiceException;

    void changeStatus(int offerId, OfferStatusType status) throws ServiceException;

    void delete(int offerId) throws ServiceException;
}
