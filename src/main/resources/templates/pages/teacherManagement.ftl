<#-- author: _Struggler -->
<#-- author: nsleaf www.nsleaf.cn -->
<#-- 教师管理界面 -->
<#assign  pageTitle>教师管理</#assign>
<#assign isUserManagement = true />
<#assign isTeacherManagement = true />
<#include "../common/head.ftl"/>
<div class="wrapper">
    <#include "../common/header.ftl"/>
    <#include "../common/sidebar.ftl"/>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                教师管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
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
                                <button type="button" class="btn btn-warning">添加</button>
                                <button id="importBtn" type="button" class="btn btn-warning">导入</button>
                                <button type="button" class="btn btn-success">导出</button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div v-show="uploadTeacherFileShow">
                                <form id="importTeacherForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="teacherXlsFile">请选择教职工信息表格(仅支持后缀为.xlsx的文件)</label>
                                        <input name="teacherXls" accept=".xlsx" type="file" id="teacherXlsFile">
                                    </div>
                                    <button id="submitImport" type="button" class="btn btn-warning btn-sm">立即导入</button>
                                    <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                                </form>
                            </div>
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
    <#include "../common/copyright.ftl">
</div>
<#assign restFooter>
    <!-- DataTables -->
    <script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script>
        const Main = new Vue({
            el: '#main',
            data: {
                uploadTeacherFileShow: false
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
            const importBtn = $('#importBtn');
            importBtn.on('click', () => {
                Main.uploadTeacherFileShow = !Main.uploadTeacherFileShow;
            });
            $('#closeImport').on('click', () => {
                importBtn.click();
            });
            $('#submitImport').on('click', () => {
                const importingMsg = xtip.load('导入中...');
                NS.postFile('/teachingSecretary/teacherManagement/importTeacherInfo'
                    , new FormData($('#importTeacherForm')[0]), (res) => {
                        if (res.code === 1000) {
                            let tips = '总数：' + res.data.count + '<br/>成功：' + res.data.success + '<br/>失败：' + res.data.fail;
                            let tipIcon = 's';
                            if (res.data.count !== res.data.success) {
                                tips += '<br />错误列表：<br/><ol>';
                                let errList = res.data.errList;
                                for (let key in errList) {
                                    tips += '<li><b>职工号：</b>' + errList[key].teacherId + '</li>';
                                }
                                tips += '</ol>';
                                tipIcon = 'w';
                            }
                            tips += '<br/><b>请点击确定重新加载数据！</b>';
                            xtip.confirm(tips, () => {
                                NS.reload();
                            }, {icon: tipIcon});
                        } else {
                            xtip.msg('导入失败！', {icon: 'e'})
                        }
                        xtip.close(importingMsg);
                    });
            })
        });
    </script>
</#assign>
<#include "../common/footer.ftl">