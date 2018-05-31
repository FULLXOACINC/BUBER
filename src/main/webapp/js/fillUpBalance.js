function hideAllMessage() {
    $('#unknown-money-format').hide();
    $('#out-of-bound-balance').hide();
    $('#full-balance').hide();
    $('#fill-up-balance-correct').hide();
    $('#card-number').hide();
}

$(document).ready(function () {
    hideAllMessage();
    var fillUpBalanceFun = function () {
        hideAllMessage();
        $.ajax({
            type: "POST",
            url: '/AJAXController',
            data: {
                command: "fill-up-balance",
                cardNumber: $('#card-number-input').val(),
                moneyAmount: $('#amount').val()
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

                }

            },
            error: function (exception) {
                console.log(exception);
            }
        });
    };
    $('#fill-up-balance').click(fillUpBalanceFun);
});