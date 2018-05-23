function hideAllMessage() {
    $('#all-correct').hide();
    $('#car-number-error').hide();
    $('#document-id-error').hide();
    $('#car-mark-error').hide();
    $('#login-not-exist-error').hide();
    $('#driver-exist-error').hide();
    $('#car-number-exist-error').hide();
    $('#document-id-exist-error').hide();
    $('#tariff-error').hide();


}

$(document).ready(function () {
    var signUpFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "sign-up-driver",
                login: $('#login').val(),
                carNumber: $('#car-number').val(),
                documentId: $('#document-id').val(),
                carMark: $('#car-mark').val(),
                tariff:$('#tariff').val()
            },
            success: function (response) {

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
                    if (response['carNumberExistError']) {
                        $('#car-number-exist-error').show();
                    }
                    if (response['documentIdExistError']) {
                        $('#document-id-exist-error').show();
                    }
                    if (response['tariffError']) {
                        $('#tariff-error').show();
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