<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
                class="icon-bar"></span><span class="icon-bar"></span></button>
        <a class="navbar-brand" href="/admin/sa/index">管理后台</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navigate-bar">
            <li data-options='index' class=""><a href="/admin/sa/index">首页</a></li>
        <@shiro.hasRole name="user">
            <li data-options='user'><a href="/admin/sa/user">会员管理</a></li>
        </@shiro.hasRole>
        <@shiro.hasRole name="product">
            <li data-options='product'><a href="/admin/sa/product">商品管理</a></li>
        </@shiro.hasRole>
            <li data-options='setting'><a href="/admin/sa/setting">系统设置</a></li>
        </ul>


    <@shiro.authenticated>
        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><@shiro.principal property="realName"/>
                    <strong class="caret"></strong></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="#">用户信息</a>
                    </li>
                    <li>
                        <a href="#">Another action</a>
                    </li>
                    <li>
                        <a href="#">Something else here</a>
                    </li>
                    <li class="divider">
                    </li>
                    <li>
                        <a href="/logout">退出</a>
                    </li>
                </ul>
            </li>
        </ul>
    </@shiro.authenticated>
    </div>

</nav>

<#--<nav class="navbar navbar-inverse navbar-fixed-top">-->
<#--<div class="container-fluid">-->
<#--<div class="navbar-header">-->
<#--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"-->
<#--aria-expanded="false" aria-controls="navbar">-->
<#--<span class="sr-only">Toggle navigation</span>-->
<#--<span class="icon-bar">1</span>-->
<#--<span class="icon-bar">2</span>-->
<#--<span class="icon-bar">3</span>-->
<#--</button>-->
<#--<a class="navbar-brand" href="/admin/sa/index">管理后台</a>-->
<#--</div>-->
<#--<div id="navbar" class="navbar-collapse collapse">-->
<#--<ul class="nav navbar-nav navigate-bar">-->
<#--<li data-options='index' class=""><a href="/admin/sa/index">首页</a></li>-->
<#--<@shiro.hasRole name="user">-->
<#--<li data-options='user'><a href="/admin/sa/user">会员管理</a></li>-->
<#--</@shiro.hasRole>-->
<#--<@shiro.hasRole name="product">-->
<#--<li data-options='product'><a href="/admin/sa/product">商品管理</a></li>-->
<#--</@shiro.hasRole>-->
<#--<li data-options='setting'><a href="/admin/sa/setting">系统设置</a></li>-->
<#--</ul>-->
<#--<ul class="nav navbar-nav navbar-right">-->
<#--<@shiro.authenticated>-->
<#--<li><a href="#" style="color:white"><@shiro.principal property="realName"/></a></li>-->
<#--<li><a href="/logout">退出</a></li>-->
<#--</@shiro.authenticated>-->

<#--</ul>-->
<#--</div>-->
<#--</div>-->
<#--</nav>-->
<script>
    (function () {
        var uri = location.pathname;
        $('#bs-example-navbar-collapse-1 .navigate-bar li').each(function () {
            console.log($(this).data('options'));
            if (uri.indexOf($(this).data('options')) > 0) {
                $(this).addClass("active")
            }

        })
    })()
</script>
