package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferService;
import by.epam.courierexchange.service.CourierOfferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import static by.epam.courierexchange.command.ParamName.*;

public class AdminShowOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        List<CourierOffer> courierOfferList;
        List<ClientOffer> clientOfferList;
        try {
            courierOfferList = new CourierOfferService().find(OfferStatusType.IN_PROCESS);
            clientOfferList = new ClientOfferService().find(OfferStatusType.IN_PROCESS);
        } catch (ServiceException e) {
            logger.error("LogicException", e);
            throw new CommandException("LogicException", e);
        }
        if (courierOfferList.size() == 0 && clientOfferList.size() == 0) {
            content.getRequestAttributes().put(ERROR_SEARCH_OFFER, true);
            content.getRequestAttributes().put(OFFERS, ACTIVE);
            return PageName.ADMIN;
        }
        if (courierOfferList.size() != 0) {
            content.getRequestAttributes().put(FIND_COURIER, true);
            content.getRequestAttributes().put(FIND_COURIER_OFFER, courierOfferList);
        }
        if (clientOfferList.size() != 0) {
            content.getRequestAttributes().put(FIND_CLIENT, true);
            content.getRequestAttributes().put(FIND_CLIENT_OFFER, clientOfferList);
        }
        content.getRequestAttributes().put(ERROR_SEARCH_OFFER, false);
        content.getRequestAttributes().put(SHOW_OFFER, true);
        content.getRequestAttributes().put(OFFERS, ACTIVE);
        return PageName.ADMIN;
    }
}
