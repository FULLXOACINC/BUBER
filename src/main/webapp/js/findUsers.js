$(document).ready(function () {
    var findFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "find-users",
                pattern: $('#pattern').val()
            },
            success: function (response) {
                $("#searched").empty();
                if (!response['error']) {
                    response['users'].forEach(function (user) {
                        $("#searched").append("<form action='/controller'>" +
                            "<input type='hidden' name='command' value='view-user'>\n" +
                            "<input type='hidden' name='user' value='" + user.login + "'>\n" +
                            "<input type='submit' class='btn btn-lg btn-primary btn-block' value='" + user.login + "'></form>");
                    });
                } else {
                    console.log(response['error']);
                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#find-users').click(findFun);
    $('#pattern').keyup(findFun);
});