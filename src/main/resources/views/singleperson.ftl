<#import 'main.ftl' as main>

<@main.main title = "Your face" activeTabIndex = 4>

<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 entry">
            <h5 class="personName">${model.name}</h5>
            <h5 class="personEmail">${model.email}</h5>
            <h5 class="personRole">${model.role}</h5>
            <img class="single-person-pic" src="${model.picture}"></img>
            <h5 class="teamName">${model.teamName}</h5>
        </div>
    </div>
</div>

</@main.main>