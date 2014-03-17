<#assign resultsCount = persons?size + teams?size>
<div class="container">
    <#if !persons?has_content && !teams?has_content>
            <div class="col-md-8 col-md-offset-2">
                <h1 class="no-results search"><img src="/facewall/assets/images/smiley_sad.png" height="55" />No results found!</h1>
            </div>
        </div>
    <#else>
        <div>
            <h3 class="results-counter">There were ${resultsCount} matching results</h3>
        </div>
        <#if persons?has_content>
            <div>
                <h1>People</h1>
            </div>
            <div class="row">
                <#list persons as result>
                    <div class="col-md-3 col-sm-4 entry">
                        <h5 class="text-center teamName">
                            <a href="/facewall/team/${result.teamName}">${result.teamName}</a>
                        </h5>
                        <a href="#">
                            <div class="imgWrapper">
                                <img class="avatar" src="${result.picture}"/>
                            </div>
                        </a>
                        <h3 class="text-center entryName">
                            <a href="#">${result.name}</a>
                        </h3>
                    </div>
                </#list>
            </div>
        </#if>

        <#if teams?has_content>
            <div>
                <h1>Teams</h1>
            </div>
            <div class="row">
                <#list teams as result>
                    <div class="col-md-4 col-md-offset-4 team-entry">
                        <div class="thumbnail">
                            <h3 class="text-center">${result.name}</h3>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
    </#if>
</div>
<script src="/facewall/assets/javascripts/jquery.fakecrop.js"></script>
<script>
    $(document).ready(function () {
        $('img.avatar').fakecrop();
    });
</script>