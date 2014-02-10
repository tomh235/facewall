<#import 'main.ftl' as main>

<@main.main title = "Search the facewall app" activeTabIndex = 0>
<div class="container">
    <#if entries?has_content>
        <div class="row">
            <#list entries as entry>
                <div class="col-md-3 col-sm-4 entry">
                    <h5 class="text-center teamName">${entry.teamHeader}</h5>
                        <a href="/facewall/person/${entry.link}">
                            <div class="imgWrapper ${entry.colour}" style="border: 15px solid #${entry.colour}">
                                <img class="avatar" src="${entry.picture}"/>
                            </div>
                        </a>

                    <h3 class="text-center entryName">
                        <a href="/facewall/person/${entry.link}">${entry.name}</a>
                    </h3>
                </div>
            </#list>
        </div>
    <#else>
        <h1 class="no-results main text-center"><img class="smiley" src="/facewall/assets/images/smiley_sad.png"/>There are no faces here!</h1>
    </#if>
</div>
<script src="/facewall/assets/javascripts/snake-order.js"></script>
<script src="/facewall/assets/javascripts/jquery.fakecrop.js"></script>
<script>
    $(document).ready(function () {
        $('img.avatar').fakecrop();
        var overviewEntries = $("div.col-md-4").detach();
        var snakeOrdered = snakeOrder(overviewEntries.get(), 4);
        $("div.row").append(snakeOrdered);
    });
</script>

</@main.main>