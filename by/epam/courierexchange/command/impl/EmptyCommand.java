package by.epam.courierexchange.command.impl;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.exception.CommandException;

public class EmptyCommand implements Command {
    @Override
    public String execute(SessionRequestContent content) throws CommandException {
        return null;
    }
}
