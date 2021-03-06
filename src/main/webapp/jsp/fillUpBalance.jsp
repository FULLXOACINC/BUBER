<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.fillUp" var="fillUpBalance"/>

<fmt:message bundle="${locale}" key="text.cardNumber" var="cardNumber"/>
<fmt:message bundle="${locale}" key="text.moneyAmount" var="moneyAmount"/>
<fmt:message bundle="${locale}" key="text.cardNumberMessage" var="cardNumberMessage"/>

<fmt:message bundle="${locale}" key="text.fillUpBalanceCorrect" var="fillUpBalanceCorrectMessage"/>
<fmt:message bundle="${locale}" key="text.fullBalance" var="fullBalanceMessage"/>
<fmt:message bundle="${locale}" key="text.outOfBoundBalance" var="outOfBoundBalanceMessage"/>
<fmt:message bundle="${locale}" key="text.unknownMoneyFormat" var="unknownMoneyFormatMessage"/>
<fmt:message bundle="${locale}" key="text.unknownMoneyFormat" var="unknownMoneyFormatMessage"/>

<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/fillUpBalance.js"></script>
</head>
<body>

<div class="back">
    <div class="container">
        <div class="form-input">
            <input type="text" id="card-number-input" class="form-control" placeholder="${cardNumber}">
            <input type="text" id="amount" class="form-control" placeholder="${moneyAmount}">
            <input type="submit" class="btn btn-lg btn-primary btn-block" id="fill-up-balance" value="${fillUpBalance}">


            <div class="error" id="unknown-money-format">
                <p>${unknownMoneyFormatMessage}</p>
            </div>
            <div class="error" id="out-of-bound-balance">
                <p>${outOfBoundBalanceMessage}</p>
            </div>
            <div class="error" id="full-balance">
                <p>${fullBalanceMessage}</p>
            </div>
            <div class="error" id="card-number">
                <p>${cardNumberMessage}</p>
            </div>
            <div class="correct" id="fill-up-balance-correct">
                <p>${fillUpBalanceCorrectMessage}</p>
            </div>
        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>
