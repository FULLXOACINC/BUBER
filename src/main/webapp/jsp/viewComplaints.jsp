<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
<form action="${pageContext.request.contextPath}/jsp/admin.jsp">
    <input type="submit" value="${console}">
</form>
<c:forEach var="complaint" items="${complaints}">
    <div id="complaint-${complaint.complaintId}">
        <p>${raidId}: ${complaint.complaintId}</p>
        <form action='${pageContext.request.contextPath}/controller'>
            <input type='hidden' name='command' value='view-user'>
            <input type='hidden' name='user' value='${complaint.complaintPersonLogin}'>
            <input type='submit' value='${complaint.complaintPersonLogin}'>
        </form>
        <p>${complaintId}: ${complaint.raidId}</p>
        <p>${complaint.complaintText}</p>
        <p><input type='submit' class="accept" id='${complaint.complaintId}' value="${acceptComplaint}"></p>
        <hr/>
    </div>

</c:forEach>

</div>
</body>
</html>