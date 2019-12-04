package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.service.LocaleService;

public class LocaleCommand implements Command {
    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        LocaleService.change(content);
        String page = content.getRequestParameters().get(ParamName.PAGE)[0];
        return page;
    }
}
