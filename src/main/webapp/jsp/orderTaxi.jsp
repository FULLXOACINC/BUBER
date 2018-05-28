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
<fmt:message bundle="${locale}" key="text.carNumber" var="carNumber"/>
<fmt:message bundle="${locale}" key="text.carMark" var="carMark"/>
<fmt:message bundle="${locale}" key="text.tariff" var="tariff"/>
<fmt:message bundle="${locale}" key="text.price" var="price"/>
<fmt:message bundle="${locale}" key="text.positiveMark" var="positiveMark"/>
<fmt:message bundle="${locale}" key="text.negativeMark" var="negativeMark"/>
<fmt:message bundle="${locale}" key="text.withDiscount" var="withDiscount"/>
<fmt:message bundle="${locale}" key="text.orderTaxi" var="orderTaxi"/>

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
<div class="constant">
    <input type="hidden" id="car-number" value="${carNumber}"/>
    <input type="hidden" id="car-mark" value="${carMark}"/>
    <input type="hidden" id="tariff" value="${tariff}"/>
    <input type="hidden" id="positive-mark" value="${positiveMark}"/>
    <input type="hidden" id="negative-mark" value="${negativeMark}"/>
    <input type="hidden" id="price" value="${price}"/>
    <input type="hidden" id="with-discount" value="${withDiscount}"/>
    <input type="hidden" id="distance-mess" value="${distance}"/>
    <input type="hidden" id="duration-mess" value="${duration}"/>
    <input type="hidden" id="order-taxi" value="${orderTaxi}"/>

</div>
<div class="back">
    <div class="container">
        <div class="form-input">
            <h2 class="form-input-heading">${orderTaxi}</h2>
            <input type="text" id="start-address" class="form-control" placeholder="${startAddress}"/>
            <input type="text" id="end-address" class="form-control" placeholder="${endAddress}"/>

            <div class="error" id="negative-balance">
                <p>${negativeBalance}</p>
            </div>
            <div class="error" id="coordinate-not-valid">
                <p>${coordinateNotValid}</p>
            </div>
            <div class="error" id="wrong-distance">
                <p>${wrongDistance}</p>
            </div>
            <div class="error" id="order-exist">
                <p>${orderExist}</p>
            </div>
            <div class="error" id="driver-not-exist">
                <p>${driverNotExist}</p>
            </div>
            <div class="error" id="driver-not-suitable">
                <p>${driverNotSuitable}</p>
            </div>
            <div class="error" id="driver-eq-passenger">
                <p>${driverEqPassenger}</p>
            </div>
            <div class="correct" id="order-correct">
                <p>${orderCorrect}</p>
            </div>


            <div id="distance">
                <input type="text" id="distance-val" class="form-control" readonly/>
            </div>

            <div id="duration">
                <input type="text" id="duration-val" class="form-control" readonly/>
            </div>

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