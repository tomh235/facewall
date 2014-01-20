<div class="container">
    <#if results.persons?has_content>
        <div>
            <h1>People</h1>
        </div>
        <div class="row">
            <#list results.persons as result>
                <div class="col-md-4">
                    <div class="thumbnail">
                        <h5>${result.teamName}</h5>
                        <img src="${result.picture}"/>
                        <h3 class="text-center">${result.name}</h3>
                    </div>
                </div>
            </#list>
        </div>
    </#if>

    <#if results.teams?has_content>
        <div>
            <h1>Teams</h1>
        </div>
        <div class="row">
            <#list results.teams as result>
                <div class="col-md-4 col-md-offset-4">
                    <div class="thumbnail">
                        <h3 class="text-center">${result.name}</h3>
                    </div>
                </div>
            </#list>
        </div>
    </#if>

    <#if !results.persons?has_content && !results.teams?has_content>
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <h1 class="no-results search"><img src="assets/images/smiley_sad.png" height="55" />No results found!</h1>
            </div>
        </div>
    </#if>
</div>