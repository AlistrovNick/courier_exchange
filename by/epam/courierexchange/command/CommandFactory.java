package by.epam.courierexchange.command;

import by.epam.courierexchange.command.impl.EmptyCommand;
import by.epam.courierexchange.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.epam.courierexchange.command.ParamName.COMMAND;

public class CommandFactory {
    private Logger logger = LogManager.getLogger();

    public Command defineCommand(SessionRequestContent content) {
        String command = content.getRequestParameters().get(COMMAND)[0];
        if (command == null) {
            return new EmptyCommand();
        }
        logger.info("define command: " + command);
        CommandType currentCommand = CommandType.valueOf(command.toUpperCase());
        return currentCommand.getCommand();
    }
}
