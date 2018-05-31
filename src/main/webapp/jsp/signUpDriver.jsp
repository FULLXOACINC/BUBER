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

<fmt:message bundle="${locale}" key="text.error.carNumberNotValid" var="carNumberError"/>
<fmt:message bundle="${locale}" key="text.documentIdNotValid" var="documentIdError"/>
<fmt:message bundle="${locale}" key="text.carMarkNotValid" var="carMarkError"/>
<fmt:message bundle="${locale}" key="text.driverExist" var="driverExistError"/>
<fmt:message bundle="${locale}" key="text.loginNotExist" var="loginNotExistError"/>
<fmt:message bundle="${locale}" key="text.carNumberExist" var="carNumberExistError"/>
<fmt:message bundle="${locale}" key="text.documentIdExist" var="documentIdExistError"/>
<fmt:message bundle="${locale}" key="text.tariffNotValid" var="tariffError"/>


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
        <p>${login}<input type="text" id="login"/></p>
        <p>${carNumber}<input type="text" id="car-number" value="7522AT1"/></p>
        <p>${documentId}<input type="text" id="document-id" value="7AD123132"/></p>
        <p>${carMark}<input type="text" id="car-mark"/></p>
        <p>${tariff}<input type="text" id="tariff"/></p>

        <p><input type="submit" id="sign-up-driver" value="${signUp}"></p>

        <div class="error" id="car-number-error">
            <p>${carNumberError}</p>
        </div>

        <div class="error" id="document-id-error">
            <p>${documentIdError}</p>
        </div>

        <div class="error" id="car-mark-error">
            <p>${carMarkError}</p>
        </div>

        <div class="error" id="login-not-exist-error">
            <p>${loginNotExistError}</p>
        </div>

        <div class="error" id="driver-exist-error">
            <p>${driverExistError}</p>
        </div>

        <div class="error" id="car-number-exist-error">
            <p>${carNumberExistError}</p>
        </div>

        <div class="error" id="document-id-exist-error">
            <p>${documentIdExistError}</p>
        </div>

        <div class="error" id="tariff-error">
            <p>${tariffError}</p>
        </div>

        <div class="correct" id="all-correct">
            <p>${driverSignUpCorrectMessage}</p>
        </div>

    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>

