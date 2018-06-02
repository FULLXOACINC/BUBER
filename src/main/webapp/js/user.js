function hideAllMessage() {
    $('#all-correct').hide();
}

$(document).ready(function () {
    var changeDiscountFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "change-discount",
                login: $('#login-val').val(),
                discount: $('#discount').val()
            },
            success: function (response) {
                if (!response['error']) {
                    $('#all-correct').show();
                } else {
                    viewServerError();
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#change-discount').click(changeDiscountFun);
});