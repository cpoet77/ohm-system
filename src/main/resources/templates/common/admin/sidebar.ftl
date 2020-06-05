<!-- Left side column. contains the sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="搜索...">
                <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>
            <li class="<#if isDashboard??>active</#if>"><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>
                    <span>控制台</span></a>
            </li>
            <li class="treeview <#if isCourseManagement??>active</#if>">
                <a href="#">
                    <i class="fa fa-mortar-board"></i> <span>课程管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="<#if isCollegeManagement??>active</#if>"><a
                                href="/teachingSecretary/collegeManagement"><i class="fa fa-circle-o"></i> 学院管理</a>
                    </li>
                    <li class="<#if isMajorManagement??>active</#if>"><a
                                href="/teachingSecretary/majorManagement"><i class="fa fa-circle-o"></i> 专业管理</a></li>
                    <li class="<#if isClassManagement??>active</#if>"><a
                                href="/teachingSecretary/classManagement"><i class="fa fa-circle-o"></i> 班级管理</a></li>
                    <li class="<#if isCourseGroupManagement??>active</#if>"><a
                                href="/teachingSecretary/courseGroupManagement"><i class="fa fa-circle-o"></i> 课群管理</a>
                    </li>
                </ul>
            </li>
            <li class="treeview <#if isUserManagement??>active</#if>">
                <a href="#">
                    <i class="fa fa-group"></i> <span>用户管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="<#if isTeacherManagement??>active</#if>"><a
                                href="/teachingSecretary/teacherManagement"><i
                                    class="fa fa-circle-o"></i> 教师管理</a></li>
                    <li class="<#if isStudentManagement??>active</#if>"><a
                                href="/teachingSecretary/studentManagement"><i
                                    class="fa fa-circle-o"></i> 学生管理</a></li>
                    <li class="<#if isUserImportData??>active</#if>"><a
                                href="/teachingSecretary/importUserData"><i
                                    class="fa fa-circle-o"></i> 导入数据</a></li>
                </ul>
            </li>
            <li class="treeview <#if isSafetyManagement??>active</#if>">
                <a href="#">
                    <i class="fa fa-shield"></i> <span>安全管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="<#if isLoginLog??>active</#if>"><a href="/teachingSecretary/loginRecord"><i
                                    class="fa fa-circle-o"></i> 登录日志</a></li>
                </ul>
            </li>
            <li class="treeview <#if isSystemRelated??>active</#if>">
                <a href="#">
                    <i class="fa fa-wrench"></i> <span>系统相关</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <#if isRoles("admin")>
                        <li class="<#if isRunLog??>active</#if>"><a href="/admin/systemRunLog"><i
                                        class="fa fa-circle-o"></i> 运行日志</a></li>
                    </#if>
                    <li><a href="../tables/simple.html"><i class="fa fa-circle-o"></i> 使用手册</a></li>
                    <li><a href="../tables/data.html"><i class="fa fa-circle-o"></i> 关于应用</a></li>
                </ul>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
