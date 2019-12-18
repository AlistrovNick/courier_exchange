<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.pageContent"/>
<html>
<head>
    <title>error</title>
</head>
<body>
<label><fmt:message key="error.db"/></label>
</body>
</html>
