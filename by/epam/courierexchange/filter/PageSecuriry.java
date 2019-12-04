package by.epam.courierexchange.filter;

import by.epam.courierexchange.command.PageName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/admin.jsp", "/jsp/courier.jsp", "/jsp/client.jsp", "/jsp/error.jsp"})
public class PageSecuriry implements Filter {
    private final static String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(ENCODING);
        response.sendRedirect(request.getContextPath() + PageName.INDEX);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
