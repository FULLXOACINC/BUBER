<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.carNumber" var="carNumber"/>
<fmt:message bundle="${locale}" key="text.carMark" var="carMark"/>
<fmt:message bundle="${locale}" key="text.currentRide" var="currentRide"/>
<fmt:message bundle="${locale}" key="text.acceptStart" var="acceptStart"/>
<fmt:message bundle="${locale}" key="text.acceptEnd" var="acceptEnd"/>
<fmt:message bundle="${locale}" key="text.waitDriverStart" var="waitDriverStart"/>
<fmt:message bundle="${locale}" key="text.rideNotFound" var="rideNotFound"/>
<fmt:message bundle="${locale}" key="text.waitDriverEnd" var="waitDriverEnd"/>
<fmt:message bundle="${locale}" key="text.refuse" var="refuse"/>
<fmt:message bundle="${locale}" key="text.rideId" var="rideId"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.rideEndCorrect" var="rideEndCorrect"/>


<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/currentUserRide.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY&callback=initMap">
    </script>
</head>
<body>
<input type="hidden" id="car-number-mess" value="${carNumber}"/>
<input type="hidden" id="car-mark-mess" value="${carMark}"/>
<input type="hidden" id="ride-id-mess" value="${rideId}"/>
<input type="hidden" id="phone-number-mess" value="${phoneNumber}"/>


<div class="back">
    <div class="container">
        <div class="form-input">
            <h2 class="form-input-heading">${currentRide}</h2>
            <div class="correct" id="ride-end-correct">
                <p>${rideEndCorrect}</p>
            </div>
            <div id="ride">
                <input type="text" class="form-control" id="ride-id" readonly/>
                <input type="text" class="form-control" id="driver-name" readonly/>
                <input type="text" class="form-control" id="car-number" readonly/>
                <input type="text" class="form-control" id="car-mark" readonly/>
                <input type="text" class="form-control" id="phone-number" readonly/>

                <input type="submit" class="btn btn-lg btn-primary btn-block ride-accept" id="accept-start"
                       value="${acceptStart}"/>
                <input type="submit" class="btn btn-lg btn-primary btn-block ride-accept" id="accept-end"
                       value="${acceptEnd}"/>
                <input type="text" class="form-control ride-accept" id="wait-start" value="${waitDriverStart}"
                       readonly/>
                <input type="text" class="form-control ride-accept" id="wait-end" value="${waitDriverEnd}"
                       readonly/>
                <input type="submit" class="btn btn-lg btn-primary btn-block" id="refuse" value="${refuse}"/>

                <div id="map"></div>
            </div>
            <div id="ride-not-found">
                <input type="text" class="form-control" value="${rideNotFound}" readonly/>
            </div>
        </div>
    </div>
</div>
<ctg:footer auth="Alex Zhuk" description="Created for EPAM System java traning"/>
</body>
</html>