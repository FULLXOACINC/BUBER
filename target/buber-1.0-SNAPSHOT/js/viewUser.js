$(document).ready(function () {
    var changeDiscountFun=function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "change-discount",
                login:  $('#login').val(),
                discount: $('#discount').val()
            },
            success: function (response) {
                if (!response['error']) {
                    console.log("change discount correct");
                }else {
                    console.log(response['error']);
                }

            },
            error: function (jqXHR, exception) {
                console.log("bad");
                // Your error handling logic here..
            }
        });
    };
    $('#change-discount').click(changeDiscountFun);
});