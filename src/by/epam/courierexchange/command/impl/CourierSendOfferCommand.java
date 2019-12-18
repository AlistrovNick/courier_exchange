package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.CourierOfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

import static by.epam.courierexchange.command.ParamName.*;

public class CourierSendOfferCommand implements Command {
    private final static String REDIRECT_PATH = "/final/Controller?command=courier_show_offer";
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        boolean errorTransport = content.getRequestParameters().get(TRANSPORT) == null;
        boolean errorGoods = content.getRequestParameters().get(GOODS) == null;
        if (errorTransport || errorGoods) {
            content.getRequestAttributes().put(ERROR_TRANSPORT_GOODS, true);
            content.getRequestAttributes().put(OFFERS, ACTIVE);
        } else {
            try {
                new CourierOfferServiceImpl().insert(createOffer(content));
            } catch (ServiceException e) {
                logger.error("execute ", e);
                throw new CommandException("execute ", e);
            }
            content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        }
        return PageName.COURIER;
    }

    private CourierOffer createOffer(SessionRequestContent content) {
        CourierOffer offer = new CourierOffer();
        offer.setUser((User) content.getSessionAttributes().get(USER));
        offer.setTransport(content.getRequestParameters().get(TRANSPORT)[0]);
        offer.setGoods(Arrays.asList(content.getRequestParameters().get(GOODS)));
        return offer;
    }
}
