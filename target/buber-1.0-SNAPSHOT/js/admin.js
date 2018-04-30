$(document).ready(function () {
    $('#find-users').click(function () {
        $.ajax({
            url: '/AJAXController',
            data: {
                command: "find-users",
                pattern: $('#pattern').val()
            },
            success: function (response) {
                $("#searched").empty();
                console.log(response['users']);
                response['users'].forEach(function (user) {
                    $("#searched").append("<form action='/controller'><input type='hidden' name='command' value='view-user'>\n" +
                        "<input type='hidden' name='user' value='" + user.login + "'>\n" +
                        "<input type='submit' value='" + user.login +"'></form>");
                });
            },
            error: function (jqXHR, exception) {
                console.log("bad");
                // Your error handling logic here..
            }
        });
    });
});