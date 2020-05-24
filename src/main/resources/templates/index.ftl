<#-- 首页 -->
<#assign activeIndex></#assign>
<#assign pageTitle>OHMS控制台</#assign>
<#assign isDashboard = true />
<#include "common/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "common/header.ftl" />
    <#include "common/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                控制台
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="callout callout-info">
                <h4>OhmSubsystem，作业在线提交系统</h4>

                <p>准备好了吗？让学生提交第一份作业！</p>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#include "common/footer.ftl" />
