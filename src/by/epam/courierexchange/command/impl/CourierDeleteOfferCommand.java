package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.CourierOfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.courierexchange.command.ParamName.CANCEL;
import static by.epam.courierexchange.command.ParamName.YOUR_OFFER;

public class CourierDeleteOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        int offerId = Integer.parseInt(content.getRequestParameters().get(CANCEL)[0]);
        try {
            new CourierOfferServiceImpl().changeStatus(offerId, OfferStatusType.DELETED);
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(YOUR_OFFER, true);
        new CourierShowOfferCommand().execute(content);
        return PageName.COURIER;
    }
}
