function hideAllMessage() {
    $('#all-correct').hide();
    $('#not-valid-login-error').hide();
    $('#login-exist-error').hide();
    $('#phone-number-exist-error').hide();
    $('#first-name-error').hide();
    $('#second-name-error').hide();
    $('#not-valid-password-error').hide();
    $('#password-not-eq-error').hide();
    $('#birth-day-error').hide();
    $('#not-valid-phone-number-error').hide();
}

$(document).ready(function () {
    var signUpFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "sign-up-user",
                login: $('#login').val(),
                firstName: $('#firstName').val(),
                secondName: $('#secondName').val(),
                password: $('#password').val(),
                repeatPassword: $('#repeatPassword').val(),
                birthDay: $('#birthDay').val(),
                phoneNumber: $('#phoneNumber').val()
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#all-correct').show();
                } else {
                    if (response['notValidLoginError']) {
                        $('#not-valid-login-error').show();
                    }
                    if (response['loginExistError']) {
                        $('#login-exist-error').show();
                    }
                    if (response['phoneNumberExistError']) {
                        $('#phone-number-exist-error').show();
                    }
                    if (response['firstNameError']) {
                        $('#first-name-error').show();
                    }
                    if (response['secondNameError']) {
                        $('#second-name-error').show();
                    }
                    if (response['notValidPasswordError']) {
                        $('#not-valid-password-error').show();
                    }
                    if (response['passwordNotEqError']) {
                        $('#password-not-eq-error').show();
                    }
                    if (response['birthDayError']) {
                        $('#birth-day-error').show();
                    }
                    if (response['notValidPhoneNumberError']) {
                        $('#not-valid-phone-number-error').show();
                    }

                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#sign-up').click(signUpFun);
});