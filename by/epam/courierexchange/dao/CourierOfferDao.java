package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface CourierOfferDao {


    List<CourierOffer> find(int offerId) throws DaoException;
    List<CourierOffer> find(User user) throws DaoException;
    List<CourierOffer> find(OfferStatusType status) throws DaoException;
    List<CourierOffer> find(User user, OfferStatusType status) throws DaoException;
    void changeStatus(int offerId, OfferStatusType newStatus) throws DaoException;
    int insert(CourierOffer offer) throws DaoException;

}
