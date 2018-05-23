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
<fmt:message bundle="${locale}" key="text.admin.console" var="console"/>

<html>
<head>
    <title>${buber}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>

<nav class="navbar navbar-toggleable-md navbar-inverse bg-inverse">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-brand">${buber}</div>

    <div class="collapse navbar-collapse">
        <c:if test="${not empty sessionScope.login}">
            <ul class="navbar-nav mr-auto">
                <form action="${ pageContext.request.contextPath }/controller" method="post">
                    <input type="hidden" name="command" value="sign-out">
                    <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="${signOut}">
                </form>
                <form action="${pageContext.request.contextPath}/jsp/orderTaxi.jsp">
                    <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="${orderTaxi}">
                </form>
                <form action="${pageContext.request.contextPath}/jsp/fillUpBalance.jsp">
                    <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="${fillUp}">
                </form>
                <c:if test="${sessionScope.type == 'ADMIN' || sessionScope.type == 'ROOT_ADMIN'}">
                    <form action="${pageContext.request.contextPath}/jsp/admin.jsp">
                        <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="${console}">
                    </form>
                </c:if>
            </ul>
        </c:if>
        <form action="${ pageContext.request.contextPath }/controller" method="post">
            <input type="hidden" name="command" value="lang">
            <input type="hidden" name="lang" value="ru">
            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="RU">
        </form>
        <form action="${ pageContext.request.contextPath }/controller" method="post">
            <input type="hidden" name="command" value="lang">
            <input type="hidden" name="lang" value="en">
            <input class="btn btn-outline-success my-2 my-sm-0" type="submit" value="EN">
        </form>
    </div>
</nav>

</html>
