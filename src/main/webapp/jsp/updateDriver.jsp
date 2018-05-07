<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.signUp" var="signUp"/>
<fmt:message bundle="${locale}" key="text.password" var="password"/>
<fmt:message bundle="${locale}" key="text.repeatPassword" var="repeatPassword"/>
<fmt:message bundle="${locale}" key="text.firstName" var="firstName"/>
<fmt:message bundle="${locale}" key="text.secondName" var="secondName"/>
<fmt:message bundle="${locale}" key="text.birthDay" var="age"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.allCorrectMessage" var="allCorrectMessage"/>

<fmt:message bundle="${locale}" key="text.error.notValidLoginErrorMessage" var="notValidLoginErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.loginExistErrorMessage" var="loginExistErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.phoneNumberExistErrorMessage" var="phoneNumberExistErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.firstNameErrorMessage" var="firstNameErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.secondNameErrorMessage" var="secondNameErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.notValidPasswordErrorMessage" var="notValidPasswordErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.passwordNotEqMessage" var="passwordNotEqMessage"/>
<fmt:message bundle="${locale}" key="text.error.birthDayErrorMessage" var="birthDayErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.notValidPhoneNumberErrorMessage" var="notValidPhoneNumberErrorMessage"/>


<html>
<head>
    <title>${signUp}</title>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script src="/js/signUpDriver.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/signUp.css">
</head>
<body>
<form action="${ pageContext.request.contextPath }/controller" method="post">


    <input type="hidden" name="command" value="sign-up-driver">
</form>
<c:choose>
    <c:when test="${notFound}">
        notFound
    </c:when>
    <c:otherwise>
        <p>${driver.login}</p>
        <p>${carNumber}<input type="text" id="carNumber" value="7522AT1"/></p>
        <p>${documentId}<input type="text" id="documentId" value="7AD123132"/></p>
        <p>${carMark}<input type="text" id="carMark"/></p>
        <p><input type="submit" id="sign-up-driver" value="${signUp}"></p>

        <div class="error" id="car-number-error">
            <p>carNumberError</p>
        </div>

        <div class="error" id="document-id-error">
            <p>documentIdError</p>
        </div>

        <div class="error" id="car-mark-error">
            <p>carMarkError</p>
        </div>

        <div class="error" id="login-not-exist-error">
            <p>loginNotExistError</p>
        </div>

        <div class="error" id="driver-exist-error">
            <p>driverExistError</p>
        </div>

        <div class="error" id="car-number-exist-error">
            <p>carNumberExistError</p>
        </div>

        <div class="error" id="document-id-exist-error">
            <p>documentIdExistError</p>
        </div>

        <div class="correct" id="all-correct">
            <p>allCorrectMessage</p>
        </div>
    </c:otherwise>
</c:choose>

<ctg:hello auth="Alex Zhuk" description="Created for EPAM-System java traning"/>
</body>
</html>

