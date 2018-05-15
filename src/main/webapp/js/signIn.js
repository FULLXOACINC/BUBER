function hideAllMessage() {
    $('#login-not-valid-error').hide();
    $('#login-not-exist-error').hide();
    $('#login-password-not-eq-error').hide();
    $('#banned-error').hide();

}

$(document).ready(function () {
    var signInFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "sign-in",
                login: $('#login').val(),
                password: $('#password').val()
            },
            success: function (response) {
                if (response['redirectPage']) {
                    $(location).attr('href', response['redirectPage']);
                }
                $('#password').val('');
                if (response['signInValidError']) {
                    $('#login-not-valid-error').show();
                }
                if (response['signInExistError']) {
                    $('#login-not-exist-error').show();
                }
                if (response['signInPasswordError']) {
                    $('#login-password-not-eq-error').show();
                }


            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#enter').click(signInFun);
});