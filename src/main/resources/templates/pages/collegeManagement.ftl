<#-- 学院管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>学院管理</#assign>
<#assign isCourseManagement = true />
<#assign isCollegeManagement = true />
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
</#assign>
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
                学院管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i>首页</a></li>
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
                            <div v-show="uploadCollegeFileShow">
                                <form id="importCollegeForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="collegeXlsFile">请选择学院信息的表格(仅支持后缀为.xlsx的文件)</label>
                                        <input name="collegeXls" accept=".xlsx" type="file" id="collegeXlsFile">
                                    </div>
                                    <button id="submitImport" type="button" class="btn btn-warning btn-sm">立即导入</button>
                                    <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                                </form>
                                <br/>
                            </div>
                            <table id="collegeList" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>院系</th>
                                    <th>人数</th>
                                    <th>介绍</th>
                                    <th>导入时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
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
                language: {
                    url: '/static/plugins/datatables/zh.json'
                },
                paging: true,
                stripeClasses: ["odd", "even"],
                searching: true,
                ordering: true,
                info: true,
                autoWidth: false,
                scrollX: true,
                ajax: (data, callback, settings) => {
                    console.log(data);
                }
            });
            const importBtn = $('#importBtn');
            importBtn.on('click', () => {
                Main.uploadCollegeFileShow = !Main.uploadCollegeFileShow;
            });
            $('#closeImport').on('click', () => {
                importBtn.click();
            });
            $('#submitImport').on('click', () => {
                const importingMsg = xtip.load('导入中...');
                NS.postFile('/teachingSecretary/collegeManagement/importCollegeInfo'
                    , new FormData($('#importCollegeForm')[0]), (res) => {
                        if (res.code === 1000) {
                            let tips = '总数：' + res.data.count + '<br/>成功：' + res.data.success + '<br/>失败：' + res.data.fail;
                            let tipIcon = 's';
                            if (res.data.count !== res.data.success) {
                                tips += '<br />错误列表：<br/><ol>';
                                let errList = res.data.errList;
                                for (let key in errList) {
                                    tips += '<li><b>学院名：</b>' + errList[key].name + '</li>';
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
<#include "../common/footer.ftl" />
