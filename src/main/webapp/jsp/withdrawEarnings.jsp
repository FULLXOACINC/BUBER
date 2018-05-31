<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.fillUp" var="fillUpBalance"/>

<fmt:message bundle="${locale}" key="text.fillUp.cardNumber" var="cardNumber"/>
<fmt:message bundle="${locale}" key="text.fillUp.moneyAmount" var="moneyAmount"/>
<fmt:message bundle="${locale}" key="text.fillUp.cardNumberMessage" var="cardNumberMessage"/>
<fmt:message bundle="${locale}" key="text.earnedMoney" var="earnedMoney"/>

<fmt:message bundle="${locale}" key="text.noMoneyEarned" var="noMoneyEarned"/>
<fmt:message bundle="${locale}" key="text.withdrawEarnings" var="withdrawEarnings"/>
<fmt:message bundle="${locale}" key="text.withdrawEarningsCorrectMessage" var="withdrawEarningsCorrectMessage"/>

<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/withdrawEarnings.js"></script>
</head>
<body>
<input type='hidden' id="earned-money-mess" value='${earnedMoney}'>

<div class="back">
    <div class="container">
        <div class="form-input">
            <h2 class="form-input-heading">${withdrawEarnings}</h2>
            <input type="text" id="earned-money" class="form-control" readonly/>
            <input type="text" id="card-number-input" class="form-control" placeholder="${cardNumber}">
            <input type="submit" class="btn btn-lg btn-primary btn-block" id="withdraw-earnings"
                   value="${withdrawEarnings}">
            <div class="error" id="no-money-earned">
                <p>${noMoneyEarned}</p>
            </div>
            <div class="error" id="card-number">
                <p>${cardNumberMessage}</p>
            </div>
            <div class="correct" id="withdraw-earnings-correct">
                <p>${withdrawEarningsCorrectMessage}</p>
            </div>
        </div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>
