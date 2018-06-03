<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.orderTaxi" var="orderTaxi"/>
<fmt:message bundle="${locale}" key="text.buberWelcome" var="buberWelcome"/>

<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <div class="container text-top my-auto">
                <h1 class="mb-1">${buberWelcome}</h1>
                <form action="${pageContext.request.contextPath}/jsp/orderTaxi.jsp">
                    <input class="btn btn-lg btn-primary btn-block" type="submit" value="${orderTaxi}">
                </form>
            </div>
        </div>
    </div>
</div>
</div>

<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>
