<#import 'main.ftl' as main>

    <@main.main title = "Team" activeTabIndex = 1>

    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 entry">
                <h5 class="teamName" style="color: {colour}">${name}</h5>
            </div>
        </div>
        <#if entries?has_content>
            <div class="row">
                <#list entries as entry>
                    <div class="col-md-3 col-sm-4 entry">s
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
    <script src="/facewall/assets/javascripts/jquery.fakecrop.js"></script>
    <script>
    $(document).ready(function () {
        $('img.avatar').fakecrop();
    });
    </script>

</@main.main>