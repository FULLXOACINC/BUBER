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
<fmt:message bundle="${locale}" key="text.age" var="age"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.allCorrectMessage" var="allCorrectMessage"/>

<fmt:message bundle="${locale}" key="text.error.notValidLoginErrorMessage" var="notValidLoginErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.loginExistErrorMessage" var="loginExistErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.phoneNumberExistErrorMessage" var="phoneNumberExistErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.firstNameErrorMessage" var="firstNameErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.secondNameErrorMessage" var="secondNameErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.notValidPasswordErrorMessage" var="notValidPasswordErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.passwordNotEqMessage" var="passwordNotEqMessage"/>
<fmt:message bundle="${locale}" key="text.error.ageErrorMessage" var="ageErrorMessage"/>
<fmt:message bundle="${locale}" key="text.error.notValidPhoneNumberErrorMessage" var="notValidPhoneNumberErrorMessage"/>


<html>
<head>
    <title>${signUp}</title>
</head>
<body>
<form action="${ pageContext.request.contextPath }/controller" method="post">
    <p>${login}<input type="text" name="login" value="${oldLogin}"/></p>
    <p>${carNumber}<input type="text" name="carNumber" value="${oldCarNumber}"/></p>
    <p>${documentId}<input type="text" name="documentId" value="${oldDocumentId}"/></p>
    <p>${carMark}<input type="text" name="carMark" value="${oldCarMark}"/></p>

    <input type="hidden" name="command" value="sign-up-user">
    <input type="submit" value="${signUp}">
</form>

<c:if test="${notValidLoginError}">
    <p>${notValidLoginErrorMessage}</p>
</c:if>
<c:if test="${loginExistError}">
    <p>${loginExistErrorMessage}</p>
</c:if>
<c:if test="${phoneNumberExistError}">
    <p> ${phoneNumberExistErrorMessage}</p>
</c:if>
<c:if test="${firstNameError}">
    <p> ${firstNameErrorMessage}</p>
</c:if>
<c:if test="${secondNameError}">
    <p> ${secondNameErrorMessage}</p>
</c:if>
<c:if test="${notValidPasswordError}">
    <p> ${notValidPasswordErrorMessage}</p>
</c:if>
<c:if test="${passwordNotEq}">
    <p>${passwordNotEqMessage}</p>
</c:if>
<c:if test="${ageError}">
    <p> ${ageErrorMessage}</p>
</c:if>
<c:if test="${notValidPhoneNumberError}">
    <p> ${notValidPhoneNumberErrorMessage}</p>
</c:if>
<c:if test="${allCorrect}">
    <p> ${allCorrectMessage}</p>
</c:if>

<br/>

<ctg:hello auth="Alex Zhuk" description="Created for EPAM-System java traning"/>
</body>
</html>

