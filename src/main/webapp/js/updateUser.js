function hideAllMessage() {
    $('#phone-number-exist').hide();
    $('#phone-number-not-valid').hide();
    $('#first-name-not-valid').hide();
    $('#last-name-not-valid').hide();
    $('#all-correct').hide();

}

$(document).ready(function () {
    hideAllMessage();
    var updateFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "update-user",
                firstName: $('#first-name').val(),
                lastName: $('#last-name').val(),
                phoneNumber: $('#phone-number').val()
            },
            success: function (response) {

                if (response['allCorrect']) {
                    $('#all-correct').show();
                } else {
                    if (response['carNumberNotValid']) {
                        $('#car-number-not-valid').show();
                    }
                    if (response['documentIdNotValid']) {
                        $('#document-id-not-valid').show();
                    }
                    if (response['carMarkNotValid']) {
                        $('#car-mark-not-valid').show();
                    }
                    if (response['driverNotExist']) {
                        $('#driver-not-exist').show();
                    }
                    if (response['carNumberExist']) {
                        $('#car-number-exist').show();
                    }
                    if (response['documentIdExist']) {
                        $('#document-id-exist').show();
                    }
                    if (response['tariffNotValid']) {
                        $('#tariff-not-valid').show();
                    }

                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#update-user').click(updateFun);
});