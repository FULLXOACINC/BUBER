var earnedMoneyMessage;

function hideAllMessage() {
    $('#unknown-money-format').hide();
    $('#no-money-earned').hide();
    $('#card-number').hide();
    $('#withdraw-earnings-correct').hide();
}

function findDriverEarnedMoney() {
    $.ajax({
        type: "POST",
        url: '/AJAXController',
        data: {
            command: "find-driver-earned-money"
        },
        success: function (response) {
            if (response['allCorrect']) {
                $('#earned-money').val(earnedMoneyMessage + ": " + response['earnedMoney']);
            } else {
                console.log(response);
            }

        },
        error: function (exception) {
            console.log(exception);
        }
    });
}

$(document).ready(function () {
    hideAllMessage();
    earnedMoneyMessage = $('#earned-money-mess').val();

    findDriverEarnedMoney()
    var withdrawEarningsFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "withdraw-earning-driver-money",
                cardNumber: $('#card-number-input').val()
            },
            success: function (response) {
                if (response['allCorrect']) {
                    findDriverEarnedMoney();
                    $('#withdraw-earnings-correct').show();
                } else {
                    if (response['noEarnedMoney']) {
                        $('#no-money-earned').show();
                    }
                    if (response['cardNumberNotValid']) {
                        $('#card-number').show();
                    }

                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#withdraw-earnings').click(withdrawEarningsFun);
});