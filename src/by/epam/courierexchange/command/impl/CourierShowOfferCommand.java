package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.CourierOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.CourierOfferServiceImpl;
import by.epam.courierexchange.service.impl.GoodsServiceImpl;
import by.epam.courierexchange.service.impl.TransportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.command.ParamName.*;

public class CourierShowOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(USER);
        List<CourierOffer> findOffer = new ArrayList<>();
        List<String> transportList = new ArrayList<>();
        List<String> goodsList = new ArrayList<>();
        try {
            CourierOfferServiceImpl courierOfferService = new CourierOfferServiceImpl();
            findOffer.addAll(courierOfferService.findByUserAndStatus(user, OfferStatusType.ACCEPT));
            findOffer.addAll(courierOfferService.findByUserAndStatus(user, OfferStatusType.IN_PROCESS));
            findOffer.addAll(courierOfferService.findByUserAndStatus(user, OfferStatusType.DENIED));
            transportList.addAll(new TransportServiceImpl().findAll());
            goodsList.addAll(new GoodsServiceImpl().findAll());
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(FIND_OFFER, findOffer);
        content.getRequestAttributes().put(GOODS_LIST, goodsList);
        content.getRequestAttributes().put(TRANSPORT_LIST, transportList);
        content.getRequestAttributes().put(OFFERS, ACTIVE);
        return PageName.COURIER;
    }
}
