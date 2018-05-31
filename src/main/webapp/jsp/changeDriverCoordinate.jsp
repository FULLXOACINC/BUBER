<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.currentAddress" var="currentAddress"/>
<fmt:message bundle="${locale}" key="text.selectAddress" var="selectAddress"/>
<fmt:message bundle="${locale}" key="text.clearMap" var="clearMap"/>
<fmt:message bundle="${locale}" key="text.acceptCoordinate" var="acceptCoordinate"/>

<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/changeDriverCoordinate.js"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY&callback=initMap">
    </script>
</head>
<body>

<div class="back">
    <div class="container">
        <div>
            <div>${currentAddress}:</div>
            <input type="text" id="current-address" value="ул 50 лет победы 23 29,Минск"/>
        </div>

        <input type="submit" id="select-address" value="${selectAddress}"/>
        <input type="submit" id="clear-map" value="${clearMap}"/>
        <input type="submit" id="accept-coordinate" value="${acceptCoordinate}"/>

        <div id="map"></div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>