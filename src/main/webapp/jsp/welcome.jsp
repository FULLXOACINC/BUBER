<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.admin.console" var="console"/>

<html>
<head>
    <title>${buber}</title>
</head>
<body>
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <input type="radio" name="lang" value="ru"> ru<br/>
    <input type="radio" name="lang" value="en"> en<br/>

    <input type="hidden" name="command" value="lang">
    <input type="submit" value="${lang}">
</form>
${sessionScope.login}
${sessionScope.type}
<c:if test="${sessionScope.type == 'ADMIN' || sessionScope.type == 'ROOT_ADMIN'}">
    <form action="/jsp/admin.jsp" method="post">
        <input type="submit" value="${console}">
    </form>
</c:if>
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <input type="hidden" name="command" value="sign-out">
    <input type="submit" value="${signOut}">
</form>
</html>
