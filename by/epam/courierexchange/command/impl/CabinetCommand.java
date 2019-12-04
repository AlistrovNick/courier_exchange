package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.entity.User;
import by.epam.courierexchange.exception.CommandException;

import static by.epam.courierexchange.command.ParamName.ACTIVE;
import static by.epam.courierexchange.command.ParamName.CABINET;

public class CabinetCommand implements Command {
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
        content.getRequestAttributes().put(CABINET, ACTIVE);
        return page;
    }
}
