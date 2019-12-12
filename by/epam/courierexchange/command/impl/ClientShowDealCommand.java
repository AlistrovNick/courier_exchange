package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.*;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferRespondedService;
import by.epam.courierexchange.service.CourierOfferRespondedService;
import by.epam.courierexchange.service.RatingCourierService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.courierexchange.command.ParamName.*;

public class ClientShowDealCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(USER);
        List<ClientOffer> findCourierOffer = new ArrayList<>();
        List<ClientOffer> findResponded = new ArrayList<>();
        List<ClientOffer> findWorkingOfferClient = new ArrayList<>();
        List<ClientOffer> findWorkingOfferCourier = new ArrayList<>();
        List<ClientOffer> findCompletedOfferClient = new ArrayList<>();
        List<ClientOffer> findCompletedOfferCourier = new ArrayList<>();
        Map<Integer, ClientOffer>  offerMap = new HashMap<>();
        Map<Integer, List<User>> courierMap = new HashMap<>();
        Map<Integer, Integer> ratingCourierMap;
        Map<Integer, Integer> ratingClientMap;
        try {
            findCourierOffer.addAll(new CourierOfferRespondedService().findByClient(user, OfferStatusType.IN_PROCESS));
            findResponded.addAll(new ClientOfferRespondedService().findByClient(user, OfferStatusType.IN_PROCESS));
            for (ClientOffer currentOffer : findResponded) {
                if (offerMap.containsKey(currentOffer.getId())) {
                    courierMap.get(currentOffer.getId()).add(currentOffer.getUser());
                } else {
                    offerMap.put(currentOffer.getId(), currentOffer);
                    courierMap.put(currentOffer.getId(), new ArrayList<>());
                    courierMap.get(currentOffer.getId()).add(currentOffer.getUser());
                }
            }
            findWorkingOfferClient.addAll(new ClientOfferRespondedService().findByClient(user, OfferStatusType.WORKING));
            findWorkingOfferCourier.addAll(new CourierOfferRespondedService().findByClient(user, OfferStatusType.WORKING));
            findCompletedOfferClient.addAll(new ClientOfferRespondedService().findByClient(user, OfferStatusType.COMPLETED));
            findCompletedOfferCourier.addAll(new CourierOfferRespondedService().findByClient(user, OfferStatusType.COMPLETED));
            ratingClientMap = new RatingCourierService().findByDeclarer(CLIENT);
            ratingCourierMap = new RatingCourierService().findByDeclarer(COURIER);
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(FIND_COURIER_OFFER, findCourierOffer);
        content.getRequestAttributes().put(OFFER_MAP, offerMap);
        content.getRequestAttributes().put(COURIER_MAP, courierMap);
        content.getRequestAttributes().put(FIND_WORKING_OFFER_CLIENT, findWorkingOfferClient);
        content.getRequestAttributes().put(FIND_WORKING_OFFER_COURIER, findWorkingOfferCourier);
        content.getRequestAttributes().put(FIND_COMPLETED_OFFER_CLIENT, findCompletedOfferClient);
        content.getRequestAttributes().put(FIND_COMPLETED_OFFER_COURIER, findCompletedOfferCourier);
        content.getRequestAttributes().put(RATING_CLIENT_MAP, ratingClientMap);
        content.getRequestAttributes().put(RATING_COURIER_MAP, ratingCourierMap);
        content.getRequestAttributes().put(DEAL, ACTIVE);
        return PageName.CLIENT;
    }
}
