<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.driver.changeCurrentCoordinate" var="changeCurrentCoordinate"/>

<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">

</head>
<body>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<form action="${pageContext.request.contextPath}/jsp/changeDriverCoordinate.jsp">
    <input type="submit" value="${changeCurrentCoordinate}">
</form>
<form action="${pageContext.request.contextPath}/jsp/signUpDriver.jsp">
    <input type="submit" value="currentRaid">
</form>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>