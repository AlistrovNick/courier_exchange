package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.ClientOfferServiceImpl;
import by.epam.courierexchange.service.impl.GoodsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.command.ParamName.*;

public class ClientShowOfferCommand implements Command {
    private final static int INTERVAL_DAYS = 90;
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(USER);
        List<ClientOffer> findOffer = new ArrayList<>();
        List<String> goodsList = new ArrayList<>();
        try {
            ClientOfferServiceImpl clientOfferService = new ClientOfferServiceImpl();
            findOffer.addAll(clientOfferService.findByUserAndStatus(user, OfferStatusType.ACCEPT));
            findOffer.addAll(clientOfferService.findByUserAndStatus(user, OfferStatusType.IN_PROCESS));
            findOffer.addAll(clientOfferService.findByUserAndStatus(user, OfferStatusType.DENIED));
            GoodsServiceImpl goodsService = new GoodsServiceImpl();
            goodsList.addAll(goodsService.findAll());
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(FIND_OFFER, findOffer);
        LocalDate today = LocalDate.now();
        LocalDate lastDay = today.plusDays(INTERVAL_DAYS);
        content.getRequestAttributes().put(TODAY, today);
        content.getRequestAttributes().put(LAST_DAY, lastDay);
        content.getRequestAttributes().put(GOODS_LIST, goodsList);
        content.getRequestAttributes().put(OFFERS, ACTIVE);
        return PageName.CLIENT;
    }
}
