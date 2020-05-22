<#-- 专业管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>专业课群</#assign>
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
                <li class="active">专业管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
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
                            <div v-show="uploadMajorFileShow">
                                <form id="importMajorForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="majorXlsFile">请选择专业信息的表格(仅支持后缀为.xlsx的文件)</label>
                                        <input name="majorXls" accept=".xlsx" type="file" id="majorXlsFile">
                                    </div>
                                    <button id="submitImport" type="button" class="btn btn-warning btn-sm">立即导入</button>
                                    <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                                </form>
                            </div>
                            <table id="majorList" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>学制</th>
                                    <th>学科</th>
                                    <th>导入时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list majors as major>
                                    <tr>
                                        <th>${major.id!""}</th>
                                        <td>${major.college.id!""}</td>
                                        <td>${major.name!""}</td>
                                        <td>4</td>
                                        <th>工学</th>
                                        <td>${major.datatime!""}</td>
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
                uploadMajorFileShow: false
            }
        });
        $(function () {
            $('#majorList').DataTable({
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": true,
                "info": true,
                "autoWidth": false
            });
            const importBtn = $('#importBtn');
            importBtn.on('click', () => {
                Main.uploadmajorFileShow = !Main.uploadMajorFileShow;
            });
            $('#closeImport').on('click', () => {
                importBtn.click();
            });
            $('#submitImport').on('click', () => {
                const importingMsg = xtip.load('导入中...');
                NS.postFile('/teachingSecretary/majorManagement/importMajorInfo'
                    , new FormData($('#importMajorForm')[0]), (res) => {
                        if (res.code === 1000) {
                            let tips = '总数：' + res.data.count + '<br/>成功：' + res.data.success + '<br/>失败：' + res.data.fail;
                            let tipIcon = 's';
                            if (res.data.count !== res.data.success) {
                                tips += '<br />错误列表：<br/><ol>';
                                let errList = res.data.errList;
                                for (let key in errList) {
                                    tips += '<li><b>专业名：</b>' + errList[key].name + '</li>';
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
                    });
                xtip.close(importingMsg);
            })
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
