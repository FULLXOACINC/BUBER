function hideAllMessage() {
    $('#login-not-valid').hide();
    $('#login-not-exist').hide();
    $('#login-password-not-eq').hide();
}

$(document).ready(function () {
    var signInFun = function () {
        hideAllMessage();
        $('#banned-error').hide();

        var login = $('#login').val();
        var loginRegExp = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,6}$/i;
        if (!loginRegExp.test(login)) {
            $('#login-not-valid').show();
            return;
        }

        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "sign-in",
                login: login,
                password: $('#password').val()
            },
            success: function (response) {
                if (response['redirectPage']) {
                    $(location).attr('href', response['redirectPage']);
                }
                $('#password').val('');
                if (response['loginNotValid']) {
                    $('#login-not-valid').show();
                }
                if (response['loginNotExist']) {
                    $('#login-not-exist').show();
                }
                if (response['passwordNotMatchLogin']) {
                    $('#login-password-not-eq').show();
                }
                if (response['error']) {
                    viewServerError();
                }


            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#enter').click(signInFun);
});