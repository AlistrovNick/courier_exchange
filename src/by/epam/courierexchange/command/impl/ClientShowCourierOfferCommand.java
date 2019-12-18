package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.CourierOfferServiceImpl;
import by.epam.courierexchange.service.impl.RatingCourierServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static by.epam.courierexchange.command.ParamName.*;

public class ClientShowCourierOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        List<CourierOffer> findCourierOffer;
        Map<Integer, Double> courierRating;
        try {
            findCourierOffer = new CourierOfferServiceImpl().findByStatus(OfferStatusType.ACCEPT);
            courierRating = new RatingCourierServiceImpl().findAllCourier();
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(COURIER_LIST, findCourierOffer);
        content.getRequestAttributes().put(COURIER_RATING, courierRating);
        content.getRequestAttributes().put(COURIER, ACTIVE);
        return PageName.CLIENT;
    }
}
