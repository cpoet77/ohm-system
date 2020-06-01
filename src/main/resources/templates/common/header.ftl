<#-- 面向普通用户的顶部导航栏 -->
<header class="main-header">
    <nav class="navbar navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <a href="/" class="navbar-brand">${siteNameFormat}</a>
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#navbar-collapse">
                    <i class="fa fa-bars"></i>
                </button>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                <ul class="nav navbar-nav">
                    <li <#if activeIndex??>class="active"</#if>>
                        <a href="/">
                            首页
                            <#if activeIndex??><span class="sr-only">(current)</span></#if>
                        </a>
                    </li>
                    <#if isRoles("admin", "teachingSecretary")>
                        <li>
                            <a href="/teachingSecretary">
                                控制台
                            </a>
                        </li>
                    </#if>
                </ul>
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" id="navbar-search-input"
                               placeholder="搜索...">
                    </div>
                </form>
            </div>
            <!-- /.navbar-collapse -->
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <#if is_auth>
                        <!-- Messages: style can be found in dropdown.less-->
                        <li class="dropdown messages-menu">
                            <!-- Menu toggle button -->
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa  fa-bell-o"></i>
                                <span class="label label-success">4</span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="header">You have 4 messages</li>
                                <li>
                                    <!-- inner menu: contains the messages -->
                                    <ul class="menu">
                                        <li><!-- start message -->
                                            <a href="#">
                                                <div class="pull-left">
                                                    <!-- UserEntity Image -->
                                                    <img src="/static/dist/img/user2-160x160.jpg" class="img-circle"
                                                         alt="User Image">
                                                </div>
                                                <!-- Message title and timestamp -->
                                                <h4>
                                                    Support Team
                                                    <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                                </h4>
                                                <!-- The message -->
                                                <p>Why not buy a new awesome theme?</p>
                                            </a>
                                        </li>
                                        <!-- end message -->
                                    </ul>
                                    <!-- /.menu -->
                                </li>
                                <li class="footer"><a href="#">See All Messages</a></li>
                            </ul>
                        </li>
                        <!-- /.messages-menu -->
                        <!-- UserEntity Account Menu -->
                        <li class="dropdown user user-menu">
                            <!-- Menu Toggle Button -->
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <img src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                     class="user-image" alt="${u.name!"user"} avatar">
                                <span class="hidden-xs">${u.realName!"获取姓名失败"}</span>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- The user image in the menu -->
                                <li class="user-header">
                                    <img src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                         class="img-circle" alt="${u.name!"user"} avatar">
                                    <p>
                                        ${u.realName!"获取姓名失败"}
                                        (<#if isRoles("admin")>超级管理员<#elseif isRoles("teachingSecretary")>教学秘书<#elseif isRoles("teacher")>教师<#else>学生</#if>
                                        )
                                        <small>${u.name!"获取用户名失败"}</small>
                                    </p>
                                </li>
                                <!-- Menu Footer-->
                                <li class="user-footer">
                                    <div class="pull-left">
                                        <a href="#"
                                           class="btn btn-default btn-flat">个人资料</a>
                                    </div>
                                    <div class="pull-right">
                                        <a href="#" onclick="NS.logout()"
                                           class="btn btn-default btn-flat">注销登录</a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    <#else>
                        <li class="dropdown messages-menu">
                            <a href="/login">
                                立即登录
                            </a>
                        </li>
                    </#if>
                </ul>
            </div>
            <!-- /.navbar-custom-menu -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</header>
