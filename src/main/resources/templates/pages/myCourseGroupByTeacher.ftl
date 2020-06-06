<#assign activeCourseGroup = true />
<#assign pageTitle>我的课群</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl">
    <!-- Full Width Column -->
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    我的课群
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li><a href="#">我的课群</a></li>
                    <li class="active">教师</li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <!-- 课群搜索 -->
                    <div class="col-md-offset-2 col-md-8">
                        <form method="get">
                            <div class="input-group margin">
                                <input name="search" type="text" class="form-control" placeholder="查找课群，例如：Java"
                                       <#if search??>value="${search}"</#if>>
                                <div class="input-group-btn">
                                    <button type="submit" class="btn bg-purple">查找</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- /.row -->
                <!-- 课群显示 -->
                <div class="box box-info">
                    <div class="box-body">
                        <#list courseGroupListVo.courseGroups as courseGroup>
                            <div class="col-md-3">
                                <div class="box box-widget widget-user">
                                    <div class="widget-user-header bg-black"
                                         style="background: url('../../static/dist/img/photo1.png') center center;">
                                    </div>
                                    <div class="widget-user-image">
                                        <img class="img-circle"
                                             src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                             alt="avatar">
                                    </div>
                                    <div class="box-footer">
                                        <h3 class="profile-username text-center">${courseGroup.name!""}</h3>
                                        <ul class="list-group list-group-unbordered">
                                            <li class="list-group-item">
                                                <b>状态</b>
                                                <a href="javascript:void(0)" class="pull-right">
                                                    <#if courseGroup.state>进行中<#else>已结束</#if>
                                                </a>
                                            </li>
                                        </ul>
                                        <a href="#" class="btn btn-primary btn-block"><b>进入</b></a>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                    <#if (courseGroupListVo.page > 1)>
                        <div class="box-footer">
                            <nav aria-label="courseGroup page">
                                <ul class="pager">
                                    <li class="previous <#if (page == 0)>disabled</#if>">
                                        <a href="<#if (page == 0)>javascript:void(0)<#else>?page=${page - 1}&search=${search!""}</#if>"><span
                                                    aria-hidden="true">&larr;</span>
                                            上一页</a></li>
                                    <li class="next <#if (page + 1 == courseGroupListVo.page)>disabled</#if>">
                                        <a href="<#if (page + 1 == courseGroupListVo.page)>javascript:void(0)<#else>?page=${page + 1}&search=${search!""}</#if>">下一页<span
                                                    aria-hidden="true">&rarr;</span></a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </#if>
                </div>
            </section>
        </div>
        <!-- /.container -->
    </div>
</div>
<#include "../common/copyright.ftl">
<!-- /.content-wrapper -->
<#include "../common/footer.ftl">
