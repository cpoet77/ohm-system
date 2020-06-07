<#-- 教师查看学生已上传作业的列表 -->
<#assign pageTitle>${homework.title!""}|${courseGroup.courseGroupName!""}</#assign>
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
</#assign>
<#include "../common/head.ftl"/>
<!-- Full Width Column -->
<div class="wrapper">
    <#include "../common/header.ftl"/>
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    ${homework.title!""}
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li><a href="/myCourseGroup">我的课群</a></li>
                    <li class="active">${homework.title!""}</li>
                </ol>
            </section>
            <section class="content">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class=""><a href="/homework?courseGroup=${courseGroup.id}">返回上级</a></li>
                        <li class="active"><a href="#pushHomeworkList" data-toggle="tab" aria-expanded="true">提交列表</a>
                        </li>
                        <li class=""><a href="#homeworkInfo" data-toggle="tab" aria-expanded="false">作业详情</a></li>
                    </ul>
                    <div class="tab-content">
                        <!-- /.tab-pane -->
                        <div class="tab-pane active" id="pushHomeworkList">
                            <#if (pushHomeworkSize > 0)>
                                <table id="pushHomeworkTable" class="table table-bordered table-hover dataTable"
                                       role="grid"
                                       aria-describedby="pushHomeworkTable_info">
                                    <thead>
                                    <tr role="row">
                                        <th>学院</th>
                                        <th>专业</th>
                                        <th>班级</th>
                                        <th>学号</th>
                                        <th>姓名</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list homework.pushHomework as pushHomework>
                                        <tr role="row">
                                            <td>${pushHomework.student.college.name}</td>
                                            <td>${pushHomework.student.major.name}</td>
                                            <td>${pushHomework.student.clazz.name}</td>
                                            <td>${pushHomework.student.studentId}</td>
                                            <td>${pushHomework.student.user.realName}</td>
                                            <td><a href="#">查看提交详情</a></td>
                                        </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            <#else>
                                <h3 class="text-center">暂时没有学生提交作业！</h3>
                            </#if>
                        </div>
                        <!-- /.tab-pane -->

                        <div class="tab-pane" id="homeworkInfo">
                            <h3>${homework.title!""}</h3>
                            <br/>
                            ${homework.content!"无"}
                            <br/>
                            <div class="row">
                                <#list homework.resources as resource>
                                    <div class="col-md-3 col-sm-6 col-xs-12">
                                        <div class="info-box bg-green"
                                             onclick="NS.to(NS.getSentinelResourceUrl('${resource.id}', '${resource.name}', '${resource.suffix}'))">
                                            <span class="info-box-icon bg-yellow"><i class="fa fa-files-o"></i></span>
                                            <div class="info-box-content">
                                                <span class="info-box-text">${resource.name!""}</span>
                                                <span class="info-box-number">${resource.suffix}</span>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        </div>
                        <!-- /.tab-pane -->
                    </div>
                    <!-- /.tab-content -->
                </div>
            </section>
        </div>
    </div>
    <#include "../common/copyright.ftl"/>
</div>
<#assign restFooter>
    <script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script>
        $(function () {
            $('#pushHomeworkTable').DataTable({
                language: {
                    url: '/static/plugins/datatables/zh.json'
                },
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": true,
                "info": true,
                "autoWidth": false,
                pageLength: 100
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
