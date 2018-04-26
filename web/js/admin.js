$(document).ready(function () {
     $('#find-users').click(function () {
        $.ajax({
            url: '/AJAXController',
            data: {
                command: "find-users",
                pattern: "TEST"
            },
            success: function (response) {
                console.log("good " + response['test']);
            },
            error: function (jqXHR, exception) {
                console.log("bad");
                // Your error handling logic here..
            }
        });
     });
});