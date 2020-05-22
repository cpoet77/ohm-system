<#-- 学院管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>学院管理</#assign>
<#include "../common/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "../common/header.ftl" />
    <#include "../common/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                课程管理
                <!--<small>advanced tables</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="#">课程管理</a></li>
                <li class="active">学院管理</li>
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
                            <form id="importCollegeForm" v-if="uploadCollegeFileShow">
                                <div class="form-group">
                                    <label for="collegeXlsFile">请选择学院信息的表格(仅支持后缀为.xls的文件)</label>
                                    <input name="collegeXls" type="file" id="collegeXlsFile">
                                </div>
                                <button id="submitImport" type="button" class="btn btn-warning btn-sm">导入</button>
                                <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                            </form>
                            <table id="collegeList" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>院系</th>
                                    <th>人数</th>
                                    <th>介绍</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list colleges as college>
                                    <tr>
                                        <th>${college.id}</th>
                                        <td>${college.name}</td>
                                        <td>1500</td>
                                        <td>${college.description}</td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <!-- DataTables -->
    <script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script>
        const Main = new Vue({
            el: '#main',
            data: {
                uploadCollegeFileShow: false
            }
        });
        $(function () {
            $('#collegeList').DataTable({
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": true,
                "info": true,
                "autoWidth": false
            });
            $('#importBtn').on('click', () => {
                Main.uploadCollegeFileShow = !Main.uploadCollegeFileShow;
            });
            $('#closeImport').on('click', $('importBtn').click);
            $('#submitImport').on('click', () => {
                NS.post('/teachingSecretary/collegeManagement/importCollegeInfo', $('#importCollegeForm').serializeArray()
                    , (res) => {

                    });
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
