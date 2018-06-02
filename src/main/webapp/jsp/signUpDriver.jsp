<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.signUp" var="signUp"/>
<fmt:message bundle="${locale}" key="text.carNumber" var="carNumber"/>
<fmt:message bundle="${locale}" key="text.documentId" var="documentId"/>
<fmt:message bundle="${locale}" key="text.carMark" var="carMark"/>
<fmt:message bundle="${locale}" key="text.tariff" var="tariff"/>

<fmt:message bundle="${locale}" key="text.driverCorrectMessage" var="driverSignUpCorrectMessage"/>

<fmt:message bundle="${locale}" key="text.error.carNumberNotValid" var="carNumberNotValid"/>
<fmt:message bundle="${locale}" key="text.documentIdNotValid" var="documentIdNotValid"/>
<fmt:message bundle="${locale}" key="text.carMarkNotValid" var="carMarkNotValid"/>
<fmt:message bundle="${locale}" key="text.driverExist" var="driverExist"/>
<fmt:message bundle="${locale}" key="text.loginNotExist" var="loginNotExist"/>
<fmt:message bundle="${locale}" key="text.carNumberExist" var="carNumberExist"/>
<fmt:message bundle="${locale}" key="text.documentIdExist" var="documentIdExist"/>
<fmt:message bundle="${locale}" key="text.tariffNotValid" var="tariffNotValid"/>


<html>
<head>
    <title>${signUp}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/signUpDriver.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<div class="back">
    <div class="container">
        <div class="form-input">

            <input type="text" class="form-control" id="login" placeholder="${login}"/>
            <input type="text" class="form-control" id="car-number" value="7522AT1" placeholder="${carNumber}"/>
            <input type="text" class="form-control" id="document-id" value="7AD123132" placeholder="${documentId}"/>
            <input type="text" class="form-control" id="car-mark" placeholder="${carMark}"/>
            <input type="text" class="form-control" id="tariff" placeholder="${tariff}"/>

            <input type="submit" class="btn btn-lg btn-primary btn-block " id="sign-up-driver" value="${signUp}">

            <div class="error" id="car-number-not-valid">
                <p>${carNumberNotValid}</p>
            </div>

            <div class="error" id="document-id-not-valid">
                <p>${documentIdNotValid}</p>
            </div>

            <div class="error" id="car-mark-not-valid">
                <p>${carMarkNotValid}</p>
            </div>

            <div class="error" id="login-not-exist">
                <p>${loginNotExist}</p>
            </div>

            <div class="error" id="driver-exist">
                <p>${driverExist}</p>
            </div>

            <div class="error" id="car-number-exist">
                <p>${carNumberExist}</p>
            </div>

            <div class="error" id="document-id-exist">
                <p>${documentIdExist}</p>
            </div>

            <div class="error" id="tariff-not-valid">
                <p>${tariffNotValid}</p>
            </div>

            <div class="correct" id="all-correct">
                <p>${driverSignUpCorrectMessage}</p>
            </div>
        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>

