<#-- 首页 -->
<#assign activeIndex></#assign>
<#assign pageTitle>我的资料</#assign>
<#assign personalCenter = true />
<#assign myProfile = true />
<#include "../common/admin/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "../common/admin/header.ftl" />
    <#include "../common/admin/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                我的资料
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="#">个人中心</a></li>
                <li class="active">我的资料</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box box-danger">
                <div class="box-body box-profile">
                    <img class="profile-user-img img-responsive img-circle"
                         src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                         alt="${u.name!"user"} avatar">
                    <h3 class="profile-username text-center">${u.realName!""}</h3>
                    <br/>
                    <ul class="list-group list-group-unbordered">
                        <li class="list-group-item">
                            <b>用户名：</b>${u.name!""}
                        </li>
                        <li class="list-group-item">
                            <b>身&nbsp;份：</b><#if isRoles("admin")>超级管理员<#elseif isRoles("teachingSecretary")>教学秘书<#elseif isRoles("teacher")>教师<#else>学生</#if>
                        </li>
                        <li class="list-group-item">
                            <b>性&nbsp;别：</b><#if u.sex == 'M'>男<#else>女</#if>
                        </li>
                        <li class="list-group-item">
                            <b>邮&nbsp;箱：</b>${u.email!""}
                        </li>
                        <li class="list-group-item">
                            <b>手机号：</b> ${u.phone!""}
                        </li>
                        <li class="list-group-item">
                            <div class="btn-group-sm">
                                <button type="button" class="btn-sm btn-primary">修改资料</button>
                                <button type="button" class="btn-sm btn-danger">更改密码</button>
                            </div>
                        </li>
                    </ul>
                </div>
                <!-- /.box-body -->
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/admin/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#include "../common/footer.ftl" />
