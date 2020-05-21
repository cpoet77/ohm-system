<#-- 面向普通用户的顶部导航栏 -->
<header class="main-header">
    <nav class="navbar navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <a href="/" class="navbar-brand">${OHMS_NAME_FORMAT}</a>
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
                            <@spring.message "dict.head.myCourses" />
                            <#if activeIndex??><span class="sr-only">(current)</span></#if>
                        </a>
                    </li>
                    <li <#if activeDailyMood??>class="active"</#if>>
                        <a href="/reception/dailyMood">
                            <@spring.message "dict.head.dailyMood" />
                            <#if activeDailyMood??><span class="sr-only">(current)</span></#if>
                        </a>
                    </li>
                    <li <#if activePlaza??>class="active"</#if>>
                        <a href="/reception/plaza">
                            <@spring.message "dict.head.learningPlaza" />
                            <#if activePlaza??><span class="sr-only">(current)</span></#if>
                        </a>
                    </li>
                    <li <#if activeOhmmeter??>class="active"</#if>>
                        <a href="/ohmmeter">
                            <@spring.message "dict.head.ohmLeaderboard" />
                            <#if activeOhmmeter??><span class="sr-only">(current)</span></#if>
                        </a>
                    </li>
                </ul>
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" id="navbar-search-input"
                               placeholder="<@spring.message "dict.common.search"/>">
                    </div>
                </form>
            </div>
            <!-- /.navbar-collapse -->
            <#if !(notUser??)>
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
                                    <!-- The user image in the navbar-->
                                    <img src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                         class="user-image" alt="${u.name!""} avatar">
                                    <!-- hidden-xs hides the username on small devices so only the image appears. -->
                                    <span class="hidden-xs">${u.name!""}</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- The user image in the menu -->
                                    <li class="user-header">
                                        <img src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                             class="img-circle" alt="${u.name!""} avatar">

                                        <p>
                                            ${u.name!""}
                                            <small>${u.email}</small>
                                        </p>
                                    </li>
                                    <!-- Menu Footer-->
                                    <li class="user-footer">
                                        <div class="pull-left">
                                            <a href="/user/profile"
                                               class="btn btn-default btn-flat"><@spring.message "dict.common.profile" /></a>
                                        </div>
                                        <div class="pull-right">
                                            <a href="#" onclick="NS.logout()"
                                               class="btn btn-default btn-flat"><@spring.message "dict.common.signOut"/></a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                        <#else>
                            <li class="dropdown messages-menu">
                                <a href="/login">
                                    <@spring.message "dict.common.signIn" />
                                </a>
                            </li>
                            <li class="dropdown messages-menu">
                                <a href="/register">
                                    <@spring.message "dict.common.signUp" />
                                </a>
                            </li>
                        </#if>
                    </ul>
                </div>
            </#if>
            <!-- /.navbar-custom-menu -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</header>
