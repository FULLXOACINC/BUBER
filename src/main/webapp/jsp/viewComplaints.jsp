<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.admin.find" var="find"/>
<fmt:message bundle="${locale}" key="text.admin.signUpDriver" var="signUpDriver"/>
<fmt:message bundle="${locale}" key="text.admin.console" var="console"/>
<fmt:message bundle="${locale}" key="text.admin.raidId" var="raidId"/>
<fmt:message bundle="${locale}" key="text.admin.complaintId" var="complaintId"/>
<fmt:message bundle="${locale}" key="text.admin.acceptComplaint" var="acceptComplaint"/>
<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/viewComplaints.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">

</head>
<body>
<div class="back">
    <div class="container">
        <c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
        <c:forEach var="complaint" items="${complaints}">
            <div id="complaint-${complaint.complaintId}">
                <p>${raidId}: ${complaint.complaintId}</p>
                <form action='${pageContext.request.contextPath}/controller'>
                    <input type='hidden' name='command' value='view-user'>
                    <input type='hidden' name='user' value='${complaint.complaintPersonLogin}'>
                    <input type='submit' value='${complaint.complaintPersonLogin}'>
                </form>
                <p>${complaintId}: ${complaint.raidId}</p>
                <p><c:out value="${complaint.complaintText}" escapeXml="true"/></p>
                <p><input type='submit' class="accept" id='${complaint.complaintId}' value="${acceptComplaint}"></p>
                <hr/>
            </div>

        </c:forEach>

    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>