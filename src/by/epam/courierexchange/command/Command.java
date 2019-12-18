package by.epam.courierexchange.command;

import by.epam.courierexchange.controller.SessionRequestContent;
import by.epam.courierexchange.exception.CommandException;

public interface Command {
    String execute(SessionRequestContent content) throws CommandException;
}
