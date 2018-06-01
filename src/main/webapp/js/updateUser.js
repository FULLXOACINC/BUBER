function hideAllMessage() {
    $('#phone-number-exist').hide();
    $('#phone-number-not-valid').hide();
    $('#first-name-not-valid').hide();
    $('#last-name-not-valid').hide();
    $('#all-correct').hide();

}

$(document).ready(function () {
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
                    if (response['phoneNumberExist']) {
                        $('#phone-number-exist').show();
                    }
                    if (response['firstNameNotValid']) {
                        $('#first-name-not-valid').show();
                    }
                    if (response['lastNameNotValid']) {
                        $('#last-name-not-valid').show();
                    }
                    if (response['phoneNumberNotValid']) {
                        $('#phone-number-not-valid').show();
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
    $('#update-user').click(updateFun);
});