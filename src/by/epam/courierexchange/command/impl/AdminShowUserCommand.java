package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.RoleType;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static by.epam.courierexchange.command.ParamName.*;

public class AdminShowUserCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        List<User> findList = new ArrayList<>();
        boolean isCourierChecked = content.getRequestParameters().get(COURIER) != null;
        boolean isClientChecked = content.getRequestParameters().get(CLIENT) != null;
        try {
            UserServiceImpl userService = new UserServiceImpl();
            if (isCourierChecked) {
                findList.addAll(userService.findByRole(RoleType.COURIER));
            }
            if (isClientChecked) {
                findList.addAll(userService.findByRole(RoleType.CLIENT));
            }
        } catch (ServiceException e) {
            logger.error("LogicException ", e);
            throw new CommandException("LogicException ", e);
        }
        if (findList.size() == 0) {
            content.getRequestAttributes().put(ERROR_SEARCH_USER, true);
        } else {
            content.getRequestAttributes().put(FIND_USERS, findList);
            content.getRequestAttributes().put(ERROR_SEARCH_USER, false);
        }
        content.getRequestAttributes().put(USERS, ACTIVE);
        return PageName.ADMIN;
    }
}
