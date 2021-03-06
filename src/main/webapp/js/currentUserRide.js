var map;

var carNumberMessage;
var carMarkMessage;
var rideIdMessage;
var phoneNumberMessage;

var rideId;
var type;

$(document).ready(function () {
    $('#ride-end-correct').hide();
    carNumberMessage = $('#car-number-mess').val();
    carMarkMessage = $('#car-mark-mess').val();
    rideIdMessage = $('#ride-id-mess').val();
    phoneNumberMessage = $('#phone-number-mess').val();

    var refuseRideFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "refuse-ride-passenger"
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#ride').hide();
                    $('#evaluation').show();
                    $('#complaint-div').show();
                    $('#ride-not-found').hide();
                } else {
                    if (response['error']) {
                        viewServerError();
                    } else {
                        console.log(response);
                    }
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    var acceptStartRideFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "accept-start-ride-passenger"
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#accept-start').hide();
                    $('#wait-end').show();
                } else {
                    if (response['error']) {
                        viewServerError();
                    } else {
                        console.log(response);
                    }
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    var acceptEndRideFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "accept-end-ride-passenger"
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#ride').hide();
                    $('#evaluation').show();
                    $('#complaint-div').show();

                    $('#ride-not-found').hide();
                } else {
                    console.log(response);
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    var sendComplaintFun = function () {
        if (rideId == null) {
            return;
        }
        if ($('#complaint').val() === '') {
            return;
        }
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "complaint-passenger",
                rideId: rideId,
                complaint: $('#complaint').val()
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#complaint-div').hide();
                } else {
                    console.log(response);
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#refuse').click(refuseRideFun);
    $('#accept-start').click(acceptStartRideFun);
    $('#accept-end').click(acceptEndRideFun);
    $('#positive-mark').click(function () {
        type = "positive";
        evaluationDriver();
    });
    $('#negative-mark').click(function () {
        type = "negative";
        evaluationDriver();
    });
    $('#send-complaint').click(sendComplaintFun);
});

function initMap() {
    $.ajax({
        type: "POST",
        url: '/AJAXController',
        data: {
            command: "find-ride-info-passenger"
        },
        success: function (response) {
            if (!response['error']) {
                if (!response['rideNotFound']) {
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

                    var startLat = response['start']['lat'];
                    var startLng = response['start']['lng'];
                    var endLat = response['end']['lat'];
                    var endLng = response['end']['lng'];

                    $('#ride-id').val(rideIdMessage + ": " + response['rideId']);
                    $('#driver-name').val(response['firstName'] + " " + response['lastName']);
                    $('#car-number').val(carNumberMessage + ": " + response['carNumber']);
                    $('#car-mark').val(carMarkMessage + ": " + response['carMark']);
                    $('#phone-number').val(phoneNumberMessage + ": " + response['phoneNumber']);
                    rideId = response['rideId'];

                    if (!response['isDriverStart']) {
                        $('#wait-start').show();
                    } else {
                        if (!response['isPassengerStart']) {
                            $('#accept-start').show();
                        } else {
                            if (!response['isDriverEnd']) {
                                $('#wait-end').show();
                            } else {
                                $('#accept-end').show();
                            }
                        }
                    }

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
                    $('#ride').hide();
                    $('#ride-not-found').show();
                }

            } else {
                console.log(response);
            }

        },
        error: function () {
            viewConnectionError();
        }
    });


}

function evaluationDriver() {
    if (rideId == null) {
        return;
    }
    $.ajax({
        type: "POST",
        url: '/AJAXController',
        data: {
            command: "evaluation-driver",
            rideId: rideId,
            type: type
        },
        success: function (response) {
            if (response['allCorrect']) {
                $('#evaluation').hide();

            } else {
                console.log(response);
            }

        },
        error: function () {
            viewConnectionError();
        }
    });
}
