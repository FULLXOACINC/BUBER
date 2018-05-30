<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.rideId" var="rideId"/>
<fmt:message bundle="${locale}" key="text.admin.complaintId" var="complaintId"/>
<fmt:message bundle="${locale}" key="text.admin.acceptComplaint" var="acceptComplaint"/>

<fmt:message bundle="${locale}" key="text.noComplaints" var="noComplaints"/>
<fmt:message bundle="${locale}" key="text.moreAboutUser" var="moreAboutUser"/>

<fmt:message bundle="${locale}" key="text.next" var="next"/>
<fmt:message bundle="${locale}" key="text.previous" var="previous"/>

<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/viewUserComplaints.js"></script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <div id="complaint">
                <input type='hidden' id="ride-id-mess" value='${rideId}'>
                <input type='hidden' id="complaint-id-mess" value='${complaintId}'>
                <input type='hidden' id="login" value='${param.userLogin}'>

                <ul class="pagination">
                    <input type='submit' class="page-link" id='prev' value="${previous}">
                    <input type='submit' class="page-link" id='next' value="${next}">
                </ul>
                <input type="text" id="complaint-id" class="form-control" readonly/>
                <input type="text" class="form-control" id="ride-id" readonly/>
                <textarea class="form-control" rows="5" id="complaint-text" readonly></textarea>
                <input type='submit' class="btn btn-lg btn-primary btn-block" id='accept' value="${acceptComplaint}">
            </div>
            <div id="no-complaint">
                <input type="text" class="form-control" value="${noComplaints}" readonly/>
            </div>
        </div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>