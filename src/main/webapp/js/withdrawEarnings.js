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
                if (response['error']) {
                    viewServerError();
                }else {
                    console.log(response);
                }
            }

        },
        error: function () {
            viewConnectionError();
        }
    });
}

$(document).ready(function () {
    earnedMoneyMessage = $('#earned-money-mess').val();

    findDriverEarnedMoney();
    var withdrawEarningsFun = function () {
        hideAllMessage();

        var cardNumberRegExp = /\d{16}/;
        var cardNumber = $('#card-number-input').val();
        if (!cardNumberRegExp.test(cardNumber)) {
            $('#card-number').show();
            return;
        }
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "withdraw-earning-driver-money",
                cardNumber: cardNumber
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
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#withdraw-earnings').click(withdrawEarningsFun);
});