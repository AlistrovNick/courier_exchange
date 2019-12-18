package by.epam.courierexchange.service;

import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.ServiceException;

import java.util.List;

public interface ClientOfferRespondedService {
    List<Integer> findOfferId(User courier, OfferStatusType status) throws ServiceException;

    List<ClientOffer> findByCourierAndStatus(User courier, OfferStatusType status) throws ServiceException;

    List<ClientOffer> findByClientAndStatus(User client, OfferStatusType status) throws ServiceException;

    void insert(int offerId, User courier) throws ServiceException;

    void deleteByOfferId(int offerId) throws ServiceException;

    void deleteByDealId(int dealId) throws ServiceException;

    void changeStatus(int dealId, OfferStatusType newStatus) throws ServiceException;
}
