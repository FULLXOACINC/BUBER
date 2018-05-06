function hideAllMessage() {
    $('#all-correct').hide();
    $('#car-number-error').hide();
    $('#document-id-error').hide();
    $('#car-mark-error').hide();
    $('#login-not-exist-error').hide();
    $('#driver-exist-error').hide();
}

$(document).ready(function () {
    var signUpFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "sign-up-driver",
                login: $('#login').val(),
                carNumber: $('#carNumber').val(),
                documentId: $('#documentId').val(),
                carMark: $('#carMark').val(),
            },
            success: function (response) {
                hideAllMessage();
                if (response['allCorrect']) {
                    $('#all-correct').show();
                } else {
                    if (response['carNumberError']) {
                        $('#car-number-error').show();
                    }
                    if (response['documentIdError']) {
                        $('#document-id-error').show();
                    }
                    if (response['carMarkError']) {
                        $('#car-mark-error').show();
                    }
                    if (response['loginNotExistError']) {
                        $('#login-not-exist-error').show();
                    }
                    if (response['driverExistError']) {
                        $('#driver-exist-error').show();
                    }

                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#sign-up-driver').click(signUpFun);
});