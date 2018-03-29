<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.singup" var="singup"/>
<fmt:message bundle="${locale}" key="text.yandex" var="yandex"/>
<fmt:message bundle="${locale}" key="text.password" var="password"/>
<fmt:message bundle="${locale}" key="text.loginWith" var="loginWith"/>
<fmt:message bundle="${locale}" key="text.enter" var="enter"/>
<fmt:message bundle="${locale}" key="text.errors.loginNotValid" var="loginNotValidMessage"/>
<fmt:message bundle="${locale}" key="text.errors.loginNotExist" var="loginNotExistMessage"/>
<fmt:message bundle="${locale}" key="text.errors.loginPasswordNotEq" var="loginPasswordNotEqMessage"/>
<fmt:message bundle="${locale}" key="text.errors.bannedError" var="bannedErrorMessage"/>
<html>
<head>
    <title>${login}</title>
</head>
<body>
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <p>${login}<input type="text" name="login"/></p>
    <p>${password}<input type="password" name="password"/></p>
    <input type="hidden" name="command" value="signin">
    <input type="submit" value="${enter}">
</form>

<c:if test="${signInValidError}">
    ${loginNotValidMessage}
</c:if>
<c:if test="${signInExistError}">
    ${loginNotExistMessage}
</c:if>
<c:if test="${signInPasswordError}">
    ${loginPasswordNotEqMessage}
</c:if>
<c:if test="${bannedError}">
    ${bannedErrorMessage}
</c:if>
<br/>

<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="singup">
    <input type="submit" value="${singup}">
</form>
<hr/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="oauth">
    <input type="hidden" name="loginType" value="yandex">
    <input type="submit" value="${yandex}">
</form>
<ctg:hello auth="Alex Zhuk" description="Created fo EPAM System java traning"/>
</body>
</html>
