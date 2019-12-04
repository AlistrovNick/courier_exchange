package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.*;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import static by.epam.courierexchange.command.ParamName.*;

public class CourierShowDealCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(ParamName.USER);
        List<ClientOffer> findClientOffer = new ArrayList<>();
        List<ClientOffer> findResponded = new ArrayList<>();
        List<ClientOffer> findWorkingOfferClient = new ArrayList<>();
        List<ClientOffer> findWorkingOfferCourier = new ArrayList<>();
        List<ClientOffer> findCompletedOfferClient = new ArrayList<>();
        List<ClientOffer> findCompletedOfferCourier = new ArrayList<>();
        try {
            findClientOffer.addAll(new ClientOfferRespondedService().findByCourier(user, OfferStatusType.IN_PROCESS));
            findResponded.addAll(new CourierOfferRespondedService().findByCourier(user, OfferStatusType.IN_PROCESS));
            findWorkingOfferClient.addAll(new ClientOfferRespondedService().findByCourier(user, OfferStatusType.WORKING));
            findWorkingOfferCourier.addAll(new CourierOfferRespondedService().findByCourier(user, OfferStatusType.WORKING));
            findCompletedOfferClient.addAll(new ClientOfferRespondedService().findByCourier(user, OfferStatusType.COMPLETED));
            findCompletedOfferCourier.addAll(new CourierOfferRespondedService().findByCourier(user, OfferStatusType.COMPLETED));
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(FIND_CLIENT_OFFER, findClientOffer);
        content.getRequestAttributes().put(FIND_RESPONDED, findResponded);
        content.getRequestAttributes().put(FIND_WORKING_OFFER_CLIENT, findWorkingOfferClient);
        content.getRequestAttributes().put(FIND_WORKING_OFFER_COURIER, findWorkingOfferCourier);
        content.getRequestAttributes().put(FIND_COMPLETED_OFFER_CLIENT, findCompletedOfferClient);
        content.getRequestAttributes().put(FIND_COMPLETED_OFFER_COURIER, findCompletedOfferCourier);
        content.getRequestAttributes().put(DEAL, ACTIVE);
        return PageName.COURIER;
    }
}
