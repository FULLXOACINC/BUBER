var removeComplaint;
$(document).ready(function () {
    var acceptComplaintFun = function () {
        removeComplaint=this.id;
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "accept-complaint",
                id: removeComplaint
            },
            success: function (response) {
                if (!response['error']) {
                    console.log("accept complaint correct id:"+removeComplaint);
                    $( "#complaint-"+removeComplaint).remove();
                } else {
                    console.log(response['error']);
                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('.accept').click(acceptComplaintFun);
});