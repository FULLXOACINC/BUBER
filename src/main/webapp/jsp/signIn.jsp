<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.signUp" var="singUp"/>
<fmt:message bundle="${locale}" key="text.yandex" var="yandex"/>
<fmt:message bundle="${locale}" key="text.google" var="google"/>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/signIn.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <input type="radio" name="lang" value="ru"> ru<br/>
    <input type="radio" name="lang" value="en"> en<br/>

    <input type="hidden" name="command" value="lang">
    <input type="submit" value="${lang}">
</form>
<p>${login}<input type="text" id="login"/></p>
<p>${password}<input type="password" id="password"/></p>
<input type="submit" id="enter" value="${enter}">


<div class="error" id="login-not-valid-error">
    <p>${loginNotValidMessage}</p>
</div>
<div class="error" id="login-not-exist-error">
    <p>${loginNotExistMessage}</p>
</div>
<div class="error" id="login-password-not-eq-error">
    <p>${loginPasswordNotEqMessage}</p>
</div>

<c:if test="${bannedError}">
    <div id="banned-error">
        <p>${bannedErrorMessage}</p>
    </div>
</c:if>


<form action="${pageContext.request.contextPath}/jsp/signUp.jsp">
    <input type="submit" value="${singUp}">
</form>
<hr/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="oauth">
    <input type="hidden" name="loginType" value="yandex">
    <input type="submit" value="${yandex}">
</form>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="oauth">
    <input type="hidden" name="loginType" value="google">
    <input type="submit" value="${google}">
</form>
<ctg:hello auth="Alex Zhuk" description="Created fo EPAM System java traning"/>
</body>
</html>
