<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.signOut" var="signOut"/>
<fmt:message bundle="${locale}" key="text.startAddress" var="startAddress"/>
<fmt:message bundle="${locale}" key="text.endAddress" var="endAddress"/>
<fmt:message bundle="${locale}" key="text.selectAddresses" var="selectAddresses"/>
<fmt:message bundle="${locale}" key="text.clearMap" var="clearMap"/>

<html>
<head>
    <title>${buber}</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/orderTaxi.js"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY&callback=initMap">
    </script>
    <style>
        #map {
            height: 100%;
        }

        html, body {
            height: 90%;
            margin: 0;
            padding: 0;
        }
    </style>
</head>
<body>
<p>${startAddress}<input type="text" id="start-address" value="ул 50 лет победы 23 29,Минск"/></p>
<p>${endAddress}<input type="text" id="end-address" value="просп. Жукова 29, Минск"/></p>
<input type="submit" id="select-addresses" value="${selectAddresses}"/>
<input type="submit" id="clearMap" value="${clearMap}"/>
<div id="map"></div>
<button id="btn">Test</button>
</body>
</html>