<#import 'main.ftl' as main>

<@main.main title = "Input your details" activeTabIndex = 2>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 form">
            <form role="form" method="post" action="/facewall/signup">
                <div class="form-group">
                    <#if (errors["personInformation.name"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["personInformation.name"]}
                        </div>
                    </#if>

                    <label>Full name</label>
                    <input class="form-control" type="text" name="name" placeholder="Enter name" value="${(personInformation.name)!""}" required>
                </div>

                <div class="form-group">
                    <#if (errors["personInformation.picture"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["personInformation.picture"]}
                        </div>
                    </#if>
                    <label>Url to picture</label>
                    <input class="form-control" type="url" name="imgUrl" placeholder="Enter url" value="${(personInformation.picture)!""}" required>
                </div>

                <div class="form-group">
                    <#if (errors["personInformation.email"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["personInformation.email"]}
                        </div>
                    </#if>
                    <label>Email address</label>
                    <input class="form-control" type="email" name="email" placeholder="Enter email" value="${(personInformation.email)!""}" required>
                </div>

                <div class="form-group">
                    <#if (errors["team"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["team"]}
                        </div>
                    </#if>

                    <label>Team name</label>
                    <#if teamNamesList?size==0>
                        <input class="form-control" type="text" name="team" placeholder="Enter team name" value="${(team.name())!""}" required>
                    <#else>
                        <select class="form-control" name="team" required>
                            <#list teamNamesList as teamName>
                                <#if teamName==''>
                                    <option value="">Unassigned</option>
                                </#if>
                                <option value="${teamName}">${teamName}</option>
                            </#list>
                        </select>
                    </#if>
                </div>

                <div class="form-group">
                    <#if (errors["personInformation.scrum"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["personInformation.scrum"]}
                        </div>
                    </#if>
                    <label>Scrum name</label>
                    <input class="form-control" type="text" name="scrum" placeholder="Enter scrum name" value="${(personInformation.scrum)!""}" required>
                </div>

                <div class="form-group">
                    <#if (errors["personInformation.role"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["personInformation.role"]}
                        </div>
                    </#if>
                    <label>Job role</label>
                    <select class="form-control" name="role" required>
                        <option value="Business Analyst">Business Analyst</option>
                        <option value="Developer">Developer</option>
                        <option value="Manager">Management</option>
                        <option value="Scrum Master">Scrum Master</option>
                        <option value="QA">QA</option>
                    </select>
                </div>

                <div class="form-group">
                    <#if (errors["personInformation.location"])??>
                        <div class="alert alert-danger">
                            <span class="glyphicon glyphicon-remove"></span>${errors["personInformation.location"]}
                        </div>
                    </#if>
                    <label>Work location</label>
                    <select class="form-control" name="location" required >
                        <option value="Bath Road">Bath Road</option>
                        <option value="Buckingham Avenue">Buckingham Avenue</option>
                        <option value="Leeds">Leeds</option>
                        <option value="Pune">Pune</option>
                    </select>
                </div>

                <input id="submit" class="btn btn-primary btn-lg btn-block" type="submit">
            </form>
        </div>
    </div>
</div>
</@main.main>