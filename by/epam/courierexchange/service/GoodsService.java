package by.epam.courierexchange.service;

import by.epam.courierexchange.dao.impl.GoodsDaoImp;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GoodsService {
    private static Logger logger = LogManager.getLogger();

    public List<String> findAll() throws ServiceException {
        List<String> findList = new ArrayList<>();
        try {
            findList.addAll(GoodsDaoImp.INSTANCE.findAll());
        } catch (DaoException e) {
            logger.error("findAll", e);
            throw new ServiceException("findAll", e);
        }
        return findList;
    }
}
