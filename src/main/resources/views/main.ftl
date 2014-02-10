<#macro main title activeTabIndex>

<!DOCTYPE html>

<html>
    <head>
        <title>${title}</title>
        <link rel="stylesheet" media="screen" href="/facewall/assets/css/main.css">
        <script src="/facewall/assets/javascripts/jquery-1.9.0.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <div class="container">
                <h1 id="homeTitle" class="text-center">
                    <img class="smiley" src="/facewall/assets/images/smiley_happy.png"/>Facewall
                </h1>

                <ul class="nav nav-tabs nav-justified">
                    <li><a id="home" href="/facewall/">Overview</a></li>
                    <li><a id="search" href="/facewall/search">Search</a></li>
                    <li><a id="register" href="/facewall/signup">Register</a></li>
                </ul>
            </div>
        </div>

        <script>
            $("ul.nav").children().eq(${activeTabIndex}).addClass("active");
        </script>

        <#nested>
    </body>
</html>

</#macro>