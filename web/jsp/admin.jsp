<%@ taglib prefix="info" uri="/WEB-INF/tld/info.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.pageContent"/>
<html>
<head>
    <title>Admin</title>
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
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">
                        <info:header user="${user}"/>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.session.servletContext.contextPath}/Controller?command=logout">
                        <fmt:message key="button.logout"/>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <ul class="nav nav-tabs">
        <li class="${cabinet}"><a data-toggle="tab" href="#cabinet"><fmt:message key="button.cabinet"/></a></li>
        <li class="${users}"><a data-toggle="tab" href="#users"><fmt:message key="button.show_user"/></a></li>
        <li class="${offers}">
            <a href="${pageContext.session.servletContext.contextPath}/Controller?command=admin_show_offer">
                <fmt:message key="button.show_offer"/>
            </a>
        </li>
    </ul>
    <div class="tab-content">
        <div id="cabinet" class="tab-pane fade in ${cabinet}">
            <br><br>
            <div class="panel panel-info" id="infoPanel">
                <div class="panel-body">
                    <div class="form-group">
                        <label class="control-label col-md-2">
                            <fmt:message key="label.email"/>
                        </label>
                        <label class="control-label">
                            ${user.email}
                        </label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">
                            <fmt:message key="label.first_name"/>
                        </label>
                        <label class="control-label">
                            ${user.firstName}
                        </label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">
                            <fmt:message key="label.last_name"/>
                        </label>
                        <label class="control-label">
                            ${user.lastName}
                        </label>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-primary" name="change" id="showHidePanel">
                <fmt:message key="button.change"/>
            </button>
            <form class="form-horizontal" onsubmit="return validate()"
                  action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="edit_profile">
                <div class="panel panel-info" id="changePanel" style="display: none">
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="control-label col-md-2">
                                <fmt:message key="label.first_name"/>
                            </label>
                            <div class="col-md-2">
                                <input type="text" name="firstName" value="${user.firstName}" class="form-control"/>
                            </div>
                            <label class="control-label" id="errorFirstName" style="display: none; color: red">
                                <fmt:message key="error.first_name"/>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">
                                <fmt:message key="label.last_name"/>
                            </label>
                            <div class="col-md-2">
                                <input type="text" name="lastName" value="${user.lastName}" class="form-control"/>
                            </div>
                            <label class="control-label" id="errorLastName" style="display: none; color: red">
                                <fmt:message key="error.last_name"/>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">
                                <fmt:message key="label.enter_new_password"/>
                            </label>
                            <div class="col-md-2">
                                <input type="password" name="password" class="form-control"/>
                            </div>
                            <label class="control-label" id="errorPassword" style="display: none; color: red">
                                <fmt:message key="error.passwords"/>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">
                                <fmt:message key="label.confirm_password"/>
                            </label>
                            <div class="col-md-2">
                                <input type="password" name="confirmPassword" class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-success" name="change" id="buttonSave" style="display: none">
                    <fmt:message key="button.save"/>
                </button>
                <button type="button" class="btn btn-danger" name="cancel" id="buttonCancel" style="display: none">
                    <fmt:message key="button.cancel"/>
                </button>
            </form>
        </div>
        <div id="users" class="tab-pane fade in ${users}">
            <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller"
                  method="post">
                <input type="hidden" name="command" value="admin_show_user">
                <div class="form-group">
                    <div class="checkbox">
                        <label class="control-label">
                            <input type="checkbox" name="courier">
                            <fmt:message key="label.couriers"/>
                        </label>
                    </div>
                    <div class="checkbox">
                        <label class="control-label">
                            <input type="checkbox" name="client">
                            <fmt:message key="label.clients"/>
                        </label>
                    </div>
                </div>
                <div>
                    <button type="submit" class="btn btn-success">
                        <fmt:message key="button.show_user"/>
                    </button>
                </div>
                <c:choose>
                    <c:when test="${errorSearchUser == true}">
                        <label class="control-label" style="color: orange">
                            <fmt:message key="error.search"/>
                        </label>
                    </c:when>
                    <c:when test="${errorSearchUser == false}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th><fmt:message key="column.id"/></th>
                                <th><fmt:message key="column.email"/></th>
                                <th><fmt:message key="column.first_name"/></th>
                                <th><fmt:message key="column.last_name"/></th>
                                <th><fmt:message key="column.role"/></th>
                                <th><fmt:message key="column.status"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="currentUser" items="${findUsers}">
                                <tr>
                                    <td>${currentUser.id}</td>
                                    <td>${currentUser.email}</td>
                                    <td>${currentUser.firstName}</td>
                                    <td>${currentUser.lastName}</td>
                                    <td>
                                        <fmt:message key="label.role.${currentUser.role.role}"/>
                                    </td>
                                    <td>
                                        <fmt:message key="label.status.${currentUser.status.status}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                </c:choose>
            </form>
        </div>
        <div id="offers" class="tab-pane fade in ${offers}">
            <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller"
                  method="post">
                <c:choose>
                    <c:when test="${errorSearchOffer == true}">
                        <label class="control-label" style="color: orange">
                            <fmt:message key="error.search"/>
                        </label>
                    </c:when>
                    <c:when test="${errorSearchOffer == false}">
                        <input type="hidden" name="command" value="admin_processing_offer">
                        <c:if test="${findCourier}">
                            <br/>
                            <table class="table table-striped">
                                <caption>
                                    <label class="control-label">
                                        <fmt:message key="label.courier_offer"/>
                                    </label>
                                </caption>
                                <thead>
                                <tr>
                                    <th><fmt:message key="label.courier_id"/></th>
                                    <th><fmt:message key="label.transport"/></th>
                                    <th><fmt:message key="label.goods"/></th>
                                    <th><fmt:message key="select.accept"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="currentCourierOffer" items="${findCourierOffer}">
                                    <tr>
                                        <td>${currentCourierOffer.user.id}</td>
                                        <td>
                                            <fmt:message key="label.transport.${currentCourierOffer.transport}"/>
                                        </td>
                                        <td>
                                            <c:forEach var="currentGoods" items="${currentCourierOffer.goods}">
                                                <fmt:message key="label.goods.${currentGoods}"/>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <select name="select" class="form-control" required>
                                                <option value="in_process courier ${currentCourierOffer.id}">
                                                    <fmt:message key="select.in_process"/>
                                                </option>
                                                <option value="accept courier ${currentCourierOffer.id}">
                                                    <fmt:message key="select.accept"/>
                                                </option>
                                                <option value="denied courier ${currentCourierOffer.id}">
                                                    <fmt:message key="select.denied"/>
                                                </option>
                                            </select>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <br/>
                        </c:if>
                        <c:if test="${findClient}">
                            <br/>
                            <table class="table table-striped">
                                <caption>
                                    <label class="control-label">
                                        <fmt:message key="label.client_offer"/>
                                    </label>
                                </caption>
                                <thead>
                                <tr>
                                    <th><fmt:message key="label.client_id"/></th>
                                    <th><fmt:message key="label.offer_info"/></th>
                                    <th><fmt:message key="select.accept"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="currentOffer" items="${findClientOffer}">
                                    <tr>
                                        <td>${currentOffer.user.id}</td>
                                        <td>
                                            <c:forEach var="currentGoods" items="${currentOffer.goods}">
                                                <fmt:message key="label.goods.${currentGoods}"/>
                                            </c:forEach>
                                                ${currentOffer.comment}
                                                ${currentOffer.date}
                                                ${currentOffer.startTime} -
                                                ${currentOffer.endTime}
                                        </td>
                                        <td>
                                            <select name="select" class="form-control" required>
                                                <option value="in_process client ${currentOffer.id}">
                                                    <fmt:message key="select.in_process"/>
                                                </option>
                                                <option value="accept client ${currentOffer.id}">
                                                    <fmt:message key="select.accept"/>
                                                </option>
                                                <option value="denied client ${currentOffer.id}">
                                                    <fmt:message key="select.denied"/>
                                                </option>
                                            </select>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <button type="submit" class="btn btn-success">
                            <fmt:message key="button.save"/>
                        </button>
                    </c:when>
                </c:choose>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#showHidePanel").click(function () {
            $("#infoPanel").hide();
            $("#showHidePanel").hide();
            $("#changePanel").show();
            $("#buttonSave").show();
            $("#buttonCancel").show();
            return false;
        });
    });
    $(document).ready(function () {
        $("#buttonCancel").click(function () {
            $("#changePanel").hide();
            $("#buttonSave").hide();
            $("#buttonCancel").hide();
            $("#infoPanel").show();
            $("#showHidePanel").show();
            return false;
        });
    });

    function validate() {
        var firstName = $('input[name=firstName]').val();
        var lastName = $('input[name=lastName]').val();
        var password = $('input[name=password]').val();
        var confirmPassword = $('input[name=confirmPassword]').val();
        var regularName = /[A-Za-z]{1,20}/;
        var regularPassword = /\w{5,20}/;
        var isValid = true;
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
        if ((regularPassword.test(password) == false && password != "") || password != confirmPassword) {
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
