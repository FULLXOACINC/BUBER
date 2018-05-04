$(document).ready(function () {
    var setDiscountFun=function () {
        $.ajax({
            url: '/AJAXController',
            data: {
                command: "set-discount",
                pattern: $('#discount').val()
            },
            success: function (response) {
                $("#searched").empty();
                if (!response['error']) {
                    response['users'].forEach(function (user) {
                        $("#searched").append("<form action='/controller'><input type='hidden' name='command' value='view-user'>\n" +
                            "<input type='hidden' name='user' value='" + user.login + "'>\n" +
                            "<input type='submit' value='" + user.login + "'></form>");
                    });
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