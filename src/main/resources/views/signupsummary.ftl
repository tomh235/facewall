<#import 'main.ftl' as main>

<@main.main title = "Summary of new userModel" activeTabIndex = 2>
    
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4 text-center">
                    <h2>Your account</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 col-md-offset-2">
    
                <h3>Name</h3>
                <p class="name">${userModel.name}</p>
    
                <h3>Email</h3>
                <p class="email">${userModel.email}</p>
    
                <h3>Role</h3>
                <p class="role">${userModel.prettifyString(userModel.role)}</p>
    
                <h3>Location</h3>
                <p class="location">${userModel.prettifyString(userModel.location)}</p>
    
                <h3>Team</h3>
                <p class="team">
                    <#if !userModel.team?? || userModel.team == "">
                    <em>Not specified</em>
                    <#else>
                    ${userModel.team}
                    </#if>
                </p>
    
                <h3>Scrum</h3>
                <p class="scrum">
                    <#if !userModel.scrum?? || userModel.scrum == "">
                        <em>Not specified</em>
                    <#else>
                        ${userModel.scrum}
                    </#if>
                </p>
            </div>
    
            <div class="col-md-4">
    
                <h3>Picture</h3>
                <h5>url:</h5>
                <p class="imgUrl">${userModel.imgURL}</p>
    
                <a href="#">
                    <img class="signup-pic" src="${userModel.imgURL}" alt="">
                </a>
    
            </div>
        </div>
    </div>
</@main.main>