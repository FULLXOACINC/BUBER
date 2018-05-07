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

<html>
<head>
    <title>${buber}</title>
</head>
<body>
    <input type="radio" name="amount" value="5.00"> 5.00<br/>
    <input type="radio" name="amount" value="10.00"> 10.00<br/>
    <input type="radio" name="amount" value="15.00"> 15.00<br/>
    <input type="radio" name="amount" value="20.00"> 20.00<br/>
    <input type="radio" name="amount" value="50.00"> 50.00<br/>
    <input type="radio" name="amount" value="100.00"> 100.00<br/>
    <input type="submit" id="fill-up-balance" value="${fillUpBalance}">

    <div class="error" id="out-of-bound-balance-error">
        <p>outOfBoundBalanceError</p>
    </div>

    <div class="correct" id="all-correct">
        <p>allCorrectMessage</p>
    </div>
</body>
</html>
