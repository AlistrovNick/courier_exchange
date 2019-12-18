package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.ClientOfferRespondedServiceImpl;
import by.epam.courierexchange.service.impl.ClientOfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.courierexchange.command.ParamName.*;

public class CourierShowClientOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(USER);
        List<ClientOffer> clientList = new ArrayList<>();
        Map<Integer, Boolean> isExist = new HashMap<>();
        try {
            clientList.addAll(new ClientOfferServiceImpl().findByStatus(OfferStatusType.ACCEPT));
            List<Integer> clientOfferIdResponded = new ClientOfferRespondedServiceImpl().findOfferId(user, OfferStatusType.IN_PROCESS);
            for (int i = 0; i < clientOfferIdResponded.size(); i++) {
                int j = 0;
                boolean isNotFound = true;
                while (j < clientList.size() && isNotFound) {
                    if (clientOfferIdResponded.get(i) == clientList.get(j).getId()) {
                        isExist.put(clientList.get(j).getId(), true);
                        isNotFound = false;
                    }
                    j++;
                }
            }
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestAttributes().put(CLIENT_LIST, clientList);
        content.getRequestAttributes().put(IS_EXIST, isExist);
        content.getRequestAttributes().put(CLIENT, ACTIVE);
        return PageName.COURIER;
    }
}
