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
<fmt:message bundle="${locale}" key="text.admin.ban" var="ban"/>
<fmt:message bundle="${locale}" key="text.admin.unBan" var="unBan"/>
<fmt:message bundle="${locale}" key="text.admin.removeAdminStatus" var="removeAdminStatus"/>
<fmt:message bundle="${locale}" key="text.admin.takeAdminStatus" var="takeAdminStatus"/>
<fmt:message bundle="${locale}" key="text.admin.console" var="console"/>
<fmt:message bundle="${locale}" key="text.admin.viewUserComplaints" var="viewUserComplaints"/>
<fmt:message bundle="${locale}" key="text.notFound" var="notFound"/>
<fmt:message bundle="${locale}" key="text.change" var="change"/>
<fmt:message bundle="${locale}" key="text.updateDriver" var="updateDriver"/>
<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/viewUser.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">

</head>
<body>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<c:choose>
    <c:when test="${notFound}">
        notFound
    </c:when>
    <c:otherwise>
        <c:out value="${user.login}" escapeXml="true"/>
        <c:out value="${user.firstName}" escapeXml="true"/>
        <c:out value="${user.lastName}" escapeXml="true"/>
        <c:out value="${user.phoneNumber}" escapeXml="true"/>

        <c:set var="banView"/>
        <c:choose>
            <c:when test="${!user.isBaned()}">
                <c:set var="banView" value="${ban}"/>
            </c:when>
            <c:otherwise>
                <c:set var="banView" value="${unBan}"/>
            </c:otherwise>
        </c:choose>

        <c:set var="adminStatusView"/>
        <c:choose>
            <c:when test="${user.type == 'ADMIN'}">
                <c:set var="adminStatusView" value="${removeAdminStatus}"/>
            </c:when>
            <c:otherwise>
                <c:set var="adminStatusView" value="${takeAdminStatus}"/>
            </c:otherwise>
        </c:choose>
        <c:if test="${user.type != 'ROOT_ADMIN' && !(user.login eq sessionScope.login)}">
            <form action="${ pageContext.request.contextPath }/controller" method="post">
                <input type="hidden" name="command" value="switch-ban">
                <input type="hidden" name="user" value="${user.login}">
                <input type="submit" value="${banView}">
            </form>

            <form action="${ pageContext.request.contextPath }/controller" method="post">
                <input type="hidden" name="command" value="switch-admin-status">
                <input type="hidden" name="user" value="${user.login}">
                <input type="submit" value="${adminStatusView}">
            </form>
        </c:if>
        <c:if test="${user.type == 'DRIVER'}">
            <form action="${ pageContext.request.contextPath }/controller" method="post">
                <input type="hidden" name="command" value="find-driver">
                <input type="hidden" name="driver" value="${user.login}">
                <input type="submit" value="${updateDriver}">
            </form>
        </c:if>
        <input id="discount" type="text" value="${user.discount}">
        <input id="login" type="hidden" value="${user.login}">
        <input id="change-discount" type="submit" value="${change}">
        <div>
            <input id="view-complaints" type="submit" value="${viewUserComplaints}">
            <div id="complaints"></div>
        </div>
    </c:otherwise>
</c:choose>
    <ctg:footer auth="Alex Zhuk" description="Created fo EPAM System java traning"/>
</body>
</html>