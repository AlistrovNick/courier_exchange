package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.CourierOfferRespondedServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.courierexchange.command.ParamName.CANCEL;
import static by.epam.courierexchange.command.ParamName.REDIRECT;

public class ClientCancelRespondCommand implements Command {
    private final static String REDIRECT_PATH = "/final/Controller?command=client_show_deal";
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        int dealId = Integer.parseInt(content.getRequestParameters().get(CANCEL)[0]);
        try {
            new CourierOfferRespondedServiceImpl().delete(dealId);
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException();
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.CLIENT;
    }
}
