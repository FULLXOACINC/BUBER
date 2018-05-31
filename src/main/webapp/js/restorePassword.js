function hideAllMessage() {
    $('#all-correct').hide();
    $('#not-valid-login').hide();
    $('#login-not-exist').hide();
    $('#not-valid-password').hide();
    $('#password-not-eq').hide();
}

$(document).ready(function () {
    hideAllMessage();
    var restorePasswordFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "restore-password",
                login: $('#login').val(),
                password: $('#password').val(),
                repeatPassword: $('#repeatPassword').val()
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
                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#restore-password').click(restorePasswordFun);
});