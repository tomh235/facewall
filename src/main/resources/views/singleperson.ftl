<#import 'main.ftl' as main>

<@main.main title = "Your face" activeTabIndex = 4>

<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 entry">
            <h5 class="personName">${name}</h5>
            <h5 class="personEmail">${email}</h5>
            <h5 class="personRole">${role}</h5>
            <img class="single-person-pic" src="${picture}"></img>
            <h5 class="teamName">${teamName}</h5>
        </div>
    </div>
</div>

</@main.main>