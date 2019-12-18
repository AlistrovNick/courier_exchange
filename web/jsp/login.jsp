<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.pageContent"/>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand">
                    <fmt:message key="label.courier_exchange"/>
                </a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active">
                        <a href="#"><fmt:message key="label.sign_in"/></a>
                    </li>
                    <li>
                        <a href="${pageContext.session.servletContext.contextPath}/jsp/registration.jsp">
                            <fmt:message key="label.registration"/>
                        </a>
                    </li>
                    <li>
                        <a href="#"><fmt:message key="label.about_us"/></a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="${pageContext.session.servletContext.contextPath}/Controller?command=locale&locale=ru_RU&page=/jsp/login.jsp">рус</a>
                    </li>
                    <li>
                        <a href="${pageContext.session.servletContext.contextPath}/Controller?command=locale&locale=en_US&page=/jsp/login.jsp">eng</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <c:if test="${invalidate}">
        ${pageContext.session.invalidate()}
    </c:if>
    <br/><br/>
    <form name="LoginForm" class="form-horizontal" onsubmit="return validate()"
          action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.email"/>
            </label>
            <div class="col-md-2">
                <input type="email" name="email" class="form-control" placeholder="<fmt:message key="label.enter_email"/>"/>
            </div>
            <label class="control-label" id="errorEmail" style="display: none; color: red">
                <fmt:message key="error.email"/>
            </label>
            <c:if test="${error}">
                <label class="control-label" style="color: red">
                    <fmt:message key="error.login_pass"/>
                </label>
            </c:if>
        </div>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.password"/>
            </label>
            <div class="col-md-2">
                <input type="password" name="password" class="form-control" pattern="\w{5,20}"
                       placeholder="<fmt:message key="label.enter_password"/>"/>
            </div>
            <label class="control-label" id="errorPassword" style="display: none; color: red">
                <fmt:message key="error.password"/>
            </label>
        </div>
        <div class="col-md-7">
            <button type="submit" class="btn btn-success pull-right">
                <i class="glyphicon glyphicon-user"></i>
                <fmt:message key="button.sign_in"/>
            </button>
        </div>
    </form>

    <script>
        function validate() {
            var email = $('input[name=email]').val();
            var password = $('input[name=password]').val();
            var regularEmail = /[A-Za-z]{1,20}/;
            var regularPassword = /\w{5,20}/;
            var isValid = true;
            if (regularEmail.test(email) == false) {
                $("#errorEmail").show();
                isValid = false;
            } else {
                $("#errorEmail").hide();
            }
            if (regularPassword.test(password) == false) {
                $("#errorPassword").show();
                isValid = false;
            } else {
                $("#errorPassword").hide();
            }
            if (isValid == true) {
                return true;
            } else {
                return false;
            }
        }
    </script>
</body>
</html>
