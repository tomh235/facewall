<#import 'main.ftl' as main>

<@main.main title = "Search the facewall app" activeTabIndex = 1>

<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="input-group input-group-lg">
                    <input tabindex="1" id="searchInput" type="text" name="keywords" class="form-control" placeholder="Hi there, search for a person or a team">
                    <span class="input-group-btn">
                        <button class="btn btn-default icon-search" type="button"></button>
                    </span>
            </div>
        </div>
    </div>
    <div id="result"></div>
    <div></div>
</div>

<script src="/facewall/assets/javascripts/search-box.js"></script>
<script src="/facewall/assets/javascripts/jquery.fakecrop.js"></script>
<script>
    var searchInput = $("#searchInput");
    var result = $("#result");
    searchInput.focus();
    autoSearchOnKeyUp(searchInput, "search-results", result);
</script>

</@main.main>