<#import 'main.ftl' as main>

<@main.main title = "${personInformation.name}'s details" activeTabIndex = 2>
    
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4 text-center">
                    <h2>Your account</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 col-md-offset-2">
    
                <h3>Name</h3>
                <p class="name">${personInformation.name}</p>
    
                <h3>Email</h3>
                <p class="email">${personInformation.email}</p>
    
                <h3>Role</h3>
                <p class="role">${personInformation.role}</p>
    
                <h3>Location</h3>
                <!--<#--<p class="location">${personInformation.location}</p>-->-->
                <p>Sorry, this field has not been implemented yet.</p>
    
                <h3>Team</h3>
                <p class="team">
                    <#if !personInformationsTeam?? || personInformationsTeam == "">
                    <em>Not specified</em>
                    <#else>
                    ${team}
                    </#if>
                </p>
    
                <h3>Scrum</h3>
                <!--<#--<p class="scrum">
                    <#if !(personInformation.scrum)?? || personInformation.scrum == "">
                        <em>Not specified</em>
                    <#else>
                        ${personInformation.scrum}
                    </#if>
                </p>-->-->
                <p>Sorry, this field has not been implemented yet.</p>
            </div>
    
            <div class="col-md-4">
    
                <h3>Picture</h3>
                <h5>url:</h5>
                <p class="imgURL">${personInformation.picture}</p>
    
                <a href="#">
                    <img class="signup-pic" src="${personInformation.picture}" alt="">
                </a>
    
            </div>
        </div>
    </div>
</@main.main>