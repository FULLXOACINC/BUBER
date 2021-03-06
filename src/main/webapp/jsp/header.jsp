<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.orderTaxi" var="orderTaxi"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.fillUp" var="fillUp"/>
<fmt:message bundle="${locale}" key="text.userMenu" var="userMenu"/>
<fmt:message bundle="${locale}" key="text.adminMenu" var="adminMenu"/>
<fmt:message bundle="${locale}" key="text.driverMenu" var="driverMenu"/>
<fmt:message bundle="${locale}" key="text.changeCurrentCoordinate" var="changeCurrentCoordinate"/>
<fmt:message bundle="${locale}" key="text.signUpDriver" var="signUpDriver"/>
<fmt:message bundle="${locale}" key="text.viewUnacceptedComplaints" var="viewUnacceptedComplaints"/>
<fmt:message bundle="${locale}" key="text.findUsers" var="findUsers"/>
<fmt:message bundle="${locale}" key="text.currentRide" var="currentRide"/>
<fmt:message bundle="${locale}" key="text.signInHeading" var="signIn"/>
<fmt:message bundle="${locale}" key="text.profile" var="profile"/>
<fmt:message bundle="${locale}" key="text.rideHistory" var="rideHistory"/>

<fmt:message bundle="${locale}" key="text.serverError" var="serverError"/>
<fmt:message bundle="${locale}" key="text.connectionError" var="connectionError"/>

<fmt:message bundle="${locale}" key="text.withdrawEarnings" var="withdrawEarnings"/>

<fmt:message bundle="${locale}" key="text.setStatusWorking" var="setStatusWorking"/>
<fmt:message bundle="${locale}" key="text.setStatusNotWorking" var="setStatusNotWorking"/>
<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>
<nav class="navbar navbar-toggleable-md navbar-inverse bg-inverse">
    <div class="navbar-brand">${buber}</div>

    <div class="collapse navbar-collapse">
        <c:choose>
            <c:when test="${not empty sessionScope.login}">
                <ul class="navbar-nav mr-auto">
                    <div class="dropdown">

                        <button class="btn btn-outline-success my-2 my-sm-0 " id="user"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${userMenu}</button>

                        <div class="dropdown-menu" aria-labelledby="user">
                            <div>
                                <form action="${pageContext.request.contextPath}/jsp/fillUpBalance.jsp">
                                    <input class="dropdown-item" type="submit" value="${fillUp}">
                                </form>
                            </div>
                            <div>
                                <form action="${pageContext.request.contextPath}/jsp/userRideHistory.jsp">
                                    <input class="dropdown-item" type="submit" value="${rideHistory}">
                                </form>
                            </div>
                            <div>
                                <form action="${pageContext.request.contextPath}/jsp/orderTaxi.jsp">
                                    <input class="dropdown-item" type="submit" value="${orderTaxi}">
                                </form>
                            </div>
                            <div>
                                <form action="${pageContext.request.contextPath}/jsp/currentUserRide.jsp">
                                    <input class="dropdown-item" type="submit" value="${currentRide}">
                                </form>
                            </div>
                            <div>
                                <form action="${ pageContext.request.contextPath }/Controller">
                                    <input type="hidden" name="command" value="find-user-profile">
                                    <input class="dropdown-item" type="submit" value="${profile}">
                                </form>
                            </div>
                            <div>
                                <form action="${ pageContext.request.contextPath }/Controller" method="post">
                                    <input type="hidden" name="command" value="sign-out">
                                    <input class="dropdown-item" type="submit" value="${signOut}">
                                </form>
                            </div>
                        </div>
                    </div>
                    <c:if test="${sessionScope.type == 'ADMIN' || sessionScope.type == 'ROOT_ADMIN'}">
                        <div class="dropdown">

                            <button class="btn btn-outline-success my-2 my-sm-0 " id="admin"
                                    data-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false">${adminMenu}</button>

                            <div class="dropdown-menu" aria-labelledby="admin">
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/signUpDriver.jsp">
                                        <input class="dropdown-item" type="submit" value="${signUpDriver}">
                                    </form>
                                </div>
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/complaints.jsp">
                                        <input class="dropdown-item" type="submit" value="${viewUnacceptedComplaints}">
                                    </form>
                                </div>
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/findUsers.jsp">
                                        <input class="dropdown-item" type="submit" value="${findUsers}">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.type == 'DRIVER'}">
                        <div class="dropdown">

                            <button class="btn btn-outline-success my-2 my-sm-0 " id="driver"
                                    data-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false">${driverMenu}</button>

                            <div class="dropdown-menu" aria-labelledby="driver">
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/changeDriverCoordinate.jsp">
                                        <input class="dropdown-item" type="submit" value="${changeCurrentCoordinate}">
                                    </form>
                                </div>
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/withdrawEarnings.jsp">
                                        <input class="dropdown-item" type="submit" value="${withdrawEarnings}">
                                    </form>
                                </div>
                                <div>
                                    <form action="${ pageContext.request.contextPath }/Controller">
                                        <input type="hidden" name="command" value="find-driver-profile">
                                        <input class="dropdown-item" type="submit" value="${profile}">
                                    </form>
                                </div>
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/driverRideHistory.jsp">
                                        <input class="dropdown-item" type="submit" value="${rideHistory}">
                                    </form>
                                </div>
                                <div>
                                    <form action="${pageContext.request.contextPath}/jsp/currentDriverRide.jsp">
                                        <input class="dropdown-item" type="submit" value="${currentRide}">
                                    </form>
                                </div>
                                <div>
                                    <input class="dropdown-item" type="submit" id="is-working"
                                           value="${setStatusWorking}">
                                </div>
                                <div>
                                    <input class="dropdown-item" type="submit" id="is-not-working"
                                           value="${setStatusNotWorking}">
                                </div>


                            </div>
                        </div>
                    </c:if>


                </ul>
            </c:when>
            <c:otherwise>
                <ul class="navbar-nav mr-auto">
                    <form action="${pageContext.request.contextPath}/jsp/signIn.jsp">
                        <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="${signIn}">
                    </form>
                </ul>
            </c:otherwise>
        </c:choose>


        <form action="${ pageContext.request.contextPath }/Controller" method="post">
            <input type="hidden" name="command" value="lang">
            <input type="hidden" name="lang" value="ru">
            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="RU">
        </form>
        <form action="${ pageContext.request.contextPath }/Controller" method="post">
            <input type="hidden" name="command" value="lang">
            <input type="hidden" name="lang" value="en">
            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="EN">
        </form>
    </div>

</nav>
<div class="error" id="server-error">
    ${serverError}
</div>
<div class="error" id="connection-error">
    ${connectionError}
</div>
</body>
</html>
