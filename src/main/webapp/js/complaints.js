var rideIdMessage;
var complaintId;
var complaintIdMessage;
var index = 0;

function findCurrentComplaint(id) {
    $.ajax({
        type: "POST",
        url: '/AJAXController',
        data: {
            command: "find-unaccepted-complaint",
            index: id
        },
        success: function (response) {
            if (!response['error']) {
                if (!response['complaintsEmpty']) {
                    $('#ride-id').val(rideIdMessage + ": " + response['rideId']);
                    $('#person-login').val(response['complaintPersonLogin']);
                    $('#complaint-text').text(response['complaintText']);
                    $('#complaint-id').val(complaintIdMessage + ": " + response['complaintId']);
                    complaintId = response['complaintId'];
                    index = response['index'];
                } else {
                    $('#no-complaint').show();
                    $('#complaint').hide();
                    console.log(response['complaintsEmpty']);
                }
            } else {
                console.log(response['error']);
            }

        },
        error: function (exception) {
            console.log(exception);
        }
    });
}

$(document).ready(function () {
    $('#no-complaint').hide();
    rideIdMessage = $('#ride-id-mess').val();
    complaintIdMessage = $('#complaint-id-mess').val();
    findCurrentComplaint(0);
    var acceptComplaintFun = function () {
        if (complaintId == null) {
            console.log("complaintId is null");
            return;
        }
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "accept-complaint",
                id: complaintId
            },
            success: function (response) {
                if (!response['error']) {
                    findCurrentComplaint(index);
                } else {
                    console.log(response['error']);
                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    var nextComplaintFun = function () {
        findCurrentComplaint(index + 1);
    };
    var prevComplaintFun = function () {
        findCurrentComplaint(index - 1);
    };
    $("#accept").click(acceptComplaintFun);
    $("#next").click(nextComplaintFun);
    $("#prev").click(prevComplaintFun);

});