package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.ClientOffer;
import by.epam.courierexchange.entity.OfferStatusType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import static by.epam.courierexchange.command.ParamName.*;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.ClientOfferService;
import by.epam.courierexchange.utilite.XSSSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class ClientSendOfferCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private final static String REDIRECT_PATH = "/final/Controller?command=client_show_offer";

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        ClientOffer clientOffer = new ClientOffer();
        clientOffer.setUser((User) content.getSessionAttributes().get(USER));
        if (content.getRequestParameters().get(GOODS) != null) {
            clientOffer.setGoods(Arrays.asList(content.getRequestParameters().get(GOODS)));
        }
        clientOffer.setComment(XSSSecurity.secure(content.getRequestParameters().get(COMMENT)[0]));
        clientOffer.setStatus(OfferStatusType.IN_PROCESS);
        String startTerm = content.getRequestParameters().get(START_TERM)[0];
        String startTime = content.getRequestParameters().get(START_TIME)[0];
        String endTime = content.getRequestParameters().get(END_TIME)[0];
        if (content.getRequestParameters().get(COMMENT)[0].isEmpty() || content.getRequestParameters().get(COMMENT)[0].isBlank()) {
            content.getRequestAttributes().put(ERROR_COMMENT, true);
            return PageName.CLIENT;
        }
        if (startTerm.isEmpty()) {
            clientOffer.setDate(null);
        } else {
            clientOffer.setDate(LocalDate.parse(startTerm));
        }
        if (startTime.isEmpty()) {
            clientOffer.setStartTime(null);
        } else {
            clientOffer.setStartTime(LocalTime.parse(startTime));
        }
        if (endTime.isEmpty()) {
            clientOffer.setEndTime(null);
        } else {
            clientOffer.setEndTime(LocalTime.parse(endTime));
        }
        try {
            new ClientOfferService().insert(clientOffer);
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.CLIENT;
    }
}
