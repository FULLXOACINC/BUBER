<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.restorePassword" var="restorePassword"/>
<fmt:message bundle="${locale}" key="text.password" var="password"/>
<fmt:message bundle="${locale}" key="text.repeatPassword" var="repeatPassword"/>
<fmt:message bundle="${locale}" key="text.allCorrectRestoreMessage" var="allCorrectRestoreMessage"/>

<fmt:message bundle="${locale}" key="text.notValidLogin" var="notValidLogin"/>
<fmt:message bundle="${locale}" key="text.loginNotExist" var="loginNotExist"/>
<fmt:message bundle="${locale}" key="text.notValidPassword" var="passwordNotValid"/>
<fmt:message bundle="${locale}" key="text.passwordNotEq" var="passwordNotEq"/>


<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/restorePassword.js"></script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <h2 class="form-input-heading">${restorePassword}</h2>
            <input type="text" class="form-control" id="login" placeholder="${login}"/>
            <input type="password" class="form-control" id="password" placeholder="${password}"/>
            <input type="password" class="form-control" id="repeat-password" placeholder="${repeatPassword}"/>
            <div>
                <input class="btn btn-lg btn-primary btn-block" type="submit" id="restore-password"
                       value="${restorePassword}">
            </div>

            <div class="error" id="not-valid-login">
                <p>${notValidLogin}</p>
            </div>

            <div class="error" id="login-not-exist">
                <p>${loginNotExist}</p>
            </div>

            <div class="error" id="not-valid-password">
                <p>${passwordNotValid}</p>
            </div>

            <div class="error" id="password-not-eq">
                <p>${passwordNotEq}</p>
            </div>

            <div class="correct" id="all-correct">
                <p>${allCorrectRestoreMessage}</p>
            </div>

        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>

