var autoSearchOnKeyUp = function (input, target, result) {
    var recentness = 0;

    $(input).keyup(function () {
        var searchstring = $(this).val();
            if (searchstring.length >= 1) {
                        recentness++;
                                   var requestRecentness = recentness;
                                   var params = {};
                                   params[input.attr("name")] = input.val();

                                   var updateResultWithMostRecent = function (data) {
                                       if (recentness == requestRecentness) {
                                           result.html(data);
                                       }
                                   };

            }  else if (searchstring.length == 0) {result.empty();}


        $.get(target, params, updateResultWithMostRecent, "html");
    });
};