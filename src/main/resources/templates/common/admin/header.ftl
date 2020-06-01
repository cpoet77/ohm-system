<header class="main-header">
    <!-- Logo -->
    <a href="/" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini">${appInfo("ohms.name.small.format")}</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">${siteNameFormat}</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- Notifications: style can be found in dropdown.less -->
                <li class="dropdown notifications-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <span class="label label-warning">10</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">You have 10 notifications</li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">
                                <li>
                                    <a href="#">
                                        <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">View all</a></li>
                    </ul>
                </li>
                <!-- UserEntity Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                             class="user-image" alt="${u.name!"user"} avatar">
                        <span class="hidden-xs">${u.realName!"获取姓名失败"}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- UserEntity image -->
                        <li class="user-header">
                            <img src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                 class="img-circle" alt="${u.name!"user"} avatar">
                            <p>
                                ${u.realName!"获取姓名失败"}
                                (<#if isRoles("admin")>超级管理员<#else>教学秘书</#if>
                                )
                                <small>${u.name!"获取用户名失败"}</small>
                            </p>
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="#" class="btn btn-default btn-flat">个人资料</a>
                            </div>
                            <div class="pull-right">
                                <a href="#" onclick="NS.logout()" class="btn btn-default btn-flat">注销登录</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>