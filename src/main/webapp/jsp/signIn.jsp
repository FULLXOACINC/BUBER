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
<fmt:message bundle="${locale}" key="text.signInHeading" var="signInHeading"/>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>

<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<div class="back">

    <div class="container">

        <div class="form-signin">
            <h2 class="form-signin-heading">${signInHeading}</h2>
            <input type="text" id="login" class="form-control" placeholder="${login}">

            <input type="password" id="password" class="form-control" placeholder="${password}">
            <div>
                <input class="btn btn-lg btn-primary btn-block" type="submit" id="enter" value="${enter}">
            </div>

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
                <div class="error" id="banned-error">
                    <p>${bannedErrorMessage}</p>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="oauth">
                <input type="hidden" name="loginType" value="google">
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="${google}">
            </form>
            <form action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="oauth">
                <input type="hidden" name="loginType" value="yandex">
                <input type="submit" class="btn btn-lg btn-primary btn-block" value="${yandex}">
            </form>
            <form action="${pageContext.request.contextPath}/jsp/signUp.jsp">
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="${singUp}">
            </form>
        </div>


    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>

</body>
</html>
