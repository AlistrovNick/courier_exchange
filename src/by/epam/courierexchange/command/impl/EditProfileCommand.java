package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.UserServiceImpl;
import by.epam.courierexchange.utilite.XSSSecurity;
import by.epam.courierexchange.validator.ValidatorPersonalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.epam.courierexchange.command.ParamName.*;

public class EditProfileCommand implements Command {
    private final static String REDIRECT_PATH = "/final/Controller?command=cabinet";
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttributes().get(ParamName.USER);
        String page;
        switch (user.getRole()) {
            case ADMIN:
                page = PageName.ADMIN;
                break;
            case COURIER:
                page = PageName.COURIER;
                break;
            case CLIENT:
                page = PageName.CLIENT;
                break;
            default:
                page = PageName.ERROR;
                break;
        }
        Map<String, String> userValue = new HashMap<>();
        userValue.put(FIRST_NAME, XSSSecurity.secure(content.getRequestParameters().get(FIRST_NAME)[0]));
        userValue.put(LAST_NAME, XSSSecurity.secure(content.getRequestParameters().get(LAST_NAME)[0]));
        userValue.put(PASSWORD, XSSSecurity.secure(content.getRequestParameters().get(PASSWORD)[0]));
        userValue.put(CONFIRM_PASSWORD, XSSSecurity.secure(content.getRequestParameters().get(CONFIRM_PASSWORD)[0]));
        content.getRequestAttributes().put(USER_VALUE, userValue);
        try {
            UserServiceImpl userService = new UserServiceImpl();
            if (!userService.changeFirstName(user, userValue.get(FIRST_NAME))) {
                content.getRequestAttributes().put(ERROR_REGISTRATION_FIRST_NAME, true);
                return page;
            }
            if (!userService.changeLastName(user, userValue.get(LAST_NAME))) {
                content.getRequestAttributes().put(ERROR_REGISTRATION_LAST_NAME, true);
                return page;
            }
            if (!userService.changePassword(user, userValue.get(PASSWORD), userValue.get(CONFIRM_PASSWORD))) {
                content.getRequestAttributes().put(ERROR_REGISTRATION_PASSWORD, true);
                return page;
            }
            user = userService.findById(user.getId()).get(0);
            content.getSessionAttributes().put(USER, user);
            content.getRequestParameters().put(REDIRECT, new String[]{REDIRECT_PATH});
        } catch (ServiceException e) {
            logger.error("execute", e);
            throw new CommandException("execute", e);
        }
        return page;
    }
}
