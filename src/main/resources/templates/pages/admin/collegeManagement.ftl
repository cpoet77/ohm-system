<#-- 学院管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>学院管理</#assign>
<#assign isCourseManagement = true />
<#assign isCollegeManagement = true />
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
</#assign>
<#include "../../common/admin/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "../../common/admin/header.ftl" />
    <#include "../../common/admin/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                学院管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
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
                                <button type="button" class="btn bg-purple" data-toggle="modal"
                                        data-target="#saveCollegeModal">添加
                                </button>
                                <button id="importBtn" type="button" class="btn btn-warning">导入</button>
                                <button type="button" class="btn btn-success">导出</button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div v-show="uploadCollegeFileShow">
                                <form id="importCollegeForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="collegeXlsFile">请选择学院信息的表格(仅支持后缀为.xlsx的文件)&nbsp;【<a
                                                    href="/static/docs/学院信息导入模板.xlsx">下载模板</a>】</label>
                                        <input name="collegeXls" accept=".xlsx" type="file" id="collegeXlsFile">
                                    </div>
                                    <button id="submitImport" type="button" class="btn btn-warning btn-sm">立即导入</button>
                                    <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                                </form>
                                <br/>
                            </div>
                            <table id="collegeList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>学院名</th>
                                    <th>开设专业数</th>
                                    <th>学生数量</th>
                                    <th>介绍</th>
                                    <th>导入时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <!-- Modal -->
                <div class="modal fade" id="saveCollegeModal" tabindex="-1" role="dialog"
                     aria-labelledby="saveCollegeModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="saveCollegeModalLabel"
                                    v-if="saveOneCollegeInfo.id !== null">
                                    更新学院信息
                                </h4>
                                <h4 class="modal-title" id="saveCollegeModalLabel" v-else>
                                    添加学院
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="saveOneCollegeInfo">
                                    <div class="form-group">
                                        <label for="collegeName">学院名</label>
                                        <input name="collegeName" type="text" class="form-control" id="collegeName"
                                               v-model="saveOneCollegeInfo.name" placeholder="请输入学院名">
                                    </div>
                                    <div class="form-group">
                                        <label for="collegeDescription">学院简介</label>
                                        <textarea name="collegeDescription" id="collegeDescription" class="form-control"
                                                  v-model="saveOneCollegeInfo.description" rows="5"
                                                  placeholder="请输入学院简介"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="saveOneCollegeInfoSubmit" type="button" class="btn btn-primary"
                                        v-if="saveOneCollegeInfo.id !== null">更新
                                </button>
                                <button id="saveOneCollegeInfoSubmit" type="button" class="btn btn-primary" v-else>添加
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../../common/admin/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <!-- DataTables -->
    <script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script>
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    uploadCollegeFileShow: false,
                    saveOneCollegeInfo: {
                        id: null,
                        name: null,
                        description: null
                    }
                },
                methods: {
                    clearSaveOneCollegeInfo: function () {
                        Main.saveOneCollegeInfo.id = null;
                        Main.saveOneCollegeInfo.name = null;
                        Main.saveOneCollegeInfo.description = null;
                    }
                }
            });
            const datatable = $('#collegeList').DataTable({
                language: {
                    url: '/static/plugins/datatables/zh.json'
                },
                paging: true,
                lengthChange: false,
                searching: false,
                ordering: false,
                info: true,
                autoWidth: false,
                scrollX: true,
                serverSide: true,
                processing: true,
                pageLength: 30,
                ajax: (data, callback, settings) => {
                    NS.post("/teachingSecretary/collegeManagement/collegeInfoList", {
                        draw: data.draw,
                        start: data.start,
                        length: data.length
                    }, (res) => {
                        callback(res.data);
                    });
                },
                columns: [
                    {data: 'id'},
                    {data: 'name'},
                    {data: 'countMajor'},
                    {data: 'countStudent'},
                    {data: 'description'},
                    {data: 'datetime'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<div class="btn-group-sm"><button onclick="NS.updateCollegeInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteCollegeInfo(' + data.id + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    },
                ]
            });
            const saveCollegeModal = $('#saveCollegeModal');
            const saveOneCollegeInfoForm = $('#saveOneCollegeInfo');
            saveOneCollegeInfoForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    collegeName: {
                        validators: {
                            notEmpty: {
                                message: '学院名不能为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 64,
                                message: '学院名长度不符合规定'
                            }
                        }
                    }
                }
            });
            saveCollegeModal.on('hide.bs.modal', () => {
                Main.clearSaveOneCollegeInfo();
            });
            $('#saveOneCollegeInfoSubmit').on('click', () => {
                const bootstrapValidator = saveOneCollegeInfoForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    NS.post('/teachingSecretary/collegeManagement/saveOneCollegeInfo', Main.saveOneCollegeInfo, (res) => {
                        const saveOneIng = xtip.load(NS.isNull(Main.saveOneCollegeInfo.id) ? '添加中...' : '更新中...');
                        if (res.code === 1000) {
                            xtip.msg(NS.isNull(Main.saveOneCollegeInfo.id) ? '添加成功' : '更新成功', {icon: 's'});
                            if (NS.isNull(Main.saveOneCollegeInfo.id)) {
                                Main.clearSaveOneCollegeInfo();
                            } else {
                                saveCollegeModal.modal('hide');
                            }
                            datatable.ajax.reload();
                        } else {
                            xtip.msg(NS.isNull(Main.saveOneCollegeInfo.id) ? '添加失败' : '更新失败', {icon: 'e'});
                        }
                        xtip.close(saveOneIng);
                    });
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
                            if (res.data.fail !== 0) {
                                tips += '<br />错误列表：<br/><ol>';
                                let errList = res.data.errList;
                                for (let key in errList) {
                                    tips += '<li><b>学院名：</b>' + errList[key].name + '</li>';
                                }
                                tips += '</ol>';
                                tipIcon = 'w';
                            }
                            res.data.success !== 0 && datatable.ajax.reload();
                            xtip.alert(tips, tipIcon);
                        } else {
                            xtip.msg('导入失败！', {icon: 'e'})
                        }
                        xtip.close(importingMsg);
                    });
            });
            NS.deleteCollegeInfo = ((id) => {/* 删除学院信息 */
                if (NS.isNull(id)) {
                    return;
                }
                xtip.confirm('正在进行危险操作！<br /><b>确定删除吗？</b>', () => {
                    const deleteIng = xtip.load('删除中...');
                    NS.post('/teachingSecretary/collegeManagement/deleteCollegeInfo', {collegeId: id}, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('删除成功', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('删除失败', {icon: 'e'});
                        }
                        xtip.close(deleteIng);
                    })
                }, {icon: 'w'})
            });
            NS.updateCollegeInfo = ((row) => {/* 更新学院信息 */
                const data = datatable.row(row).data();
                Main.saveOneCollegeInfo.id = data.id;
                Main.saveOneCollegeInfo.name = data.name;
                Main.saveOneCollegeInfo.description = data.description;
                saveCollegeModal.modal('show');
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
