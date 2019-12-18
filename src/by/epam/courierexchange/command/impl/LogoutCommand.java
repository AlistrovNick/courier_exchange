package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;

public class LogoutCommand implements Command {

    @Override
    public String execute(SessionRequestContent content) {
        content.getRequestAttributes().clear();
        content.getRequestParameters().clear();
        content.getSessionAttributes().clear();
        content.getSessionAttributes().put(ParamName.INVALIDATE, true);
        return PageName.INDEX;
    }
}
