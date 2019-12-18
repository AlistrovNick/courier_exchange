<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="info" uri="/WEB-INF/tld/info.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.pageContent"/>
<html>
<head>
    <title>Client</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<!-- nav tabs -->
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
            <a href="${pageContext.session.servletContext.contextPath}/Controller?command=client_show_offer">
                <fmt:message key="button.your_offers"/>
            </a>
        </li>
        <li class="${deal}">
            <a href="${pageContext.session.servletContext.contextPath}/Controller?command=client_show_deal">
                <fmt:message key="button.your_deals"/>
            </a>
        </li>
        <li class="${courier}">
            <a href="${pageContext.session.servletContext.contextPath}/Controller?command=client_show_courier_offer">
                <fmt:message key="button.find_courier"/>
            </a>
        </li>
    </ul>
    <!-- content -->
    <div class="tab-content">
        <!--cabinet -->
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
                <button type="button" class="btn btn-danger" name="cancel" id="cancelChangeUserData"
                        style="display: none">
                    <fmt:message key="button.cancel"/>
                </button>
            </form>
        </div>
<!-- offers -->
        <div id="offers" class="tab-pane fade in ${offers}">
            <br/>
            <form class="form-horizontal" id="offerForm"
                  action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="client_delete_offer">
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
                            <th><fmt:message key="label.offer_info"/></th>
                            <th><fmt:message key="label.status"/></th>
                            <th><fmt:message key="label.cancel"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="currentOffer" items="${findOffer}">
                            <tr>
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
                                    <fmt:message key="label.status.${currentOffer.status.status}"/>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-danger" name="cancel"
                                            value="${currentOffer.id}">
                                        <fmt:message key="button.cancel"/>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </form>
            <button type="button" class="btn btn-primary" name="change" id="newOffer">
                <fmt:message key="button.new_offer"/>
            </button>
            <form class="form-horizontal" id="newOfferForm" style="display: none" onsubmit="return validateOffer()"
                  action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                <input type="hidden" name="command" value="client_send_offer">
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
                <div>
                    <label class="control-label">
                        <fmt:message key="label.comment"/>
                    </label>
                </div>
                <div class="form-group">
                    <div class="col-md-3">
                            <textarea class="form-control" rows="5" cols="100" name="comment"
                                      placeholder="<fmt:message key="label.enter_comment"/>"></textarea>
                    </div>
                    <label class="control-label" id="errorComment" style="display: none; color: red">
                        <fmt:message key="error.comment"/>
                    </label>
                </div>
                <label class="control-label">
                    <fmt:message key="label.choose_date"/>
                </label>
                <div class="form-group">
                    <div class="col-md-3">
                        <input type="date" class="form-control" name="startTerm" min="${today}" max="${lastDay}">
                    </div>
                </div>
                <label class="control-label">
                    <fmt:message key="label.choose_time"/>
                </label>
                <br>
                <div class="form-group">
                    <label class="control-label col-md-2">
                        <fmt:message key="label.choose_time.starting_at"/>
                    </label>
                    <div class="col-md-2">
                        <input type="time" name="startTime" class="form-control">
                    </div>
                    <label class="control-label col-md-2">
                        <fmt:message key="label.choose_time.ending_in"/>
                    </label>
                    <div class="col-md-2">
                        <input type="time" name="endTime" class="form-control">
                    </div>
                </div>
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
                <c:if test="${findCourierOffer.size() == 0}">
                    <label class="control-label" style="color: orange">
                        <fmt:message key="error.search"/>
                    </label>
                </c:if>
                <c:if test="${findCourierOffer.size() > 0}">
                    <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller"
                          method="post">
                        <input type="hidden" name="command" value="client_cancel_respond">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th><fmt:message key="label.rating"/></th>
                                <th><fmt:message key="column.id"/></th>
                                <th><fmt:message key="column.first_name"/></th>
                                <th><fmt:message key="column.last_name"/></th>
                                <th><fmt:message key="column.comment"/></th>
                                <th><fmt:message key="label.cancel"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="currentCourierOffer" items="${findCourierOffer}">
                                <tr>
                                    <td>${courierRating[currentCourierOffer.user.id]}</td>
                                    <td>${currentCourierOffer.user.id}</td>
                                    <td>${currentCourierOffer.user.firstName}</td>
                                    <td>${currentCourierOffer.user.lastName}</td>
                                    <td>${currentCourierOffer.comment}</td>
                                    <td>
                                        <button type="submit" class="btn btn-danger" name="cancel"
                                                value="${currentCourierOffer.dealId}">
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
                <c:if test="${offerMap.size() == 0}">
                    <label class="control-label" style="color: orange">
                        <fmt:message key="error.search"/>
                    </label>
                </c:if>
                <c:if test="${offerMap.size() > 0}">
<!--responded to you-->
                    <form class="form-horizontal" action="${pageContext.session.servletContext.contextPath}/Controller"
                          method="post">
                        <input type="hidden" name="command" value="client_processing_respond">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th><fmt:message key="column.id"/></th>
                                <th><fmt:message key="column.offer_info"/></th>
                                <th><fmt:message key="column.rating"/></th>
                                <th><fmt:message key="column.courier"/></th>
                                <th><fmt:message key="column.process"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="currentOffer" items="${offerMap}">
                                <tr>
                                    <td>${currentOffer.key}</td>
                                    <td>
                                        <c:forEach var="currentGoods" items="${currentOffer.value.goods}">
                                            <fmt:message key="label.goods.${currentGoods}"/>
                                        </c:forEach>
                                            ${currentOffer.value.comment}
                                            ${currentOffer.value.date}
                                            ${currentOffer.value.startTime} -
                                            ${currentOffer.value.endTime}
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <c:forEach var="currentCourier" items="${courierMap[currentOffer.key]}">
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td>
                                                ${courierRating[currentCourier.id]}
                                        </td>
                                        <td>
                                                ${currentCourier.id}
                                                ${currentCourier.firstName}
                                                ${currentCourier.lastName}
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-success" name="accept"
                                                    value="${currentOffer.value.dealId} ${currentOffer.value.id}">
                                                <fmt:message key="button.accept"/>
                                            </button>
                                            <button type="submit" class="btn btn-danger" name="denied"
                                                    value="${currentOffer.value.dealId}">
                                                <fmt:message key="button.denied"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </c:if>
            </div>
<!-- working -->
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
                        <form name="WorkingDeal" action="${pageContext.session.servletContext.contextPath}/Controller"
                              method="post">
                            <input type="hidden" name="command" value="client_finish_deal">
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
                <button data-target="#completed" class="btn btn-primary col-md-3" data-toggle="collapse">
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
                                <th><fmt:message key="column.rating"/></th>
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
                                    <td>
                                        <c:if test="${ratingClientMap[currentCompletedDeal.dealId] == 0}">
                                            <button type="button" class="btn btn-warning" name="rating"
                                                    data-toggle="modal" data-target="#ratingModalForm"
                                                    onclick="sendRating('client', ${currentCompletedDeal.dealId})">
                                                <fmt:message key="button.rating"/>
                                            </button>
                                        </c:if>
                                        <c:if test="${ratingClientMap[currentCompletedDeal.dealId] > 0}">
                                            <label class="control-label">
                                                <fmt:message key="label.you_already_rating"/>
                                            </label>
                                        </c:if>
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
                                    <td>
                                        <c:if test="${ratingCourierMap[currentCompletedDeal.dealId] == 0}">
                                            <button type="button" class="btn btn-warning" name="rating"
                                                    data-toggle="modal" data-target="#ratingModalForm"
                                                    onclick="sendRating('courier', ${currentCompletedDeal.dealId})">
                                                <fmt:message key="button.rating"/>
                                            </button>
                                        </c:if>
                                        <c:if test="${ratingCourierMap[currentCompletedDeal.dealId] > 0}">
                                            <label class="control-label">
                                                <fmt:message key="label.you_already_rating"/>
                                            </label>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
                <div id="ratingModalForm" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-body">
                                <form class="form-horizontal"
                                      action="${pageContext.session.servletContext.contextPath}/Controller"
                                      method="post">
                                    <input type="hidden" name="command" value="client_rating_courier">
                                    <input type="hidden" name="finishDeclarer" id="finishDeclarer" value="">
                                    <input type="hidden" name="finishDealId" id="finishDealId" value="">
                                    <label class="control-label">
                                        <fmt:message key="label.rating_courier"/>
                                    </label>
                                    <input type="number" name="rating" class="form-control" min="1" max="5" required>
                                    <button type="submit" class="btn btn-success">
                                        <fmt:message key="button.rating"/>
                                    </button>
                                    <button class="btn btn-danger" data-dismiss="modal">
                                        <fmt:message key="button.cancel"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<!-- find courier -->
        <div id="courier" class="tab-pane fade in ${courier}">
            <c:if test="${courierList.size() == 0}">
                <label class="control-label" style="color: orange">
                    <fmt:message key="error.search"/>
                </label>
            </c:if>
            <c:if test="${courierList.size() > 0}">
                <form id="formSendRespond" class="form-horizontal" style="display: none"
                      action="${pageContext.session.servletContext.contextPath}/Controller" method="post">
                    <input type="hidden" name="command" value="client_send_respond">
                    <input type="hidden" name="offerId" id="offerId" value="">
                    <br><br>
                    <label class="control-label">
                        <fmt:message key="label.enter_comment"/>
                    </label>
                    <div class="row">
                        <div class="col-md-3">
                            <textarea name="comment" class="form-control" rows="5"></textarea>
                        </div>
                        <label class="control-label" style="display: none; color: red">
                            <fmt:message key="error.comment"/>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-success" name="respond">
                        <fmt:message key="button.respond"/>
                    </button>
                    <button type="button" class="btn btn-danger" id="cancelSendRespond">
                        <fmt:message key="button.cancel"/>
                    </button>
                </form>
                <table class="table table-striped" id="tableCourierOffer">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.rating"/></th>
                        <th><fmt:message key="label.courier"/></th>
                        <th><fmt:message key="label.transport"/></th>
                        <th><fmt:message key="label.goods"/></th>
                        <th><fmt:message key="label.respond"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="currentOffer" items="${courierList}">
                        <tr>
                            <td>
                                ${courierRating[currentOffer.user.id]}
                            </td>
                            <td>
                                    ${currentOffer.user.id}
                                    ${currentOffer.user.firstName}
                                    ${currentOffer.user.lastName}
                            </td>
                            <td>
                                <fmt:message key="label.transport.${currentOffer.transport}"/>
                            </td>
                            <td>
                                <c:forEach var="currentGoods" items="${currentOffer.goods}">
                                    <fmt:message key="label.goods.${currentGoods}"/>
                                </c:forEach>
                            </td>
                            <td>
                                <button type="button" id="but" class="btn btn-primary"
                                        onclick="getOfferId(${currentOffer.id})">
                                    <fmt:message key="button.respond"/>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#changeUserData").click(function () {
            $("#infoPanel").hide();
            $("#changeUserData").hide();
            $("#changePanel").show();
            $("#buttonSave").show();
            $("#cancelChangeUserData").show();
            return false;
        });
    });
    $(document).ready(function () {
        $("#cancelChangeUserData").click(function () {
            $("#changePanel").hide();
            $("#buttonSave").hide();
            $("#cancelChangeUserData").hide();
            $("#infoPanel").show();
            $("#changeUserData").show();
            return false;
        });
    });
    $(document).ready(function () {
        $("#newOffer").click(function () {
            $("#offerForm").hide();
            $("#newOffer").hide();
            $("#newOfferForm").show();
            return false;
        });
    });
    $(document).ready(function () {
        $("#cancelNewOffer").click(function () {
            $("#offerForm").show();
            $("#newOffer").show();
            $("#newOfferForm").hide();
            return false;
        });
    });
    $(document).ready(function () {
        $("#cancelSendRespond").click(function () {
            $("#formSendRespond").hide();
            $("#tableCourierOffer").show();
        });
    });

    function sendRating(declarer, dealId) {
        $('input[name=finishDeclarer]').val(declarer);
        $('input[name=finishDealId]').val(dealId);
    }

    function ratingCourier(declarer, dealId, courierId) {
        $('input[name=declarer]').val(declarer);
        $('input[name=dealId]').val(dealId);
        $('input[name=courierId]').val(courierId);
    }

    function getOfferId(offerId) {
        $("#tableCourierOffer").hide();
        $("#formSendRespond").show();
        $("#offerId").val(offerId);
        return offerId;
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

    function validateOffer() {
        var comment = $('textarea[name=comment]').val();
        if (comment == "") {
            $("#errorComment").show();
            return false
        }
        return true;
    }
</script>
</body>
</html>
