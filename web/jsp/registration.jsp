<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.pageContent"/>
<html>
<head>
    <title>Registration</title>
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
                    <li>
                        <a href="${pageContext.session.servletContext.contextPath}/jsp/login.jsp">
                            <fmt:message key="label.sign_in"/>
                        </a>
                    </li>
                    <li class="active">
                        <a href="#">
                            <fmt:message key="label.registration"/>
                        </a>
                    </li>
                    <li>
                        <a href="#"><fmt:message key="label.about_us"/></a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="${pageContext.session.servletContext.contextPath}/Controller?command=locale&locale=ru_RU&page=/jsp/registration.jsp">рус</a>
                    </li>
                    <li>
                        <a href="${pageContext.session.servletContext.contextPath}/Controller?command=locale&locale=en_US&page=/jsp/registration.jsp">eng</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <br/><br/>
    <form name="RegistrationForm" class="form-horizontal" onsubmit="return validate()"
          action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
        <input type="hidden" name="command" value="registration"/>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.email"/>
            </label>
            <div class="col-md-2">
                <input type="email" name="email" value="${userValue.email}" class="form-control"
                       placeholder="<fmt:message key="label.enter_email"/>"/>
            </div>
            <label class="control-label" id="errorEmail" style="display: none; color: red">
                <fmt:message key="error.email"/>
            </label>
            <c:if test="${errorRegistrationEmail}">
                <label class="control-label col-md-2" style="color: red">
                    <fmt:message key="error.email"/>
                </label>
            </c:if>
        </div>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.first_name"/>
            </label>
            <div class="col-md-2">
                <input type="text" name="firstName" value="${userValue.firstName}"
                       class="form-control" placeholder="<fmt:message key="label.enter_first_name"/>"/>
            </div>
            <label class="control-label" id="errorFirstName" style="display: none; color: red">
                <fmt:message key="error.first_name"/>
            </label>
            <c:if test="${errorRegistrationFirstName}">
                <label class="control-label col-md-2" style="color: red">
                    <fmt:message key="error.first_name"/>
                </label>
            </c:if>
        </div>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.last_name"/>
            </label>
            <div class="col-md-2">
                <input type="text" name="lastName" value="${userValue.lastName}" class="form-control"
                       placeholder="<fmt:message key="label.enter_last_name"/>"/>
            </div>
            <label class="control-label" id="errorLastName" style="display: none; color: red">
                <fmt:message key="error.last_name"/>
            </label>
            <c:if test="${errorRegistrationLastName}">
                <label class="control-label col-md-2" style="color: red">
                    <fmt:message key="error.last_name"/>
                </label>
            </c:if>
        </div>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.password"/>
            </label>
            <div class="col-md-2">
                <input type="password" name="password" value="${userValue.password}" class="form-control"
                       placeholder="<fmt:message key="label.enter_password"/>"/>
            </div>
            <label class="control-label" id="errorPassword" style="display: none; color: red">
                <fmt:message key="error.passwords"/>
            </label>
            <c:if test="${errorRegistrationPassword}">
                <label class="control-label col-md-2" style="color: red">
                    <fmt:message key="error.passwords"/>
                </label>
            </c:if>
        </div>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.confirm_password"/>
            </label>
            <div class="col-md-2">
                <input type="password" name="confirmPassword" value="${userValue.confirmPassword}" class="form-control"
                       placeholder="<fmt:message key="label.confirm_password"/>"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-5">
                <fmt:message key="label.choose_role"/>
            </label>
            <div class="col-md-2">
                <select name="select" class="form-control">
                    <option value="${userValue.role}" selected><fmt:message key="select.role_${userValue.role}"/></option>
                    <option value="client"><fmt:message key="select.role_client"/></option>
                    <option value="courier"><fmt:message key="select.role_courier"/></option>
                </select>
            </div>
            <label class="control-label" id="errorRole" style="display: none; color: red">
                <fmt:message key="error.role"/>
            </label>
            <label class="control-label" id="errorRole" style="display: none; color: red">
                <fmt:message key="error.passwords"/>
            </label>
            <c:if test="${errorRegistrationRole}">
                <label class="control-label col-md-2" style="color: red">
                    <fmt:message key="error.role"/>
                </label>
            </c:if>
        </div>
        <div class="col-md-7">
            <button type="submit" class="btn btn-success pull-right">
                <i class="glyphicon glyphicon-user"></i>
                <fmt:message key="button.registration"/>
            </button>
        </div>
    </form>
    <script>
        function validate() {
            var email = $('input[name=email]').val();
            var firstName = $('input[name=firstName]').val();
            var lastName = $('input[name=lastName]').val();
            var password = $('input[name=password]').val();
            var confirmPassword = $('input[name=confirmPassword]').val();
            var role = $('select[name=select]').val();
            var regularEmail = /\w{3,20}@\w+\.\w{2,4}/;
            var regularName = /[A-Za-z]{1,20}/;
            var regularPassword = /\w{5,20}/;
            var isValid = true;
            if (regularEmail.test(email) == false) {
                $("#errorEmail").show();
                isValid = false;
            } else {
                $("#errorEmail").hide();
            }
            if (regularName.test(firstName) == false) {
                $("#errorFirstName").show();
                isValid = false;
            } else {
                $("#errorFirstName").hide();
            }
            if (regularName.test(lastName) == false) {
                $("#errorLastName").show();
                isValid = false;
            } else {
                $("#errorLastName").hide();
            }
            if (regularPassword.test(password) == false || password != confirmPassword) {
                $("#errorPassword").show();
                isValid = false;
            } else {
                $("#errorPassword").hide();
            }
            if (role !="courier" && role!="client") {
                $("#errorRole").show();
                isValid = false;
            } else {
                $("#errorRole").hide();
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
