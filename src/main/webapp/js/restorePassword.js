function hideAllMessage() {
    $('#all-correct').hide();
    $('#not-valid-login').hide();
    $('#login-not-exist').hide();
    $('#not-valid-password').hide();
    $('#password-not-eq').hide();
}

$(document).ready(function () {
    var restorePasswordFun = function () {
        hideAllMessage();
        var login = $('#login').val();
        var password = $('#password').val();
        var repeatPassword = $('#repeat-password').val();

        var loginRegExp = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,6}$/i;
        var passwordRegExp = /[\w\d!@#$%^&*+_-~]{6,35}/;
        var hasProblem = false;
        if (!loginRegExp.test(login)) {
            $('#not-valid-login').show();
            hasProblem = true;
        }
        if (!passwordRegExp.test(password)) {
            $('#not-valid-password').show();
            hasProblem = true;
        }
        if (repeatPassword !== password) {
            $('#password-not-eq').show();
            hasProblem = true;
        }
        if (hasProblem) {
            return
        }
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "restore-password",
                login: login,
                password: password,
                repeatPassword: repeatPassword
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#all-correct').show();
                } else {
                    if (response['notValidLogin']) {
                        $('#not-valid-login').show();
                    }
                    if (response['loginNotExist']) {
                        $('#login-not-exist').show();
                    }
                    if (response['passwordNotValid']) {
                        $('#not-valid-password').show();
                    }
                    if (response['passwordNotEq']) {
                        $('#password-not-eq').show();
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
    $('#restore-password').click(restorePasswordFun);
});