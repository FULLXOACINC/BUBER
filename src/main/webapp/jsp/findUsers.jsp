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
<fmt:message bundle="${locale}" key="text.admin.viewUnacceptedComplaints" var="viewUnacceptedComplaints"/>
<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/findUsers.js"></script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <input id="pattern" class="form-control" type="text" value="">
            <input id="find-users" class="btn btn-lg btn-primary btn-block" type="submit" value="${find}">
            <br/>
            <div id="searched"></div>
        </div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>