package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferRespondedService;
import by.epam.courierexchange.service.ClientOfferService;
import by.epam.courierexchange.service.CourierOfferRespondedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.courierexchange.command.ParamName.*;

public class ClientFinishDealCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private final static String REDIRECT_PATH = "/final/Controller?command=client_show_deal";
    private final static String REGEX_SPLIT = "\\s";

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        try {
            int dealId = Integer.parseInt(content.getRequestParameters().get(FINISH)[0].split(REGEX_SPLIT)[0]);
            if (content.getRequestParameters().get(FINISH)[0].split(REGEX_SPLIT)[1].equals(COURIER)) {
                new CourierOfferRespondedService().changeStatus(dealId, OfferStatusType.COMPLETED);
            }
            if (content.getRequestParameters().get(FINISH)[0].split(REGEX_SPLIT)[1].equals(CLIENT)) {
                new ClientOfferRespondedService().changeStatus(dealId, OfferStatusType.COMPLETED);
            }
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.CLIENT;
    }
}
