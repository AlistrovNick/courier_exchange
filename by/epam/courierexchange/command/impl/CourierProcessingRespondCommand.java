package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferRespondedService;
import by.epam.courierexchange.service.CourierOfferRespondedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.epam.courierexchange.command.ParamName.*;

public class CourierProcessingRespondCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private final static String REGEX_SPLIT = "\\s";
    private final static String REDIRECT_PATH = "/final/Controller?command=courier_show_deal";

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        boolean isAcceptPress = content.getRequestParameters().get(ACCEPT) != null;
        boolean isDeniedPress = content.getRequestParameters().get(DENIED) != null;
        try {
            if (isAcceptPress) {
                int acceptDealId = Integer.parseInt(content.getRequestParameters().get(ACCEPT)[0]);
                new CourierOfferRespondedService().changeStatus(acceptDealId, OfferStatusType.WORKING);
            }
            if (isDeniedPress) {
                int deniedDealId = Integer.parseInt(content.getRequestParameters().get(DENIED)[0]);
                new CourierOfferRespondedService().delete(deniedDealId);
            }
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.COURIER;
    }
}
