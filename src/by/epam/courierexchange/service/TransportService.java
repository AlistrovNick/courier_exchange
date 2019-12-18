package by.epam.courierexchange.service;

import by.epam.courierexchange.exception.ServiceException;

import java.util.List;

public interface TransportService {
    List<String> findAll() throws ServiceException;
}
