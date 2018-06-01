function hideAllMessage() {
    $('#car-number-not-valid').hide();
    $('#document-id-not-valid').hide();
    $('#car-mark-not-valid').hide();
    $('#driver-not-exist').hide();
    $('#car-number-exist').hide();
    $('#document-id-exist').hide();
    $('#tariff-not-valid').hide();
    $('#all-correct').hide();

}

$(document).ready(function () {
    var updateFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "update-driver",
                login: $('#login').val(),
                carNumber: $('#car-number').val(),
                documentId: $('#document-id').val(),
                carMark: $('#car-mark').val(),
                tariff: $('#tariff').val()
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
                    if (response['error']) {
                        viewServerError();
                    }

                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#update-driver').click(updateFun);
});