function hideAllMessage() {
    $('#all-correct').hide();
    $('#car-number-error').hide();
    $('#document-id-error').hide();
    $('#car-mark-error').hide();
    $('#driver-not-exist-error').hide();
    $('#car-number-exist-error').hide();
    $('#document-id-exist-error').hide();
}

$(document).ready(function () {
    var updateFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "update-driver",
                login: $('#login').val(),
                carNumber: $('#car-number').val(),
                documentId: $('#document-id').val(),
                carMark: $('#car-mark').val()
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
                    if (response['driverNotExistError']) {
                        $('#driver-not-exist-error').show();
                    }
                    if (response['carNumberExistError']) {
                        $('#car-number-exist-error').show();
                    }
                    if (response['documentIdExistError']) {
                        $('#document-id-exist-error').show();
                    }

                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#update-driver').click(updateFun);
});