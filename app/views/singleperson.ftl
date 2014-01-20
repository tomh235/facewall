<#import 'main.ftl' as main>

<@main.main title = "Your face" activeTabIndex = 4>

<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 entry">
            <h5 class="personName">${person.name}</h5>
            <img class="single-person-pic" src="${person.picture}"></img>
            <h5 class="teamName">${person.teamName}</h5>
        </div>
    </div>
</div>

</@main.main>