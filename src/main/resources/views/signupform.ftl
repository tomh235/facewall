<#import 'main.ftl' as main>

<@main.main title = "Input your details" activeTabIndex = 2>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 form">
            <form role="form" method="post" action="/facewall/signup/summary">
                <div class="form-group">
                    <#if (userForm.error("name"))??>
                        <#list userForm.error("name") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>

                    <label>Full name</label>
                    <input class="form-control" type="text" name="name" placeholder="Enter name" required>
                </div>

                <div class="form-group">
                    <#if (userForm.error("imgUrl"))??>
                        <#list userForm.error("imgUrl") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Url to picture</label>
                    <input class="form-control" type="url" name="imgUrl" placeholder="Enter url" required>
                </div>

                <div class="form-group">
                    <#if (userForm.error("email"))??>
                        <#list userForm.error("email") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Email address</label>
                    <input class="form-control" type="email" name="email" placeholder="Enter email" required>
                </div>

                <div class="form-group">
                    <#if (userForm.error("team"))??>
                        <#list userForm.error("team") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>

                    <label>Team name</label>
                    <#if teamNamesList?size==0>
                        <input class="form-control" type="text" name="team" placeholder="Enter team name" required>
                    <#else>
                        <select class="form-control" name="team" required>
                            <#list teamNamesList as teamName>
                                <#if teamName==''>
                                    <option value="">No Team</option>
                                </#if>
                                <option value="${teamName}">${teamName}</option>
                            </#list>
                        </select>
                    </#if>
                </div>

                <div class="form-group">
                    <#if (userForm.error("scrum"))??>
                        <#list userForm.error("scrum") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Scrum name</label>
                    <input class="form-control" type="text" name="scrum" placeholder="Enter scrum name" required>
                </div>

                <div class="form-group">
                    <#if (userForm.error("role"))??>
                        <#list userForm.error("role") as error>
                        <h4>${error.message}</h4>
                        </#list>
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
                    <#if (userForm.error("location"))??>
                        <#list userForm.error("location") as error>
                        <h4>${error.message}</h4>
                        </#list>
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