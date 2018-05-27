<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.startAddress" var="startAddress"/>
<fmt:message bundle="${locale}" key="text.endAddress" var="endAddress"/>
<fmt:message bundle="${locale}" key="text.selectAddresses" var="selectAddresses"/>
<fmt:message bundle="${locale}" key="text.clearMap" var="clearMap"/>
<fmt:message bundle="${locale}" key="text.distance" var="distance"/>
<fmt:message bundle="${locale}" key="text.duration" var="duration"/>

<fmt:message bundle="${locale}" key="text.negativeBalance" var="negativeBalance"/>
<fmt:message bundle="${locale}" key="text.coordinateNotValid" var="coordinateNotValid"/>
<fmt:message bundle="${locale}" key="text.wrongDistance" var="wrongDistance"/>
<fmt:message bundle="${locale}" key="text.orderExist" var="orderExist"/>
<fmt:message bundle="${locale}" key="text.orderCorrect" var="orderCorrect"/>
<fmt:message bundle="${locale}" key="text.driverNotExist" var="driverNotExist"/>
<fmt:message bundle="${locale}" key="text.driverNotSuitable" var="driverNotSuitable"/>
<fmt:message bundle="${locale}" key="text.driverEqPassenger" var="driverEqPassenger"/>


<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/orderTaxi.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY&callback=initMap">
    </script>
</head>
<body>
<div class="back">
    <div class="container">
        <div class="form-input">
            <h2 class="form-input-heading">Текущая поездка</h2>

            <input type="text" class="form-control" value="${ride.rideId}" readonly/>

            <input type="text" class="form-control" value="${ride.driver.phoneNumber}" readonly/>
            <input type="text" class="form-control" value="${ride.driver.firstName} ${ride.driver.secondName}" readonly/>
            <input type="text" class="form-control" value="${ride.driver.carMark.markName} ${ride.driver.carNumber}" readonly/>

            <input type="submit" class="btn btn-lg btn-primary btn-block" id="select-addresses"
                   value="${selectAddresses}"/>
            <input type="submit" class="btn btn-lg btn-primary btn-block" id="clear-map" value="${clearMap}"/>

        </div>
        <div id="map"></div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>