<#macro main title activeTabIndex>

<!DOCTYPE html>

<html>
    <head>
        <title>${title}</title>
        <link rel="stylesheet" media="screen" href="/assets/css/main.css">
        <script src="/assets/javascripts/jquery-1.9.0.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <div class="container">
                <h1 class="text-center"><img class="smiley" src="assets/images/smiley_happy.png" />Facewall</h1>
                <ul class="nav nav-tabs nav-justified">
                    <li><a id="home" href="/">Overview</a></li>
                    <li><a id="search" href="/search">Search</a></li>
                    <li><a id="register" href="/signupform">Register</a></li>
                </ul>
            </div>
        </div>

        <script>
            $("ul.nav").children().eq(${activeTabIndex}).addClass("active");
        </script>

        <#nested/>
    </body>
</html>

</#macro>