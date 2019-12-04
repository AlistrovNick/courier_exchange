package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferRespondedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.epam.courierexchange.command.ParamName.*;

public class CourierRespondClientOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private final static String REDIRECT_PATH = "/final/Controller?command=courier_show_client_offer";

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(USER);
        int offerId = Integer.parseInt(content.getRequestParameters().get(RESPOND)[0]);
        try {
            new ClientOfferRespondedService().insert(offerId, user);
        } catch (ServiceException e) {
            logger.error("execute");
            throw new CommandException("execute");
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.COURIER;
    }
}
