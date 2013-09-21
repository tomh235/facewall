var numTimesKeyUp = 0;

var autoSearchOnKeyUp = function (input, target, result) {
    input.keyup(function () {
        var params = {}
        params[input.attr("name")] = input.val()

        var doIfLatestKeyUp = function(curTimesKeyUp, task) {
            return function(data) {
                if (curTimesKeyUp == numTimesKeyUp) {
                    task(data);
                }
            };
        };

        var updateResult = function (data) {
            result.empty();
            result.append(data);
        };

        var emptyResult = function (data) {
            result.empty();
        };

        numTimesKeyUp += 1;

        $.get(target, params)
            .done(doIfLatestKeyUp(numTimesKeyUp, updateResult))
            .fail(doIfLatestKeyUp(numTimesKeyUp, emptyResult));
    });
};