function hideAllMessage() {
    $('#unknown-money-format').hide();
    $('#out-of-bound-balance').hide();
    $('#full-balance').hide();
    $('#fill-up-balance-correct').hide();
    $('#card-number').hide();
}

$(document).ready(function () {
    var fillUpBalanceFun = function () {
        hideAllMessage();
        var moneyAmount = $('#amount').val();
        var cardNumber = $('#card-number-input').val();
        var moneyFormatRegExp = /[1-9]\d*\.\d{2}/;
        if (!moneyFormatRegExp.test(moneyAmount)) {
            $('#unknown-money-format').show();
            return;
        }
        if (moneyAmount >= 100000) {
            $('#out-of-bound-balance').show();
            return;
        }
        var cardNumberRegExp = /\d{16}/;
        if (!cardNumberRegExp.test(cardNumber)) {
            $('#card-number').show();
            return;
        }
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "fill-up-balance",
                cardNumber: cardNumber,
                moneyAmount: moneyAmount
            },
            success: function (response) {
                if (response['allCorrect']) {
                    $('#fill-up-balance-correct').show();
                } else {
                    if (response['fullBalance']) {
                        $('#full-balance').show();
                    }
                    if (response['outOfBoundBalance']) {
                        $('#out-of-bound-balance').show();
                    }
                    if (response['unknownMoneyFormat']) {
                        $('#unknown-money-format').show();
                    }
                    if (response['cardNumberNotValid']) {
                        $('#card-number').show();
                    }
                    if (response['error']) {
                        viewServerError();
                    }
                }

            },
            error: function () {
                viewConnectionError();
            }
        });
    };
    $('#fill-up-balance').click(fillUpBalanceFun);
});