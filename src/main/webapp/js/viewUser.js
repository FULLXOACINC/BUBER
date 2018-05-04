$(document).ready(function () {
    var setDiscountFun=function () {
        $.ajax({
            url: '/AJAXController',
            data: {
                command: "set-discount",
                pattern: $('#discount').val()
            },
            success: function (response) {
                $("#discount").empty();
                if (!response['error']) {
                    console.log("set discount correct");
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
    $('#set-discount').click(setDiscountFun);
});