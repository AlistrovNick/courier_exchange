package by.epam.courierexchange.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public SessionRequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        Enumeration<String> requestAtt = request.getAttributeNames();
        while (requestAtt.hasMoreElements()) {
            String currentAttribute = requestAtt.nextElement();
            requestAttributes.put(currentAttribute, request.getAttribute(currentAttribute));
        }
        Enumeration<String> requestParam = request.getParameterNames();
        while (requestParam.hasMoreElements()) {
            String currentParameter = requestParam.nextElement();
            requestParameters.put(currentParameter, request.getParameterValues(currentParameter));
        }
        Enumeration<String> sessionAtt = request.getSession().getAttributeNames();
        while (sessionAtt.hasMoreElements()) {
            String currentAttribute = sessionAtt.nextElement();
            sessionAttributes.put(currentAttribute, request.getSession().getAttribute(currentAttribute));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        Set<String> attributes = requestAttributes.keySet();
        for (String attribute : attributes) {
            request.setAttribute(attribute, requestAttributes.get(attribute));
        }
        attributes.clear();
        attributes = sessionAttributes.keySet();
        for (String attribute : attributes) {
            request.getSession().setAttribute(attribute, sessionAttributes.get(attribute));
        }
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
