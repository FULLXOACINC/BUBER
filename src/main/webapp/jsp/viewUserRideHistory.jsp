<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="properties/text" var="locale" scope="session"/>
<fmt:message bundle="${locale}" key="text.lang" var="lang"/>
<fmt:message bundle="${locale}" key="text.buber" var="buber"/>
<fmt:message bundle="${locale}" key="text.rideId" var="rideId"/>

<fmt:message bundle="${locale}" key="text.driverLogin" var="driverLogin"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.price" var="price"/>
<fmt:message bundle="${locale}" key="text.noRide" var="noRide"/>

<fmt:message bundle="${locale}" key="text.next" var="next"/>
<fmt:message bundle="${locale}" key="text.previous" var="previous"/>

<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/viewUserRideHistory.js"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY&callback=initMap">
    </script>
</head>
<body>

<div class="back">
    <div class="container">
        <div class="form-input">
            <div id="ride">
                <input type='hidden' id="ride-id-mess" value='${rideId}'>
                <input type='hidden' id="driver-login-mess" value='${driverLogin}'>
                <input type='hidden' id="driver-phone-number-mess" value='${phoneNumber}'>
                <input type='hidden' id="price-mess" value='${price}'>

                <ul class="pagination">
                    <input type='submit' class="page-link" id='prev' value="${previous}">
                    <input type='submit' class="page-link" id='next' value="${next}">
                </ul>

                <input type="text" id="ride-id" class="form-control" readonly/>
                <input type="text" id="driver-login" class="form-control" readonly/>
                <input type="text" id="driver-name" class="form-control" readonly/>
                <input type="text" id="driver-phone-number" class="form-control" readonly/>
                <input type="text" id="price" class="form-control" readonly/>

                <div id="map"></div>
            </div>
            <div id="no-ride">
                <input type="text" class="form-control" value="${noRide}" readonly/>
            </div>
        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>