function hideAllMessage() {
    $('#all-correct').hide();
    $('#repeat-pls').hide();
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
                if (response['allCorrect']) {
                    $('#all-correct').show();
                } else {
                    if(response['error']){
                        viewServerError();

                    }else {
                        $('#repeat-pls').show();
                    }
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#change-discount').click(changeDiscountFun);
});