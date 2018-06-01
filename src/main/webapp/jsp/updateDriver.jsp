<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.password" var="password"/>
<fmt:message bundle="${locale}" key="text.repeatPassword" var="repeatPassword"/>
<fmt:message bundle="${locale}" key="text.firstName" var="firstName"/>
<fmt:message bundle="${locale}" key="text.lastName" var="secondName"/>
<fmt:message bundle="${locale}" key="text.birthDay" var="age"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.updateDriver" var="updateDriver"/>

<fmt:message bundle="${locale}" key="text.driverCorrectMessage" var="driverUpdateCorrectMessage"/>

<fmt:message bundle="${locale}" key="text.notValidLogin" var="notValidLoginErrorMessage"/>
<fmt:message bundle="${locale}" key="text.loginExist" var="loginExistErrorMessage"/>
<fmt:message bundle="${locale}" key="text.phoneNumberExist" var="phoneNumberExistErrorMessage"/>
<fmt:message bundle="${locale}" key="text.firstNameNotValid" var="firstNameErrorMessage"/>
<fmt:message bundle="${locale}" key="text.lastNameNotValid" var="secondNameErrorMessage"/>
<fmt:message bundle="${locale}" key="text.notValidPassword" var="notValidPasswordErrorMessage"/>
<fmt:message bundle="${locale}" key="text.passwordNotEq" var="passwordNotEqMessage"/>
<fmt:message bundle="${locale}" key="text.birthDayErrorNotValid" var="birthDayErrorMessage"/>
<fmt:message bundle="${locale}" key="text.phoneNumberNotValid" var="notValidPhoneNumberErrorMessage"/>

<fmt:message bundle="${locale}" key="text.driverNotFound" var="driverNotFound"/>

<fmt:message bundle="${locale}" key="text.carNumber" var="carNumber"/>
<fmt:message bundle="${locale}" key="text.documentId" var="documentId"/>
<fmt:message bundle="${locale}" key="text.carMark" var="carMark"/>
<fmt:message bundle="${locale}" key="text.tariff" var="tariff"/>
<fmt:message bundle="${locale}" key="text.updateDriverCorrect" var="updateDriverCorrect"/>


<fmt:message bundle="${locale}" key="text.driverNotExist" var="driverNotExistError"/>

<fmt:message bundle="${locale}" key="text.error.carNumberNotValid" var="carNumberError"/>
<fmt:message bundle="${locale}" key="text.documentIdNotValid" var="documentIdError"/>
<fmt:message bundle="${locale}" key="text.carMarkNotValid" var="carMarkError"/>
<fmt:message bundle="${locale}" key="text.driverExist" var="driverExistError"/>
<fmt:message bundle="${locale}" key="text.loginNotExist" var="loginNotExistError"/>
<fmt:message bundle="${locale}" key="text.carNumberExist" var="carNumberExistError"/>
<fmt:message bundle="${locale}" key="text.documentIdExist" var="documentIdExistError"/>
<fmt:message bundle="${locale}" key="text.tariffNotValid" var="tariffError"/>


<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/updateDriver.js"></script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">

            <c:choose>
                <c:when test="${not empty driver}">
                    <h2 class="form-input-heading">${updateDriver}</h2>

                    <h2 class="form-input-heading">${driver.login}</h2>

                    <input type="hidden" id="login" value="${driver.login}">

                    <input type="text" class="form-control" id="car-number" value="${driver.carNumber}"
                           placeholder="${carNumber}"/>
                    <input type="text" class="form-control" id="document-id" value="${driver.documentId}"
                           placeholder="${documentId}"/>
                    <input type="text" class="form-control" id="car-mark" value="${driver.carMark.markName}"
                           placeholder="${carMark}"/>
                    <input type="text" class="form-control" id="tariff" value="${driver.tariff}"
                           placeholder="${tariff}"/>
                    <input type="submit" class="btn btn-lg btn-primary btn-block" id="update-driver"
                           value="${updateDriver}">

                    <div class="error" id="car-number-not-valid">
                        <p>${carNumberError}</p>
                    </div>
                    <div class="error" id="document-id-not-valid">
                        <p>${documentIdError}</p>
                    </div>
                    <div class="error" id="car-mark-not-valid">
                        <p>${carMarkError}</p>
                    </div>
                    <div class="error" id="driver-not-exist">
                        <p>${driverNotExistError}</p>
                    </div>
                    <div class="error" id="car-number-exist">
                        <p>${carNumberExistError}</p>
                    </div>
                    <div class="error" id="document-id-exist">
                        <p>${documentIdExistError}</p>
                    </div>
                    <div class="error" id="tariff-not-valid">
                        <p>${tariffError}</p>
                    </div>
                    <div class="correct" id="all-correct">
                        <p>${updateDriverCorrect}</p>
                    </div>

                </c:when>
                <c:otherwise>
                    <input type="text" value="${driverNotFound}" class="form-control" readonly/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>

