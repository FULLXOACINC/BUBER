<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>

<html>
<head>
    <title>${buber}</title>
</head>
<body>
${sessionScope.login}
${sessionScope.type}
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <input type="hidden" name="command" value="sign-out">
    <input type="submit" value="${signOut}">
</form>
</html>
