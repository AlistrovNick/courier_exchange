package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.ClientOfferRespondedServiceImpl;
import by.epam.courierexchange.service.impl.CourierOfferRespondedServiceImpl;
import by.epam.courierexchange.service.impl.RatingCourierServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.courierexchange.command.ParamName.*;

public class CourierFinishDealCommand implements Command {
    private final static String REDIRECT_PATH = "/final/Controller?command=courier_show_deal";
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        String declarer = content.getRequestParameters().get(DECLARER)[0];
        int dealId = Integer.parseInt(content.getRequestParameters().get(DEAL_ID)[0]);
        int courierId = Integer.parseInt(content.getRequestParameters().get(COURIER_ID)[0]);
        try {
            if (declarer.equals(COURIER)) {
                new CourierOfferRespondedServiceImpl().changeStatus(dealId, OfferStatusType.COMPLETED);
            }
            if (declarer.equals(CLIENT)) {
                new ClientOfferRespondedServiceImpl().changeStatus(dealId, OfferStatusType.COMPLETED);
            }
            new RatingCourierServiceImpl().insert(dealId, declarer, courierId, 0);
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.COURIER;
    }
}
