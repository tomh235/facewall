var autoSearchOnKeyUp = function (input, target, result) {
    var recentness = 0;

    $(input).keypress(function () {
        recentness++;
        var requestRecentness = recentness;
        var params = {};
        params[input.attr("name")] = input.val();

        var updateResultWithMostRecent = function (data) {
            if (recentness == requestRecentness) {
                result.empty();
                result.append(data);
            }
        };

        $.get(target, params).done(updateResultWithMostRecent);
    });
};