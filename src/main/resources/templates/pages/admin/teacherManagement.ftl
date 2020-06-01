<#-- author: _Struggler -->
<#-- author: nsleaf www.nsleaf.cn -->
<#-- 教师管理界面 -->
<#assign  pageTitle>教师管理</#assign>
<#assign isUserManagement = true />
<#assign isTeacherManagement = true />
<#include "../../common/admin/head.ftl"/>
<div class="wrapper">
    <#include "../../common/admin/header.ftl"/>
    <#include "../../common/admin/sidebar.ftl"/>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                教师管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i> 控制台</a></li>
                <li><a href="#">用户管理</a></li>
                <li class="active">教师管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" id="main">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <div class="btn-group">
                                <button type="button" class="btn bg-purple">添加</button>
                                <button type="button" class="btn bg-orange">导出</button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="teacherList" class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>职工号</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>邮箱</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list teachers as teacher>
                                    <tr>
                                        <td>${teacher.user.id!""}</td>
                                        <td>${teacher.user.name!""}</td>
                                        <td>${teacher.teacherId!""}</td>
                                        <td>${teacher.user.realName!""}</td>
                                        <td>${teacher.user.sex!""}</td>
                                        <td>${teacher.user.email!""}</td>
                                        <td>
                                            <div class="btn-group-sm">
                                                <button type="button" class="btn btn-warning btn-sm"><i
                                                            class="fa fa-pencil-square-o"></i></button>
                                                <button type="button" class="btn btn-danger btn-sm"><i
                                                            class="fa fa-trash-o"></i></button>
                                            </div>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>

    </div>
    <!-- /.content -->
    <#include "../../common/admin/copyright.ftl">
</div>
<#assign restFooter>
    <!-- DataTables -->
    <script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script>
        const Main = new Vue({
            el: '#main',
            data: {

            }
        });
        $(function () {
            $('#teacherList').DataTable({
                "paging": true,
                "lengthChange": false,
                "searching": true,
                "ordering": true,
                "info": true,
                "autoWidth": false
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl">