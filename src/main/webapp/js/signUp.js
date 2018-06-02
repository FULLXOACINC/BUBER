function hideAllMessage() {
    $('#all-correct').hide();
    $('#not-valid-login').hide();
    $('#login-exist').hide();
    $('#phone-number-exist').hide();
    $('#first-name-not-valid').hide();
    $('#last-name-not-valid').hide();
    $('#not-valid-password').hide();
    $('#password-not-eq').hide();
    $('#birth-day-not-valid').hide();
    $('#not-valid-phone-number').hide();
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
                firstName: $('#first-name').val(),
                lastName: $('#last-name').val(),
                password: $('#password').val(),
                repeatPassword: $('#repeat-password').val(),
                birthDay: $('#birth-day').val(),
                phoneNumber: $('#phone-number').val()
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#all-correct').show();
                } else {
                    if (response['notValidLogin']) {
                        $('#not-valid-login').show();
                    }
                    if (response['loginExist']) {
                        $('#login-exist').show();
                    }
                    if (response['phoneNumberExist']) {
                        $('#phone-number-exist').show();
                    }
                    if (response['firstNameNotValid']) {
                        $('#first-name-not-valid').show();
                    }
                    if (response['lastNameNotValid']) {
                        $('#last-name-not-valid').show();
                    }
                    if (response['notValidPassword']) {
                        $('#not-valid-password').show();
                    }
                    if (response['passwordNotEq']) {
                        $('#password-not-eq').show();
                    }
                    if (response['birthDayNotValid']) {
                        $('#birth-day-not-valid').show();
                    }
                    if (response['phoneNumberNotValid']) {
                        $('#not-valid-phone-number').show();
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
    $('#sign-up').click(signUpFun);
});