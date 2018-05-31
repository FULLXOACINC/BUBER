$(document).ready(function () {
    var changeDiscountFun = function () {
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "change-discount",
                login: $('#login').val(),
                discount: $('#discount').val()
            },
            success: function (response) {
                if (!response['error']) {
                    console.log("change discount correct");
                } else {
                    console.log(response['error']);
                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    var viewComplaintsFun = function () {
        $('#complaint').empty();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "find-user-complaint",
                login: $('#login').val()
            },
            success: function (response) {
                if (!response['error']) {
                    response['complaint'].forEach(function (complaint) {
                        if (!complaint.accept) {
                            $('#complaint').append("<div id='complaint-" + complaint.complaintId + "'>" +
                                "<div>ID: " + complaint.complaintId + "</div>\n" +
                                "<div>RAID ID: " + complaint.raidId + "</div>\n" +
                                "<div id='" + complaint.complaintId + "-text'></div>\n" +
                                "<input type='submit' class='accept' id=" + complaint.complaintId + " value='Ok'>\n" +
                                "</div>");
                            $("#" + complaint.complaintId + "-text").text(complaint.complaintText);
                            var acceptComplaintFun = function () {
                                removeComplaint = this.id;
                                $.ajax({
                                    type: "POST",
                                    url: '/AJAXController',
                                    data: {
                                        command: "accept-complaint",
                                        id: complaint.complaintId
                                    },
                                    success: function (response) {
                                        if (!response['error']) {
                                            console.log("accept complaint correct id:" + removeComplaint);
                                            $("#" + complaint.complaintId).remove();
                                        } else {
                                            console.log(response['error']);
                                        }

                                    },
                                    error: function (exception) {
                                        console.log(exception);
                                    }
                                });
                            };
                            $('#' + complaint.complaintId).click(acceptComplaintFun);
                        } else {
                            $('#complaint').append("<div id='complaint-" + complaint.complaintId + "'>" +
                                "<div>ID: " + complaint.complaintId + "</div>\n" +
                                "<div>RAID ID: " + complaint.raidId + "</div>\n" +
                                "<div id='" + complaint.complaintId + "-text'></div>\n" +
                                "</div>");
                            $("#" + complaint.complaintId + "-text").text(complaint.complaintText);
                        }
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
    $('#view-complaint').click(viewComplaintsFun);
    $('#change-discount').click(changeDiscountFun);
});