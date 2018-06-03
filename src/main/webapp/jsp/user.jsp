<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.ban" var="ban"/>
<fmt:message bundle="${locale}" key="text.unBan" var="unBan"/>
<fmt:message bundle="${locale}" key="text.removeAdminStatus" var="removeAdminStatus"/>
<fmt:message bundle="${locale}" key="text.takeAdminStatus" var="takeAdminStatus"/>
<fmt:message bundle="${locale}" key="text.viewUserComplaints" var="viewUserComplaints"/>
<fmt:message bundle="${locale}" key="text.userNotFound" var="userNotFound"/>
<fmt:message bundle="${locale}" key="text.change" var="change"/>
<fmt:message bundle="${locale}" key="text.updateDriver" var="updateDriver"/>

<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.firstName" var="firstName"/>
<fmt:message bundle="${locale}" key="text.lastName" var="lastName"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>

<fmt:message bundle="${locale}" key="text.changeCorrect" var="changeCorrect"/>
<fmt:message bundle="${locale}" key="text.repeatPls" var="repaetPls"/>
<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/user.js"></script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <c:choose>
                <c:when test="${not empty user}">
                    <input type="hidden" id="login-val" value="${user.login}" class="form-control" readonly/>
                    <input type="text" value="${login}: ${user.login}" class="form-control" readonly/>
                    <input type="text" value="${firstName}: ${user.firstName}" class="form-control" readonly/>
                    <input type="text" value="${lastName}: ${user.lastName}" class="form-control" readonly/>
                    <input type="text" value="${phoneNumber}: ${user.phoneNumber}" class="form-control" readonly/>

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
                        <form action="${ pageContext.request.contextPath }/Controller" method="post">
                            <input type="hidden" name="command" value="switch-ban">
                            <input type="hidden" name="user" value="${user.login}">
                            <input type="submit" class="btn btn-lg btn-primary btn-block" value="${banView}">
                        </form>

                        <form action="${ pageContext.request.contextPath }/Controller" method="post">
                            <input type="hidden" name="command" value="switch-admin-status">
                            <input type="hidden" name="user" value="${user.login}">
                            <input type="submit" class="btn btn-lg btn-primary btn-block" value="${adminStatusView}">
                        </form>
                    </c:if>
                    <c:if test="${user.type == 'DRIVER'}">
                        <form action="${ pageContext.request.contextPath }/Controller">
                            <input type="hidden" name="command" value="find-driver-update">
                            <input type="hidden" name="driver" value="${user.login}">
                            <input type="submit" class="btn btn-lg btn-primary btn-block" value="${updateDriver}">
                        </form>
                    </c:if>
                    <input id="discount" class="form-control" type="text" value="${user.discount}">
                    <input id="change-discount" class="btn btn-lg btn-primary btn-block" type="submit"
                           value="${change}">
                    <div class="correct" id="all-correct">
                        <p>${changeCorrect}</p>
                    </div>
                    <div class="error" id="repeat-pls">
                        <p>${repaetPls}</p>
                    </div>
                    <div>
                        <form action="${ pageContext.request.contextPath }/jsp/userComplaints.jsp">
                            <input name="userLogin" type="hidden" value="${user.login}">
                            <input class="btn btn-lg btn-primary btn-block" type="submit"
                                   value="${viewUserComplaints}">
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <input type="text" value="${userNotFound}" class="form-control" readonly/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>