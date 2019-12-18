package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.RatingCourierServiceImpl;
import by.epam.courierexchange.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.epam.courierexchange.command.ParamName.*;

public class LoginCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        try {
            UserServiceImpl userService = new UserServiceImpl();
            String email = content.getRequestParameters().get(EMAIL)[0];
            if (!userService.checkByEmail(email)) {
                content.getRequestAttributes().put(ERROR, true);
                return PageName.LOGIN;
            }
            List<User> userList = userService.findByEmail(email);
            String password = userService.codingPassword(content.getRequestParameters().get(PASSWORD)[0]);
            String truePassword = userService.findPasswordById(userList.get(0).getId());
            if (!truePassword.equals(password)) {
                content.getRequestAttributes().put(ERROR, true);
                return PageName.LOGIN;
            }
            content.getSessionAttributes().put(USER, userList.get(0));
            content.getRequestAttributes().put(CABINET, ACTIVE);
            switch (userList.get(0).getRole()) {
                case ADMIN:
                    return PageName.ADMIN;
                case COURIER:
                    double rating = new RatingCourierServiceImpl().findByCourierId(userList.get(0).getId());
                    content.getSessionAttributes().put(RATING, rating);
                    return PageName.COURIER;
                case CLIENT:
                    return PageName.CLIENT;
                default:
                    return PageName.LOGIN;
            }
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
    }
}
