package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferService;
import by.epam.courierexchange.service.CourierOfferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.epam.courierexchange.command.ParamName.*;

public class AdminProcessingOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private final static String REGEX_SPLIT = "\\s";
    private final static String REDIRECT_PATH = "/final/Controller?command=admin_show_offer";

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        String[] select = content.getRequestParameters().get(SELECT);
        String[] paramSelect;
        OfferStatusType newOfferStatus;
        int offerId;
        try {
            for (String currentSelect : select) {
                paramSelect = currentSelect.split(REGEX_SPLIT);
                newOfferStatus = OfferStatusType.valueOf(paramSelect[0].toUpperCase());
                RoleType role = RoleType.valueOf(paramSelect[1].toUpperCase());
                offerId = Integer.parseInt(paramSelect[2]);
                switch (role) {
                    case COURIER:
                        new CourierOfferService().changeStatus(offerId, newOfferStatus);
                        break;
                    case CLIENT:
                        new ClientOfferService().changeStatus(offerId, newOfferStatus);
                        break;
                }
            }
        } catch (ServiceException e) {
            logger.error("LogicException ", e);
            throw new CommandException("LogicException ", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.ADMIN;
    }
}
