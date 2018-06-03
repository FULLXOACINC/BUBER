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
<fmt:message bundle="${locale}" key="text.waitPassengerStart" var="waitPassengerStart"/>
<fmt:message bundle="${locale}" key="text.rideNotFound" var="rideNotFound"/>
<fmt:message bundle="${locale}" key="text.waitPassengerEnd" var="waitPassengerEnd"/>
<fmt:message bundle="${locale}" key="text.refuseDriver" var="refuseDriver"/>
<fmt:message bundle="${locale}" key="text.rideId" var="rideId"/>
<fmt:message bundle="${locale}" key="text.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${locale}" key="text.rideEndCorrect" var="rideEndCorrect"/>

<fmt:message bundle="${locale}" key="text.positiveEvaluation" var="positiveEvaluation"/>
<fmt:message bundle="${locale}" key="text.negativeEvaluation" var="negativeEvaluation"/>
<fmt:message bundle="${locale}" key="text.complaint" var="complaint"/>
<fmt:message bundle="${locale}" key="text.evaluationExist" var="evaluationExist"/>
<fmt:message bundle="${locale}" key="text.sendComplaint" var="sendComplaint"/>


<html>
<c:import url="${ pageContext.request.contextPath }/jsp/header.jsp"/>
<head>
    <title>${buber}</title>
    <script src="${pageContext.request.contextPath}/js/currentDriverRide.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDCd_w_dcctv7LlPuHYIn2dbpA74JSyaVY&callback=initMap">
    </script>
</head>
<body>
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
                <input type="text" class="form-control" id="passenger-name" readonly/>
                <input type="text" class="form-control" id="phone-number" readonly/>

                <input type="submit" class="btn btn-lg btn-primary btn-block ride-accept" id="accept-start"
                       value="${acceptStart}"/>
                <input type="submit" class="btn btn-lg btn-primary btn-block ride-accept" id="accept-end"
                       value="${acceptEnd}"/>
                <input type="text" class="form-control ride-accept" id="wait-start" value="${waitPassengerStart}"
                       readonly/>
                <input type="text" class="form-control ride-accept" id="wait-end" value="${waitPassengerEnd}"
                       readonly/>
                <input type="submit" class="btn btn-lg btn-primary btn-block" id="refuse" value="${refuseDriver}"/>

                <div id="map"></div>
            </div>
            <div id="complaint-div">
                <input type="text" class="form-control" value="${complaint}" readonly/>
                <textarea class="form-control" rows="5" id="complaint"></textarea>
                <input type="submit" class="btn btn-lg btn-primary btn-block" id="send-complaint"
                       value="${sendComplaint}"/>
            </div>

            <div id="ride-not-found">
                <input type="text" class="form-control" value="${rideNotFound}" readonly/>
            </div>
        </div>
    </div>
</div>
<c:import url="${ pageContext.request.contextPath }/jsp/footer.jsp"/>
</body>
</html>