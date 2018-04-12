<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.admin.find" var="find"/>
<fmt:message bundle="${locale}" key="text.admin.ban" var="ban"/>
<fmt:message bundle="${locale}" key="text.admin.unBan" var="unBan"/>

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
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <input type="text" name="pattern" value="">
    <input type="hidden" name="command" value="find-users">
    <input type="submit" value="${find}">
</form>
<c:forEach items="${users}" var="user">
    <c:out value="${user.login}" escapeXml="true"/>
    <c:out value="${user.firstName}" escapeXml="true"/>
    <c:out value="${user.lastName}" escapeXml="true"/>
    <c:out value="${user.phoneNumber}" escapeXml="true"/>

    <c:if test="${user.type != 'ADMIN'}">
        <c:if test="${!user.isBaned()}">
            <form action="${ pageContext.request.contextPath }/controller" method="post">
                <input type="hidden" name="command" value="user-ban">
                <input type="hidden" name="user" value="${user.login}">
                <input type="submit" value="${ban}">
            </form>
        </c:if>
        <c:if test="${user.isBaned()}">
            <form action="${ pageContext.request.contextPath }/controller" method="post">
                <input type="hidden" name="command" value="user-ban">
                <input type="hidden" name="user" value="${user.login}">
                <input type="submit" value="${unBan}">
            </form>
        </c:if>
    </c:if>
    <hr/>
</c:forEach>

<form action="${ pageContext.request.contextPath }/controller" method="post">
    <input type="hidden" name="command" value="sign-out">
    <input type="submit" value="${signOut}">
</form>
</body>
</html>