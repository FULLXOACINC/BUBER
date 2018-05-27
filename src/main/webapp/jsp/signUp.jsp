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
<fmt:message bundle="${locale}" key="text.birthDay" var="birthDay"/>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/signUp.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<div class="back">
    <div class="container">
        <div class="form-input">
            <h2 class="form-input-heading">${signUp}</h2>
            <input type="text" id="login" class="form-control" placeholder="${login}"/>
            <input type="password" class="form-control" id="password" placeholder="${password}"/>
            <input type="password" class="form-control" id="repeatPassword" placeholder="${repeatPassword}"/>
            <input type="text" class="form-control" id="firstName" placeholder="${firstName}"/>
            <input type="text" class="form-control" id="secondName" placeholder="${secondName}"/>
            <input type="date" class="form-control" id="birthDay" placeholder="${birthDay}">
            <input type="text" class="form-control" id="phoneNumber" placeholder="${phoneNumber}"/>
            <div>
                <input class="btn btn-lg btn-primary btn-block" type="submit" id="sign-up" value="${signUp}">
            </div>

            <div class="error" id="not-valid-login-error">
                <p>${notValidLoginErrorMessage}</p>
            </div>

            <div class="error" id="login-exist-error">
                <p>${loginExistErrorMessage}</p>
            </div>

            <div class="error" id="phone-number-exist-error">
                <p>${phoneNumberExistErrorMessage}</p>
            </div>

            <div class="error" id="first-name-error">
                <p>${firstNameErrorMessage}</p>
            </div>

            <div class="error" id="second-name-error">
                <p>${secondNameErrorMessage}</p>
            </div>

            <div class="error" id="not-valid-password-error">
                <p>${notValidPasswordErrorMessage}</p>
            </div>

            <div class="error" id="password-not-eq-error">
                <p>${passwordNotEqMessage}</p>
            </div>

            <div class="error" id="birth-day-error">
                <p>${birthDayErrorMessage}</p>
            </div>

            <div class="error" id="not-valid-phone-number-error">
                <p>${notValidPhoneNumberErrorMessage}</p>
            </div>

            <div class="correct" id="all-correct">
                <p>${allCorrectMessage}</p>
            </div>

        </div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM-System java traning"/>
</body>
</html>

