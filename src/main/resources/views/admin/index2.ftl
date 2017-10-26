<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>后台管理</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link href="/static/css/base.css" rel="stylesheet"/>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/layer/layer.js"></script>

    <script src="/static/js/bootstrap-treeview.js"></script>
</head>
<body data-target="#one" data-spy="scroll">

<!--<script baseUrl="" src="/js/user.login.js"></script>-->
<div class="navbar navbar-inverse navbar-fixed-top animated fadeInDown" style="z-index: 101;height: 41px;">

    <div class="container" style="padding-left: 0px; padding-right: 0px;">
        <div class="navbar-header">
            <button data-target=".navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle collapsed">
                <span class="sr-only">导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div role="navigation" class="navbar-collapse collapse">
            <a id="_logo" href="" style="color:#fff; font-size: 24px;" class="navbar-brand hidden-sm">SSM + Shiro Demo
                演示</a>
            <ul class="nav navbar-nav" id="topMenu">
                <li class="dropdown active">
                    <a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown"
                       class="dropdown-toggle" href="/user/index.shtml">
                        个人中心<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/user/index.shtml">个人资料</a></li>
                        <li><a href="/user/updateSelf.shtml">资料修改</a></li>
                        <li><a href="/user/updatePswd.shtml">密码修改</a></li>
                        <li><a href="/role/mypermission.shtml">我的权限</a></li>
                    </ul>
                </li>
                <li class="dropdown ">
                    <a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown"
                       class="dropdown-toggle" href="/member/list.shtml">
                        用户中心<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/member/list.shtml">用户列表</a></li>
                        <li><a href="/member/online.shtml">在线用户</a></li>
                    </ul>
                </li>
                <li class="dropdown ">
                    <a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown"
                       class="dropdown-toggle" href="/permission/index.shtml">
                        权限管理<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/role/index.shtml">角色列表</a></li>
                        <li><a href="/role/allocation.shtml">角色分配（这是个JSP页面）</a></li>
                        <li><a href="/permission/index.shtml">权限列表</a></li>
                        <li><a href="/permission/allocation.shtml">权限分配</a></li>
                    </ul>
                </li>
                <li>
                    <a class="dropdown-toggle" target="_blank" href="http://www.sojson.com/tag_shiro.html"
                       target="_blank">
                        Shiro相关博客<span class="collapsing"></span>
                    </a>
                </li>
                <li>
                    <a class="dropdown-toggle" href="http://www.sojson.com/shiro" target="_blank">
                        本项目介绍<span class="collapsing"></span>
                    </a>
                </li>
                <li>
                    <a class="dropdown-toggle" href="http://www.sojson.com/jc/shiro.html" target="_blank">
                        Shiro Demo 其他版本<span class="collapsing"></span>
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav  pull-right">
                <li class="dropdown " style="color:#fff;">
                    <a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown"
                       onclick="location.href='/user/index.shtml'" href="/user/index.shtml"
                       class="dropdown-toggle qqlogin">${adminUserName?default('管理员')}<span class="caret"></span></a>
                    <ul class="dropdown-menu" userid="1">
                        <li><a href="/user/index.shtml">个人资料</a></li>
                        <li><a href="/role/mypermission.shtml">我的权限</a></li>
                        <li><a href="javascript:void(0);" onclick="logout();">退出登录</a></li>
                    </ul>
                </li>
            </ul>
            <style>#topMenu > li > a {
                padding: 10px 13px
            }</style>
        </div>
    </div>
</div>
<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
    <div class="row">
        <div id="one" class="col-md-2">
            <ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix"
                style="top: 100px; z-index: 100;">
                <li class=" ">
                    <a href="/user/index.shtml">
                        <i class="glyphicon glyphicon-chevron-right"></i>个人资料
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dLabel" style="margin-left: 160px; margin-top: -40px;">
                        <li><a href="/user/updateSelf.shtml">资料修改</a></li>
                        <li><a href="/user/updatePswd.shtml">密码修改</a></li>
                    </ul>
                </li>
                <li class="active dropdown">
                    <a href="/role/mypermission.shtml">
                        <i class="glyphicon glyphicon-chevron-right"></i>我的权限
                    </a>
                </li>
            </ul>
        </div>
        <div class="col-md-10">
            <h2>我的权限</h2>
            <hr>
            <div id="getPermissionTree">loding... ...</div>
        </div>
    </div>
</div>

<script>
    $(function () {
//        //加载 permission tree data
//        var load = layer.load();
//        $.post("getPermissionTree.shtml", {}, function (data) {
//            layer.close(load);
//            if (data && !data.length) {
//                return $("#getPermissionTree").html('<code>您没有任何权限。只有默认的个人中心。</code>'), !1;
//            }
//            $('#getPermissionTree').treeview({
//                levels: 1,//层级
//                color: "#428bca",
//                nodeIcon: "glyphicon glyphicon-user",
//                showTags: true,//显示数量
//                data: data//数据
//            });
//        }, 'json');
    });


    /**
     * @author sojson.com
     * @ps 你可以当作是一个闭包 | 封装的Demo
     */
    (function (o, w) {
        if (!w.so) w.so = {};
        return (function (so) {
            so.$1 = !0,//true
                so.$0 = !1;//false
            /**
             * 全选
             */
            so.checkBoxInit = function (prentCheckbox, childCheckbox) {
                childCheckbox = o(childCheckbox), prentCheckbox = o(prentCheckbox);
                //先取消全选。
                //childCheckbox.add(prentCheckbox).attr('checked',!1);
                //全选
                prentCheckbox.on('click', function () {
                    childCheckbox.attr('checked', this.checked);
                });
                //子选择
                childCheckbox.on('click', function () {
                    prentCheckbox.attr('checked', childCheckbox.length === childCheckbox.end().find(':checked').not(prentCheckbox).length);
                });
            },
                //初始化
                so.init = function (fn) {
                    o(function () {
                        fn()
                    });
                }
            so.id = function (id) {
                return o('#' + id);
            }
            so.default = function () {
            }

        })(so);
    })($, window);

</script>
</body>
</html>