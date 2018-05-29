$(document).ready(function () {
    var workingFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "driver-set-working-status"
            },
            success: function (response) {
                if (!response['allCorrect']) {
                    console.log(response)
                }
            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    var notWorkingFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "driver-set-not-working-status"
            },
            success: function (response) {
                if (!response['allCorrect']) {
                    console.log(response)
                }
            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#is-working').click(workingFun);
    $('#is-not-working').click(notWorkingFun);

});