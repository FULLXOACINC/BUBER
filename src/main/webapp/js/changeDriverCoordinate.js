var map;
var address;
var point;

function hideAllMessage() {
    $('#all-correct').hide();
    $('#repeat-pls').hide();
}

function changeCurrentCoordinateFun() {
    if (point == null) {
        return;
    }
    hideAllMessage();
    $.ajax({
        type: "POST",
        url: '/AJAXController',
        data: {
            command: "update-current-driver-coordinate",
            lat: point.getPosition().lat(),
            lng: point.getPosition().lng()
        },
        success: function (response) {
            if (response['allCorrect']) {
                $('#all-correct').show()
            } else {
                if (response['error']) {
                    viewServerError();
                } else {
                    $('#repeat-pls').show();
                }
            }

        },
        error: function () {
            viewConnectionError();
        }
    });

}

$(document).ready(function () {
    var selectAddressFun = function () {
        if (!map) {
            return;
        }
        if ($('#current-address').val() === address) {
            return;
        }
        clearMap();
        address = $('#current-address').val();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "geo-decode-address",
                currentAddress: $('#current-address').val()
            },
            success: function (response) {
                if (!response['error']) {
                    if (response['decodeProblem']) {
                        console.log(response['decodeProblem']);
                        return;
                    }
                    var currentLng = response['current']['lng'];
                    var currentLat = response['current']['lat'];
                    point = new google.maps.Marker({
                        position: new google.maps.LatLng(currentLat, currentLng),
                        map: map,
                        icon: '/img/carMarker.svg'
                    });
                    point.setMap(map);

                } else {
                    console.log(response['error']);
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#select-address').click(selectAddressFun);
    $('#accept-coordinate').click(changeCurrentCoordinateFun);
    $('#clear-map').click(clearMap);

});

function clearMap() {

    if (point != null) {
        point.setMap(null);
        point = null;
    }

}

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 11,
        center: new google.maps.LatLng(53.90453979999999, 27.5615344),
        draggableCursor: 'crosshair'
    });

    map.addListener('click', function (e) {
        if (point == null) {
            point = new google.maps.Marker({
                position: e.latLng,
                map: map,
                icon: '/img/carMarker.svg'
            });
            point.setMap(map);
            address = null;
        }
    });

}


