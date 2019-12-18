<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="info" uri="info"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.pageContent"/>
<html>
<head>
    <title>courier</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<!-- nav-tabs -->
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
<!-- tabs -->
    <div class="container">
        <ul class="nav nav-tabs">
            <li class="${cabinet}"><a data-toggle="tab" href="#cabinet"><fmt:message key="button.cabinet"/></a></li>
            <li class="${offers}">
                <a href="${pageContext.session.servletContext.contextPath}/Controller?command=courier_show_offer">
                    <fmt:message key="button.your_offers"/>
                </a>
            </li>
            <li class="${deal}">
                <a href="${pageContext.session.servletContext.contextPath}/Controller?command=courier_show_deal">
                    <fmt:message key="button.your_deals"/>
                </a>
            </li>
            <li class="${client}">
                <a href="${pageContext.session.servletContext.contextPath}/Controller?command=courier_show_client_offer">
                    <fmt:message key="button.find_client"/>
                </a>
            </li>
        </ul>
<!-- content -->
        <div class="tab-content">
<!-- cabinet -->
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
                        <div class="form-group">
                            <label class="control-label col-md-2">
                                <fmt:message key="label.rating"/>
                            </label>
                            <label class="control-label">
                                ${rating}
                            </label>
                        </div>
                    </div>
                </div>
                <button type="button" class="btn btn-primary" name="change" id="changeUserData">
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
                    <button type="button" class="btn btn-danger" name="cancel" id="cancelChangeUserData" style="display: none">
                        <fmt:message key="button.cancel"/>
                    </button>
                </form>
            </div>
<!-- your offers -->
            <div id="offers" class="tab-pane fade in ${offers}">
                <br/>
                <form class="form-horizontal" id="offerForm"
                          action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="courier_delete_offer">
                    <c:if test="${findOffer.size() == 0}">
                        <label class="control-label" style="color: orange">
                            <fmt:message key="error.search"/>
                        </label>
                    </c:if>
                    <c:if test="${findOffer.size() > 0}">
                        <table class="table table-striped">
                            <caption>
                                <label class="control-label">
                                    <fmt:message key="label.your_offer"/>
                                </label>
                            </caption>
                            <thead>
                            <tr>
                                <th><fmt:message key="label.offer_info"/> </th>
                                <th><fmt:message key="label.status"/> </th>
                                <th><fmt:message key="label.cancel"/> </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="currentOffer" items="${findOffer}">
                                <tr>
                                    <td>
                                        <fmt:message key="label.transport.${currentOffer.transport}"/> -
                                        <c:forEach var="currentGoods" items="${currentOffer.goods}">
                                            <fmt:message key="label.goods.${currentGoods}"/>
                                        </c:forEach>

                                    </td>
                                    <td>
                                        <fmt:message key="label.status.${currentOffer.status.status}"/>
                                    </td>
                                    <td>
                                        <button type="submit" class="btn btn-danger" name="cancel" value="${currentOffer.id}">
                                            <fmt:message key="button.cancel"/>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <br><br>
                    <button type="button" class="btn btn-primary" name="newOffer" id="newOffer">
                        <fmt:message key="button.new_offer"/>
                    </button>
                </form>
                <form class="form-horizontal" id="newOfferForm" style="display: none" onsubmit="return validateOffer()"
                      action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="courier_send_offer">
                    <label class="control-label">
                        <fmt:message key="label.choose_transport"/>
                    </label>
                    <c:forEach var="currentTransport" items="${transportList}">
                        <div class="row">
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input type="radio" class="form-check-input" name="transport" value="${currentTransport}">
                                    <fmt:message key="label.transport.${currentTransport}"/>
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                    <label class="control-label" id="errorTransport" style="display: none; color: red">
                        <fmt:message key="error.transport_goods"/>
                    </label>
                    <label class="control-label">
                        <fmt:message key="label.choose_transported_goods"/>
                    </label>
                    <c:forEach var="currentGoods" items="${goodsList}">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="checkbox" class="form-check-input" name="goods" value="${currentGoods}">
                                <fmt:message key="label.goods.${currentGoods}"/>
                            </label>
                        </div>
                    </c:forEach>
                    <button type="submit" class="btn btn-success">
                        <fmt:message key="button.send_offer"/>
                    </button>
                    <button type="button" class="btn btn-danger" id="cancelNewOffer">
                        <fmt:message key="button.cancel"/>
                    </button>
                </form>
            </div>
<!-- deals -->
            <div id="deals" class="tab-pane fade in ${deal}">
                <br><br>
                <div class="row">
                    <button class="btn btn-primary col-md-3" data-toggle="collapse" data-target="#youResponded">
                        <fmt:message key="label.you_responded"/>
                    </button>
                </div>
                <div class="collapse" id="youResponded">
                    <c:if test="${findClientOffer.size() == 0}">
                        <label class="control-label" style="color: orange">
                            <fmt:message key="error.search"/>
                        </label>
                    </c:if>
                    <c:if test="${findClientOffer.size() > 0}">
                        <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="courier_cancel_respond">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><fmt:message key="column.id"/></th>
                                    <th><fmt:message key="column.first_name"/></th>
                                    <th><fmt:message key="column.last_name"/></th>
                                    <th><fmt:message key="column.offer_info"/></th>
                                    <th><fmt:message key="label.cancel"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="currentClientOffer" items="${findClientOffer}">
                                    <tr>
                                        <td>${currentClientOffer.user.id}</td>
                                        <td>${currentClientOffer.user.firstName}</td>
                                        <td>${currentClientOffer.user.lastName}</td>
                                        <td>
                                            <c:forEach var="currentGoods" items="${currentClientOffer.goods}">
                                                <fmt:message key="label.goods.${currentGoods}"/>
                                            </c:forEach>
                                            ${currentClientOffer.comment}
                                            ${currentClientOffer.date}
                                            ${currentClientOffer.startTime} -
                                            ${currentClientOffer.endTime}
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-danger" name="cancel" value="${currentClientOffer.dealId}">
                                                <fmt:message key="button.cancel"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </c:if>
                </div>
                <div class="row">
                    <button class="btn btn-primary col-md-3" data-toggle="collapse" data-target="#respondedToYou">
                        <fmt:message key="label.responded_to_you"/>
                    </button>
                </div>
                <div class="collapse" id="respondedToYou">
                    <c:if test="${findResponded.size() == 0}">
                        <label class="control-label" style="color: orange">
                            <fmt:message key="error.search"/>
                        </label>
                    </c:if>
                    <c:if test="${findResponded.size() > 0}">
                        <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                            <input type="hidden" name="command" value="courier_processing_respond">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><fmt:message key="column.id"/></th>
                                    <th><fmt:message key="column.first_name"/></th>
                                    <th><fmt:message key="column.last_name"/></th>
                                    <th><fmt:message key="column.offer_info"/></th>
                                    <th><fmt:message key="column.process"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="currentRespond" items="${findResponded}">
                                    <tr>
                                        <td>${currentRespond.user.id}</td>
                                        <td>${currentRespond.user.firstName}</td>
                                        <td>${currentRespond.user.lastName}</td>
                                        <td>${currentRespond.comment}</td>
                                        <td>
                                            <button type="submit" class="btn btn-success" name="accept" value="${currentRespond.dealId}">
                                                <fmt:message key="button.accept"/>
                                            </button>
                                            <button type="submit" class="btn btn-danger" name="denied" value="${currentRespond.dealId}">
                                                <fmt:message key="button.denied"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </c:if>
                </div>
                <div class="row">
                    <button class="btn btn-primary col-md-3" data-toggle="collapse" data-target="#working">
                        <fmt:message key="label.working"/>
                    </button>
                </div>
                <div class="collapse" id="working">
                    <c:choose>
                        <c:when test="${findWorkingOfferClient.size() == 0 && findWorkingOfferCourier.size() == 0}">
                            <label class="control-label" style="color: orange">
                                <fmt:message key="error.search"/>
                            </label>
                        </c:when>
                        <c:otherwise>
                            <form name="WorkingDeal" action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                                <input type="hidden" name="command" value="courier_finish_deal">
                                <input type="hidden" name="declarer" id="declarer" value="">
                                <input type="hidden" name="dealId" id="dealId" value="">
                                <input type="hidden" name="courierId" id="courierId" value="">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="column.id"/></th>
                                        <th><fmt:message key="column.first_name"/></th>
                                        <th><fmt:message key="column.last_name"/></th>
                                        <th><fmt:message key="column.email"/></th>
                                        <th><fmt:message key="column.comment"/></th>
                                        <th><fmt:message key="label.finish"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="currentWorkingDeal" items="${findWorkingOfferClient}">
                                        <tr>
                                            <td>${currentWorkingDeal.user.id}</td>
                                            <td>${currentWorkingDeal.user.firstName}</td>
                                            <td>${currentWorkingDeal.user.lastName}</td>
                                            <td>${currentWorkingDeal.user.email}</td>
                                            <td>
                                                <c:forEach var="currentGoods" items="${currentWorkingDeal.goods}">
                                                    <fmt:message key="label.goods.${currentGoods}"/>
                                                </c:forEach>
                                                    ${currentWorkingDeal.comment}
                                                    ${currentWorkingDeal.date}
                                                    ${currentWorkingDeal.startTime}-
                                                    ${currentWorkingDeal.endTime}
                                            </td>
                                            <td>
                                                <button type="submit" class="btn btn-warning" name="finish"
                                                        onclick="ratingCourier('client', ${currentWorkingDeal.dealId}, ${currentWorkingDeal.user.id})">
                                                    <fmt:message key="button.finish"/>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:forEach var="currentWorkingDeal" items="${findWorkingOfferCourier}">
                                        <tr>
                                            <td>${currentWorkingDeal.user.id}</td>
                                            <td>${currentWorkingDeal.user.firstName}</td>
                                            <td>${currentWorkingDeal.user.lastName}</td>
                                            <td>${currentWorkingDeal.user.email}</td>
                                            <td>${currentWorkingDeal.comment}</td>
                                            <td>
                                                <button type="submit" class="btn btn-warning" name="finish"
                                                        onclick="ratingCourier('courier', ${currentWorkingDeal.dealId}, ${currentWorkingDeal.user.id})">
                                                    <fmt:message key="button.finish"/>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row">
                    <button data-target="#completed" class="btn btn-primary col-md-3" data-toggle="collapse" >
                        <fmt:message key="label.completed"/>
                    </button>
                </div>
                <div class="collapse" id="completed">
                    <c:choose>
                        <c:when test="${findCompletedOfferClient.size() == 0 && findCompletedOfferCourier.size() == 0}">
                            <label class="control-label" style="color: orange">
                                <fmt:message key="error.search"/>
                            </label>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th><fmt:message key="column.id"/></th>
                                    <th><fmt:message key="column.first_name"/></th>
                                    <th><fmt:message key="column.last_name"/></th>
                                    <th><fmt:message key="column.email"/></th>
                                    <th><fmt:message key="column.comment"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="currentCompletedDeal" items="${findCompletedOfferClient}">
                                    <tr>
                                        <td>${currentCompletedDeal.user.id}</td>
                                        <td>${currentCompletedDeal.user.firstName}</td>
                                        <td>${currentCompletedDeal.user.lastName}</td>
                                        <td>${currentCompletedDeal.user.email}</td>
                                        <td>
                                            <c:forEach var="currentGoods" items="${currentCompletedDeal.goods}">
                                                <fmt:message key="label.goods.${currentGoods}"/>
                                            </c:forEach>
                                                ${currentCompletedDeal.comment}
                                                ${currentCompletedDeal.date}
                                                ${currentCompletedDeal.startTime}-
                                                ${currentCompletedDeal.endTime}
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:forEach var="currentCompletedDeal" items="${findCompletedOfferCourier}">
                                    <tr>
                                        <td>${currentCompletedDeal.user.id}</td>
                                        <td>${currentCompletedDeal.user.firstName}</td>
                                        <td>${currentCompletedDeal.user.lastName}</td>
                                        <td>${currentCompletedDeal.user.email}</td>
                                        <td>${currentCompletedDeal.comment}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
<!-- find client -->
            <div id="client" class="tab-pane fade in ${client}">
                <c:if test="${clientList.size() == 0}">
                    <label class="control-label" style="color: orange">
                        <fmt:message key="error.search"/>
                    </label>
                </c:if>
                <c:if test="${clientList.size() > 0}">
                    <br><br>
                    <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                        <input type="hidden" name="command" value="courier_respond_client_offer">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th><fmt:message key="column.client"/></th>
                                <th><fmt:message key="column.offer_info"/></th>
                                <th><fmt:message key="column.process"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="currentOffer" items="${clientList}">
                                <tr>
                                    <td>
                                            ${currentOffer.user.id}
                                            ${currentOffer.user.firstName}
                                            ${currentOffer.user.lastName}
                                    </td>
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
                                        <c:choose>
                                            <c:when test="${isExist[currentOffer.id]}">
                                                <fmt:message key="label.already_respond"/>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary" name="respond" value="${currentOffer.id}">
                                                    <fmt:message key="button.respond"/>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function(){
            $("#changeUserData").click(function () {
                $("#infoPanel").hide();
                $("#changeUserData").hide();
                $("#changePanel").show();
                $("#buttonSave").show();
                $("#cancelChangeUserData").show();
                return false;
            });
        });
        $(document).ready(function(){
            $("#cancelChangeUserData").click(function () {
                $("#changePanel").hide();
                $("#buttonSave").hide();
                $("#cancelChangeUserData").hide();
                $("#infoPanel").show();
                $("#changeUserData").show();
                return false;
            });
        });
        $(document).ready(function(){
            $("#newOffer").click(function () {
                $("#offerForm").hide();
                $("#newOffer").hide();
                $("#newOfferForm").show();
                return false;
            });
        });
        $(document).ready(function(){
            $("#cancelNewOffer").click(function () {
                $("#offerForm").show();
                $("#newOffer").show();
                $("#newOfferForm").hide();
                return false;
            });
        });
        function ratingCourier(declarer, dealId, courierId) {
            $('input[name=declarer]').val(declarer);
            $('input[name=dealId]').val(dealId);
            $('input[name=courierId]').val(courierId);
        }
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
            if ((regularPassword.test(password) == false && password !="") || password != confirmPassword) {
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
        function validateOffer() {
            var transport = $('input[name=transport]:checked').val();
            var goods = $('input[name=goods]:checked').val();
            if (transport == undefined || goods == undefined) {
                $("#errorTransport").show();
                return false
            }
            return true;
        }
    </script>
</body>
</html>
