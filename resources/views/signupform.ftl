<#import 'main.ftl' as main>

<@main.main title = "Input your details" activeTabIndex = 2>
        
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 form">
            <form role="form" method="post" action="/signupform">
                <div class="form-group">
                    <#if userForm.error("name")??>
                        <#list userForm.error("name") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>

                    <label>Full name</label>
                    <input class="form-control" type="text" name="name" placeholder="Enter name" required>
                </div>

                <div class="form-group">
                    <#if userForm.error("imgURL")??>
                        <#list userForm.error("imgURL") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Url to picture</label>
                    <input class="form-control" type="url" name="imgURL" placeholder="Enter url" required>
                </div>

                <div class="form-group">
                    <#if userForm.error("email")??>
                        <#list userForm.error("email") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Email address</label>
                    <input class="form-control" type="email" name="email" placeholder="Enter email" required>
                </div>

                <div class="form-group">
                    <#if userForm.error("team")??>
                        <#list userForm.error("team") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>

                    <label>Team name</label>
                    <select class="form-control" name="team" required>
                        <#list teamNamesList as teamName>
                            <option value="${teamName}">${teamName}</option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <#if userForm.error("scrum")??>
                        <#list userForm.error("scrum") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Scrum name</label>
                    <input class="form-control" type="text" name="scrum" placeholder="Enter scrum name" required>
                </div>

                <div class="form-group">
                    <#if userForm.error("role")??>
                        <#list userForm.error("role") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Job role</label>
                    <select class="form-control" name="role" required>
                        <option value="ba">Business Analyst</option>
                        <option value="developer">Developer</option>
                        <option value="manager">Management</option>
                        <option value="scrum_master">Scrum Master</option>
                        <option value="qa">QA</option>
                    </select>
                </div>

                <div class="form-group">
                    <#if userForm.error("location")??>
                        <#list userForm.error("location") as error>
                        <h4>${error.message}</h4>
                        </#list>
                    </#if>
                    <label>Work location</label>
                    <select class="form-control" name="location" required >
                        <option value="bath_road">Bath Road</option>
                        <option value="buckingham_avenue">Buckingham Avenue</option>
                        <option value="leeds">Leeds</option>
                        <option value="pune">Pune</option>
                    </select>
                </div>

                <input id="submit" class="btn btn-primary btn-lg btn-block" type="submit">
            </form>
        </div>
    </div>
</div>
</@main.main>