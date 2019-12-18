package by.epam.courierexchange.service;

import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.ServiceException;

import java.util.List;

public interface CourierOfferService {
    List<CourierOffer> findByStatus(OfferStatusType status) throws ServiceException;

    List<CourierOffer> findByUserAndStatus(User user, OfferStatusType status) throws ServiceException;

    void insert(CourierOffer offer) throws ServiceException;

    void changeStatus(int offerId, OfferStatusType newStatus) throws ServiceException;
}
