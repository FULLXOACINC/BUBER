<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.orderTaxi" var="orderTaxi"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.fillUp" var="fillUpBalance"/>

<fmt:message bundle="${locale}" key="text.fillUp.fillUpBalanceCorrectMessage" var="fillUpBalanceCorrectMessage"/>
<fmt:message bundle="${locale}" key="text.fillUp.fullBalanceMessage" var="fullBalanceMessage"/>
<fmt:message bundle="${locale}" key="text.fillUp.outOfBoundBalanceMessage" var="outOfBoundBalanceMessage"/>
<fmt:message bundle="${locale}" key="text.fillUp.unknownMoneyFormatMessage" var="unknownMoneyFormatMessage"/>

<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/fillUpBalance.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>

<input type="radio" name="money-amount" value="5.00" checked>5.00<br/>
<input type="radio" name="money-amount" value="10.00">10.00<br/>
<input type="radio" name="money-amount" value="15.00">15.00<br/>
<input type="radio" name="money-amount" value="20.00">20.00<br/>
<input type="radio" name="money-amount" value="50.00">50.00<br/>
<input type="radio" name="money-amount" value="100.00">100.00<br/>
<input type="submit" id="fill-up-balance" value="${fillUpBalance}">


<div class="error" id="unknown-money-format">
    <p>${unknownMoneyFormatMessage}</p>
</div>
<div class="error" id="out-of-bound-balance">
    <p>${outOfBoundBalanceMessage}</p>
</div>
<div class="error" id="full-balance">
    <p>${fullBalanceMessage}</p>
</div>
<div class="correct" id="fill-up-balance-correct">
    <p>${fillUpBalanceCorrectMessage}</p>
</div>
</body>
</html>
