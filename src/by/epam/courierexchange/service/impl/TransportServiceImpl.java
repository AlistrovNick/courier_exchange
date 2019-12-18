package by.epam.courierexchange.service.impl;

import by.epam.courierexchange.dao.impl.TransportDaoImpl;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.TransportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TransportServiceImpl implements TransportService {
    private static Logger logger = LogManager.getLogger();

    public List<String> findAll() throws ServiceException {
        List<String> findList = new ArrayList<>();
        try {
            findList.addAll(TransportDaoImpl.INSTANCE.findAll());
        } catch (DaoException e) {
            logger.error("findAll", e);
            throw new ServiceException("findAll", e);
        }
        return findList;
    }
}
