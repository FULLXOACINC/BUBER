<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.yandex" var="yandex"/>
<fmt:message bundle="${locale}" key="text.password" var="password"/>
<fmt:message bundle="${locale}" key="text.loginWith" var="loginWith"/>
<fmt:message bundle="${locale}" key="text.enter" var="enter"/>
<html>
<head>
    <title>${login}</title>
</head>
<body>
<form action="${ pageContext.request.contextPath }/controller">
    <p>${login}<input type="text" name="login"/></p>
    <p>${password}<input type="password" name="password"/></p>
    <input type="submit" value="${enter}">
</form>

<br/>
<hr/>

<form action="${ pageContext.request.contextPath }/controller">
    <input type="hidden" name="command" value="oauth">
    <input type="hidden" name="loginType" value="yandex">
    <input type="submit" value="${yandex}">
</form>

</body>
</html>
