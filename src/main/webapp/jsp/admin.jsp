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
<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="sign-out">
    <input type="submit" value="${signOut}">
</form>
<form action="${pageContext.request.contextPath}/jsp/signUpDriver.jsp">
    <input type="submit" value="${signUpDriver}">
</form>
<input id="pattern" type="text" value="">
<input id="find-users" type="submit" value="${find}">
<br/>
<div id="searched">

</div>
</body>
</html>