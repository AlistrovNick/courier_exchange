package by.epam.courierexchange.controller;

import by.epam.courierexchange.command.Command;
import by.epam.courierexchange.command.CommandFactory;
import by.epam.courierexchange.command.PageName;
import by.epam.courierexchange.command.ParamName;
import by.epam.courierexchange.exception.CommandException;
import by.epam.courierexchange.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
    private final static String REDIRECT = "redirect";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.INSTANCE.destroyPool();
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent();
        content.extractValues(req);
        Command command = new CommandFactory().defineCommand(content);
        String page;
        try {
            page = command.execute(content);
        } catch (CommandException e) {
            content.getRequestAttributes().put(ParamName.ERROR, ParamName.ERROR_DB);
            page = PageName.ERROR;
        }
        content.insertAttributes(req);
        if (content.getRequestParameters().get(REDIRECT) == null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else {
            String redirect = content.getRequestParameters().get(REDIRECT)[0];
            resp.sendRedirect(redirect);
        }

    }
}
