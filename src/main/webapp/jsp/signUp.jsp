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
<fmt:message bundle="${locale}" key="text.lastName" var="secondName"/>
<fmt:message bundle="${locale}" key="text.birthDay" var="birthDay"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.allCorrectMessage" var="allCorrectMessage"/>

<fmt:message bundle="${locale}" key="text.notValidLogin" var="notValidLogin"/>
<fmt:message bundle="${locale}" key="text.loginExist" var="loginExist"/>
<fmt:message bundle="${locale}" key="text.phoneNumberExist" var="phoneNumberExist"/>
<fmt:message bundle="${locale}" key="text.firstNameNotValid" var="firstNameNotValid"/>
<fmt:message bundle="${locale}" key="text.lastNameNotValid" var="lastNameNotValid"/>
<fmt:message bundle="${locale}" key="text.notValidPassword" var="notValidPassword"/>
<fmt:message bundle="${locale}" key="text.passwordNotEq" var="passwordNotEq"/>
<fmt:message bundle="${locale}" key="text.birthDayErrorNotValid" var="birthDayNotValid"/>
<fmt:message bundle="${locale}" key="text.phoneNumberNotValid" var="notValidPhoneNumber"/>


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
            <input type="password" class="form-control" id="repeat-password" placeholder="${repeatPassword}"/>
            <input type="text" class="form-control" id="first-name" placeholder="${firstName}"/>
            <input type="text" class="form-control" id="last-name" placeholder="${secondName}"/>
            <input type="date" class="form-control" id="birth-day" placeholder="${birthDay}">
            <input type="text" class="form-control" id="phone-number" placeholder="${phoneNumber}"/>
            <div>
                <input class="btn btn-lg btn-primary btn-block" type="submit" id="sign-up" value="${signUp}">
            </div>

            <div class="error" id="not-valid-login">
                <p>${notValidLogin}</p>
            </div>

            <div class="error" id="login-exist">
                <p>${loginExist}</p>
            </div>

            <div class="error" id="phone-number-exist">
                <p>${phoneNumberExist}</p>
            </div>

            <div class="error" id="first-name-not-valid">
                <p>${firstNameNotValid}</p>
            </div>

            <div class="error" id="last-name-not-valid">
                <p>${lastNameNotValid}</p>
            </div>

            <div class="error" id="not-valid-password">
                <p>${notValidPassword}</p>
            </div>

            <div class="error" id="password-not-eq">
                <p>${passwordNotEq}</p>
            </div>

            <div class="error" id="birth-day-not-valid">
                <p>${birthDayNotValid}</p>
            </div>

            <div class="error" id="not-valid-phone-number">
                <p>${notValidPhoneNumber}</p>
            </div>

            <div class="correct" id="all-correct">
                <p>${allCorrectMessage}</p>
            </div>

        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>

