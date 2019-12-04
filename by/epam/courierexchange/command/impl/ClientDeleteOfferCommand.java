package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.epam.courierexchange.command.ParamName.*;

public class ClientDeleteOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private final static String REDIRECT_PATH = "/final/Controller?command=client_show_offer";

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        int offerId = Integer.parseInt(content.getRequestParameters().get(CANCEL)[0]);
        try {
            new ClientOfferService().delete(offerId);
        } catch (ServiceException e) {
            logger.error("LogicException ", e);
            throw new CommandException("LogicException ", e);
        }
        content.getRequestAttributes().put(OFFERS, ACTIVE);
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.CLIENT;
    }
}
