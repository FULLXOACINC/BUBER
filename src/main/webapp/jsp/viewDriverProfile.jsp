<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.driverNotFound" var="driverNotFound"/>
<fmt:message bundle="${locale}" key="text.change" var="change"/>
<fmt:message bundle="${locale}" key="text.updateDriver" var="updateDriver"/>

<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.carNumber" var="carNumber"/>
<fmt:message bundle="${locale}" key="text.documentId" var="documentId"/>
<fmt:message bundle="${locale}" key="text.positiveMark" var="positiveMark"/>
<fmt:message bundle="${locale}" key="text.negativeMark" var="negativeMark"/>
<fmt:message bundle="${locale}" key="text.tariff" var="tariff"/>
<fmt:message bundle="${locale}" key="text.atWork" var="atWork"/>
<fmt:message bundle="${locale}" key="text.notAtWork" var="notAtWork"/>
<fmt:message bundle="${locale}" key="text.earnedMoney" var="earnedMoney"/>
<fmt:message bundle="${locale}" key="text.carMark" var="carMark"/>


<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/viewUser.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">

</head>
<body>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<div class="back">
    <div class="container">
        <div class="form-input">
            <c:choose>
                <c:when test="${not empty driver}">
                    <input type="text" id="login" value="${login}: ${driver.login}" class="form-control" readonly/>
                    <input type="text" value="${carNumber}: ${driver.carNumber}" class="form-control" readonly/>
                    <input type="text" value="${documentId}: ${driver.documentId}" class="form-control" readonly/>
                    <input type="text" value="${carMark}: ${driver.carMark.markName}" class="form-control" readonly/>
                    <input type="text" value="${positiveMark}: ${driver.positiveMark}" class="form-control" readonly/>
                    <input type="text" value="${negativeMark}: ${driver.negativeMark}" class="form-control" readonly/>
                    <input type="text" value="${tariff}: ${driver.tariff}" class="form-control" readonly/>
                    <input type="text" value="${earnedMoney}: ${driver.earnedMoney} BYN" class="form-control" readonly/>
                    <c:choose>
                        <c:when test="${driver.isWorking()}">
                            <input type="text" value="${atWork}" class="form-control" readonly/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="${notAtWork}" class="form-control" readonly/>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <input type="text" value="${driverNotFound}" class="form-control" readonly/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created fo EPAM System java traning"/>
</body>
</html>