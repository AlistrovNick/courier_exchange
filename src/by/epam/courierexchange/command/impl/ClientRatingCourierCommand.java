package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.RatingCourierServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.courierexchange.command.ParamName.*;

public class ClientRatingCourierCommand implements Command {
    private final static String REDIRECT_PATH = "/final/Controller?command=client_show_deal";
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {

        System.out.println(content.getRequestParameters().get(FINISH_DECLARER)[0]);
        System.out.println(content.getRequestParameters().get(FINISH_DEAL_ID)[0]);
        System.out.println(content.getRequestParameters().get(RATING)[0]);


        String declarer = content.getRequestParameters().get(FINISH_DECLARER)[0];
        int dealId = Integer.parseInt(content.getRequestParameters().get(FINISH_DEAL_ID)[0]);
        int rating = Integer.parseInt(content.getRequestParameters().get(RATING)[0]);
        if (rating < 1 || rating > 5) {
            content.getRequestAttributes().put(ERROR_RATING, true);
            return PageName.CLIENT;
        }
        try {
            new RatingCourierServiceImpl().update(dealId, declarer, rating);
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        return PageName.CLIENT;
    }
}
