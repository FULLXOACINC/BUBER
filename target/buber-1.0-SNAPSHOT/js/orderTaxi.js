var map;
var startMarker;
var endMarker;
var directionsService;
var directionsDisplay;
var index = 0;


$(document).ready(function () {

});


function initMap() {
    directionsService = new google.maps.DirectionsService;
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 11,
        center: new google.maps.LatLng(53.90453979999999, 27.5615344),
        mapTypeId: 'terrain'
    });
    var rendererOptions = {
        map: map,
        suppressMarkers: true,
        dragable: true,
        polylineOptions: {
            strokeWeight: 4,
            strokeOpacity: 1,
            strokeColor: "#666666"
        }
    };
    directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);


    directionsDisplay.setMap(map);
    $('#btn').click(function () {
        var start = new google.maps.LatLng(53.90453979999999, 27.5615344);
        var end = new google.maps.LatLng(53.90454, 27.63164);

        var waypts = [];
        waypts.push({
            location: start,
            stopover: true
        });

        waypts.push({
            location: end,
            stopover: true
        });


        directionsService.route({
            origin: start,
            destination: end,
            waypoints: waypts,
            optimizeWaypoints: true,
            travelMode: 'DRIVING'
        }, function (response, status) {
            if (status === 'OK') {
                console.log(response);
                var route1 = response.routes[0].legs[0];
                var route2 = response.routes[0].legs[response.routes[0].legs.length - 1];
                var marker1 = new google.maps.Marker({
                    position: route1.start_location,
                    map: map,
                    icon: '/img/startMarker.svg'
                });
                marker1.setMap(map);
                var marker2 = new google.maps.Marker({
                    position: route2.end_location,
                    map: map,
                    icon: '/img/endMarker.svg'
                });
                marker2.setMap(map);
                directionsDisplay.setDirections(response);
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });
    });

    map.addListener('click', function (e) {
        var marker = new google.maps.Marker({
            position: e.latLng,
            map: map,
            icon: '/img/carMarker.svg',
            driverLogin: "san91130324" + index
        });
        var contentString = '<div id="content">' +
            '<div id="siteNotice">' +
            '</div>' +
            '<h1 id="firstHeading" class="firstHeading">Uluru</h1>' +
            '<div id="bodyContent">' +
            '<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large ' +
            'sandstone rock formation in the southern part of the ' +
            'Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) ' +
            'south west of the nearest large town, Alice Springs; 450&#160;km ' +
            '(280&#160;mi) by road. Kata Tjuta and Uluru are the two major ' +
            'features of the Uluru - Kata Tjuta National Park. Uluru is ' +
            'sacred to the Pitjantjatjara and Yankunytjatjara, the ' +
            'Aboriginal people of the area. It has many springs, waterholes, ' +
            'rock caves and ancient paintings. Uluru is listed as a World ' +
            'Heritage Site.</p>' +
            '<p>Attribution: Uluru, <a href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">' +
            'https://en.wikipedia.org/w/index.php?title=Uluru</a> ' +
            '(last visited June 22, 2009).</p>' +
            '</div>' +
            '</div>' +
            '<button id="' + marker.driverLogin + '">Test</button>';

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
        marker.addListener('click', function () {
            if (!secondClick) {
                infoWindow.open(marker.get('map'), marker);
                $('#' + marker.driverLogin).click(function () {
                    console.log(marker.driverLogin)
                });
                secondClick = true;
            } else {
                infoWindow.close();
                secondClick = false;
            }
        });
        index++;
    });
}



