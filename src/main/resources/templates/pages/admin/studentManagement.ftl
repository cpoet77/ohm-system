<#-- author: _Struggler -->
<#-- 学生管理界面 -->
<#assign  pageTitle>学生管理</#assign>
<#assign isUserManagement = true />
<#assign isStudentManagement = true />
<#include "../../common/admin/head.ftl"/>
<div class="wrapper">
    <#include "../../common/admin/header.ftl"/>
    <#include "../../common/admin/sidebar.ftl"/>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                学生管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i> 控制台</a></li>
                <li><a href="#">用户管理</a></li>
                <li class="active">学生管理</li>
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
                            <form id="filterForm">
                                <div class="form-group">
                                    <label>Select</label>
                                    <select class="form-control">
                                        <option>数信学院</option>
                                        <option>option 2</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="studentList" class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>班级</th>
                                    <th>专业名</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list students as student>
                                    <tr>
                                        <td>${student.id!""}</td>
                                        <td>${student.name!""}</td>
                                        <td>${student.studentId!""}</td>
                                        <td>${student.realName!""}</td>
                                        <td>${student.sex!""}</td>
                                        <td>${student.className!""}</td>
                                        <td>${student.majorName!""}</td>
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
            data: {}
        });
        $(function () {
            $('#studentList').DataTable({
                "paging": true,
                "lengthChange": true,
                "searching": true,
                "ordering": true,
                "info": true,
                "autoWidth": true
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl">