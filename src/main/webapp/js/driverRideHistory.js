var rideIdMessage;
var passengerLoginMessage;
var phoneNumberMessage;
var earnedMoneyMessage;

var directionsDisplay;
var startMarker;
var endMarker;

var index = 0;

function findCurrentRide(id) {
    $.ajax({
        type: "POST",
        url: '/AJAXController',
        data: {
            command: "find-driver-ride-history",
            index: id
        },
        success: function (response) {
            clearMap();
            if (!response['error']) {
                if (!response['rideNotFound']) {
                    var startLat = response['start']['lat'];
                    var startLng = response['start']['lng'];
                    var endLat = response['end']['lat'];
                    var endLng = response['end']['lng'];

                    $('#ride-id').val(rideIdMessage + ": " + response['rideId']);
                    $('#passenger-name').val(response['firstName'] + " " + response['lastName']);
                    $('#passenger-login').val(passengerLoginMessage + ": " + response['passengerLogin']);
                    $('#passenger-phone-number').val(phoneNumberMessage + ": " + response['passengerPhoneNumber']);
                    $('#earned-money').val(earnedMoneyMessage + ": " + response['earnedMoney']);


                    var startCoordinate = new google.maps.LatLng(startLat, startLng);
                    var endCoordinate = new google.maps.LatLng(endLat, endLng);

                    var waypts = [];
                    waypts.push({
                        location: startCoordinate,
                        stopover: true
                    });

                    waypts.push({
                        location: endCoordinate,
                        stopover: true
                    });


                    directionsService.route({
                        origin: startCoordinate,
                        destination: endCoordinate,
                        waypoints: waypts,
                        optimizeWaypoints: true,
                        travelMode: 'DRIVING'
                    }, function (response, status) {
                        if (status === 'OK') {

                            var route1 = response.routes[0].legs[0];
                            var route2 = response.routes[0].legs[response.routes[0].legs.length - 1];
                            startMarker = new google.maps.Marker({
                                position: route1.start_location,
                                map: map,
                                icon: '/img/startMarker.svg'
                            });
                            endMarker = new google.maps.Marker({
                                position: route2.end_location,
                                map: map,
                                icon: '/img/endMarker.svg'
                            });

                            startMarker.setMap(map);
                            endMarker.setMap(map);

                            directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
                            directionsDisplay.setMap(map);
                            directionsDisplay.setDirections(response);
                        } else {
                            console.log('Directions request failed due to ' + status);
                        }


                    });

                } else {
                    $('#no-ride').show();
                    $('#ride').hide();
                    console.log(response['rideNotFound']);
                }
            } else {
                viewServerError();
            }

        },
        error: function () {
            viewConnectionError();
        }
    });
}

$(document).ready(function () {
    $('#no-ride').hide();
    rideIdMessage = $('#ride-id-mess').val();
    passengerLoginMessage = $('#passenger-login-mess').val();
    phoneNumberMessage = $('#passenger-phone-number-mess').val();
    earnedMoneyMessage = $('#earned-money-mess').val();

    var nextComplaintFun = function () {
        findCurrentRide(index + 1);
    };
    var prevComplaintFun = function () {
        findCurrentRide(index - 1);
    };
    $("#next").click(nextComplaintFun);
    $("#prev").click(prevComplaintFun);

});

function initMap() {

    directionsService = new google.maps.DirectionsService;
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 11,
        center: new google.maps.LatLng(53.90453979999999, 27.5615344),
        draggableCursor: 'crosshair'
    });
    rendererOptions = {
        map: map,
        suppressMarkers: true,
        dragable: true,
        polylineOptions: {
            strokeWeight: 4,
            strokeOpacity: 1,
            strokeColor: "#000000"
        }
    };
    findCurrentRide(0);

}

function clearMap() {
    if (startMarker != null) {
        startMarker.setMap(null);
        startMarker = null;
    }
    if (endMarker != null) {
        endMarker.setMap(null);
        endMarker = null;

    }
    if (directionsDisplay != null) {
        directionsDisplay.setMap(null);
        directionsDisplay = null;
    }

}