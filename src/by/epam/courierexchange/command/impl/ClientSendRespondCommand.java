package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.CourierOfferRespondedServiceImpl;
import by.epam.courierexchange.utilite.XSSSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.courierexchange.command.ParamName.REDIRECT;

public class ClientSendRespondCommand implements Command {
    private final static String REDIRECT_PATH = "/final/Controller?command=client_show_deal";
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(ParamName.USER);
        int courierOfferId = Integer.parseInt(content.getRequestParameters().get(ParamName.OFFER_ID)[0]);
        String comment = XSSSecurity.secure(content.getRequestParameters().get(ParamName.COMMENT)[0]);
        if (comment.isEmpty() || comment.isBlank()) {
            content.getRequestAttributes().put(ParamName.ERROR_COMMENT, true);
            return PageName.CLIENT;
        }
        try {
            new CourierOfferRespondedServiceImpl().insert(courierOfferId, user, comment);
        } catch (ServiceException e) {
            logger.error("execute");
            throw new CommandException("execute", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.CLIENT;
    }
}
