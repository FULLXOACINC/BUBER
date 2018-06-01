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

<fmt:message bundle="${locale}" key="text.lastName" var="lastName"/>
<fmt:message bundle="${locale}" key="text.firstName" var="firstName"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.updateUserCorrect" var="updateUserCorrect"/>
<fmt:message bundle="${locale}" key="text.updateUser" var="updateUser"/>
<fmt:message bundle="${locale}" key="text.userNotFound" var="userNotFound"/>

<fmt:message bundle="${locale}" key="text.firstNameNotValid" var="firstNameNotValidMessage"/>
<fmt:message bundle="${locale}" key="text.lastNameNotValid" var="lastNameNotValidMessage"/>
<fmt:message bundle="${locale}" key="text.phoneNumberExist" var="phoneNumberExistMessage"/>
<fmt:message bundle="${locale}" key="text.phoneNumberNotValid" var="phoneNumberNotValidMessage"/>


<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/updateUser.js"></script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <c:choose>
                <c:when test="${not empty user}">
                    <h2 class="form-input-heading">${updateUser}</h2>
                    <h2 class="form-input-heading">${user.login}</h2>

                    <input type="text" class="form-control" id="first-name" value="${user.firstName}"
                           placeholder="${firstName}"/>
                    <input type="text" class="form-control" id="last-name" value="${user.lastName}"
                           placeholder="${lastName}"/>
                    <input type="text" class="form-control" id="phone-number" value="${user.phoneNumber}"
                           placeholder="${phoneNumber}"/>
                    <input type="submit" class="btn btn-lg btn-primary btn-block" id="update-user"
                           value="${updateUser}">


                    <div class="error" id="phone-number-exist">
                        <p>${phoneNumberExistMessage}</p>
                    </div>
                    <div class="error" id="phone-number-not-valid">
                        <p>${phoneNumberNotValidMessage}</p>
                    </div>
                    <div class="error" id="first-name-not-valid">
                        <p>${firstNameErrorMessage}</p>
                    </div>

                    <div class="error" id="last-name-not-valid">
                        <p>${lastNameNotValidMessage}</p>
                    </div>

                    <div class="correct" id="all-correct">
                        <p>${updateUserCorrect}</p>
                    </div>

                </c:when>
                <c:otherwise>
                    <input type="text" value="${userNotFound}" class="form-control" readonly/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>

