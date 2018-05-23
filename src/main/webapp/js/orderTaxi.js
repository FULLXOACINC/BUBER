var map;
var startMarker;
var endMarker;
var directionsService;
var directionsDisplay;
var rendererOptions;

var start;

var drivers = [];

var distance;

var startAddress;
var endAddress;


$(document).ready(function () {
    $('#duration').hide();
    $('#distance').hide();
    var selectStartEndAddressFun = function () {
        if (!map) {
            return;
        }
        if ($('#start-address').val() === startAddress && $('#end-address').val() === endAddress) {
            return;
        }
        clearMap();
        $('#duration').hide();
        $('#distance').hide();
        startAddress = $('#start-address').val();
        endAddress = $('#end-address').val();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "geo-decode-raid-start-end",
                startAddress: $('#start-address').val(),
                endAddress: $('#end-address').val()
            },
            success: function (response) {
                if (!response['error']) {
                    if (response['decodeProblem']) {
                        console.log(response['decodeProblem']);
                        return;
                    }
                    var startLng = response['start']['lng'];
                    var startLat = response['start']['lat'];
                    var endLng = response['end']['lng'];
                    var endLat = response['end']['lat'];

                    $.ajax({
                            type: "POST",
                            url: '/AJAXController',
                            data: {
                                command: "find-distance-info",
                                startLat: startLat,
                                startLng: startLng,
                                endLat: endLat,
                                endLng: endLng
                            },
                            success: function (response) {
                                if (!response['error'] && !response['findProblem'] && !response['notValidCoordinate']) {
                                    $('#duration').show();
                                    $('#distance').show();
                                    $('#duration-val').text(response['distance']);
                                    $('#duration-val').text(response['duration']);

                                    distance = response['distance'];

                                    $.ajax({
                                        type: "POST",
                                        url: '/AJAXController',
                                        data: {
                                            command: "find-suitable-drivers",
                                            lat: startLat,
                                            lng: startLng
                                        },
                                        success: function (response) {
                                            if (!response['error'] && !response['notValidCoordinate']) {
                                                response["drivers"].forEach(function (driver) {
                                                    placeDriver(driver["firstName"], driver["lastName"], driver["lat"], driver["lng"], driver["login"], driver["positiveMark"], driver["negativeMark"], driver["tariff"], driver["drivers"], driver["carNumber"], driver["carMark"])
                                                });
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
                                                console.log(response);
                                            }
                                        },
                                        error: function (exception) {
                                            console.log(exception);
                                        }
                                    });


                                } else {
                                    console.log(response);
                                }
                            }
                            ,
                            error: function (exception) {
                                console.log(exception);
                            }
                        }
                    );

                } else {
                    console.log(response['error']);
                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#select-addresses').click(selectStartEndAddressFun);
    $('#clear-map').click(clearMap);

});

function clearMap() {
    startAddress = null;
    endAddress = null;
    $('#distance').empty();
    $('#duration').empty();
    if (startMarker != null) {
        startMarker.setMap(null);
        startMarker=null;
    }
    if (endMarker != null) {
        endMarker.setMap(null);
        endMarker=null;

    }
    if (directionsDisplay != null) {
        directionsDisplay.setMap(null);
    }

    drivers.forEach(function (driver) {
        driver.setMap(null);
    });
    drivers = [];
}

function initMap() {
    directionsService = new google.maps.DirectionsService;
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 11,
        center: new google.maps.LatLng(53.90453979999999, 27.5615344)
    });
    rendererOptions = {
        map: map,
        suppressMarkers: true,
        dragable: true,
        polylineOptions: {
            strokeWeight: 4,
            strokeOpacity: 1,
            strokeColor: "#1d1e1f"
        }
    };
    map.addListener('click', function (e) {
        if (startMarker == null) {
            startMarker = new google.maps.Marker({
                position: e.latLng,
                map: map,
                icon: '/img/startMarker.svg'
            });
            start=e.latLng;
            startMarker.setMap(map);
        }else {
            if (endMarker == null) {
                startMarker.setMap(null);
                var startLng = start.lng();
                var startLat = start.lat();
                var endLng = e.latLng.lng();
                var endLat = e.latLng.lat();

                $.ajax({
                        type: "POST",
                        url: '/AJAXController',
                        data: {
                            command: "find-distance-info",
                            startLat: startLat,
                            startLng: startLng,
                            endLat: endLat,
                            endLng: endLng
                        },
                        success: function (response) {
                            if (!response['error'] && !response['findProblem'] && !response['notValidCoordinate']) {
                                $('#duration').show();
                                $('#distance').show();
                                $('#duration-val').text(response['distance']);
                                $('#duration-val').text(response['duration']);

                                distance = response['distance'];

                                $.ajax({
                                    type: "POST",
                                    url: '/AJAXController',
                                    data: {
                                        command: "find-suitable-drivers",
                                        lat: startLat,
                                        lng: startLng
                                    },
                                    success: function (response) {
                                        if (!response['error'] && !response['notValidCoordinate']) {
                                            response["drivers"].forEach(function (driver) {
                                                placeDriver(driver["firstName"], driver["lastName"], driver["lat"], driver["lng"], driver["login"], driver["positiveMark"], driver["negativeMark"], driver["tariff"], driver["drivers"], driver["carNumber"], driver["carMark"])
                                            });
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
                                            console.log(response);
                                        }
                                    },
                                    error: function (exception) {
                                        console.log(exception);
                                    }
                                });


                            } else {
                                console.log(response);
                            }
                        }
                        ,
                        error: function (exception) {
                            console.log(exception);
                        }
                    }
                );
            }
        }

    });

}


function placeDriver(firstName, LastName, lat, lng, login, positiveMark, negativeMark, tariff, price, carNumber, carMark) {
    console.log(lat + " " + lng);
    var marker = new google.maps.Marker({
        position: {lat: lat, lng: lng},
        map: map,
        icon: '/img/carMarker.svg'
    });

    var contentString = '<div id="content">' +
        '<div id="siteNotice">' +
        '</div>' +
        '<h1 id="firstHeading" class="firstHeading">' + firstName + ' ' + LastName + '</h1>' +
        '<div id="bodyContent">' +
        '<p><b>' + carNumber + '</b></p>' +
        '<p><b>' + carMark + '</b></p>' +
        '<p><b>' + positiveMark + '</b></p>' +
        '<p><b>' + negativeMark + '</b></p>' +
        '<p><b>' + tariff + '</b></p>' +
        '<p><b>' + price + '</b></p>' +
        '<button id="' + carNumber + '">Test</button>';

    var infoWindow = new google.maps.InfoWindow({
        content: contentString
    });
    var secondClick;
    google.maps.event.addListener(infoWindow, 'closeclick', function () {
        secondClick = false;
    });

    function toggleBounce() {
        if (marker.getAnimation() !== null) {
            marker.setAnimation(null);
        }
    }

    marker.addListener('click', toggleBounce);
    marker.setMap(map);
    drivers.push(marker);
    marker.addListener('click', function () {
        if (!secondClick) {
            infoWindow.open(marker.get('map'), marker);
            console.log(carNumber);
            $('#' + carNumber).click(function () {
                console.log(login);
            });
            secondClick = true;
        } else {
            infoWindow.close();
            secondClick = false;
        }
    });
}


