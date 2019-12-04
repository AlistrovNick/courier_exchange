package by.epam.courierexchange.service;

import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.controller.SessionRequestContent;

public class LocaleService {

    public static void change(SessionRequestContent content) {
        String locale = content.getRequestParameters().get(ParamName.LOCALE)[0];
        content.getSessionAttributes().put(ParamName.LOCALE, locale);
    }
}
