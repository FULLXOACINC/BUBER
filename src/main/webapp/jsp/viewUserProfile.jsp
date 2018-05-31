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
<fmt:message bundle="${locale}" key="text.userNotFound" var="userNotFound"/>
<fmt:message bundle="${locale}" key="text.change" var="change"/>
<fmt:message bundle="${locale}" key="text.updateDriver" var="updateDriver"/>

<fmt:message bundle="${locale}" key="text.login" var="login"/>
<fmt:message bundle="${locale}" key="text.firstName" var="firstName"/>
<fmt:message bundle="${locale}" key="text.secondName" var="lastName"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.balance" var="balance"/>
<fmt:message bundle="${locale}" key="text.discount" var="discount"/>
<fmt:message bundle="${locale}" key="text.updateProfile" var="updateProfile"/>

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
                    <input type="text" id="login" value="${login}: ${user.login}" class="form-control" readonly/>
                    <input type="text" value="${firstName}: ${user.firstName}" class="form-control" readonly/>
                    <input type="text" value="${lastName}: ${user.lastName}" class="form-control" readonly/>
                    <input type="text" value="${phoneNumber}: ${user.phoneNumber}" class="form-control" readonly/>
                    <input type="text" value="${balance}: ${user.balance} BYN" class="form-control" readonly/>
                    <input type="text" value="${discount}: ${user.discount}" class="form-control" readonly/>

                    <form action="${ pageContext.request.contextPath }/controller">
                        <input type="hidden" name="command" value="find-user-update">
                        <input type="submit" class="btn btn-lg btn-primary btn-block" value="${updateProfile}">
                    </form>

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