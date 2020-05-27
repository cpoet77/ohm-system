<#-- 专业管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>专业管理</#assign>
<#assign isCourseManagement = true />
<#assign isMajorManagement = true />
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
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
                课程管理
                <!--<small>advanced tables</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="#">课程管理</a></li>
                <li class="active">专业管理</li>
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
                                        data-target="#saveMajorModal">添加
                                </button>
                                <button id="importBtn" type="button" class="btn btn-warning">导入</button>
                                <button type="button" class="btn btn-success">导出</button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div v-show="uploadMajorFileShow">
                                <form id="importMajorForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="majorXlsFile">请选择专业信息的表格(仅支持后缀为.xlsx的文件)&nbsp;【<a
                                                    href="/static/docs/专业信息导入模板.xlsx">下载模板</a>】</label>
                                        <input name="majorXls" accept=".xlsx" type="file" id="majorXlsFile">
                                    </div>
                                    <button id="submitImport" type="button" class="btn btn-warning btn-sm">立即导入</button>
                                    <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                                </form>
                            </div>
                            <table id="majorList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>专业人数</th>
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
                <div class="modal fade" id="saveMajorModal" tabindex="-1" role="dialog"
                     aria-labelledby="saveMajorModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="saveMajorModalLabel"
                                    v-if="saveOneMajorInfo.id !== null">
                                    更新专业信息
                                </h4>
                                <h4 class="modal-title" id="saveMajorModalLabel" v-else>
                                    添加专业
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="saveOneMajorInfo">
                                    <div class="form-group">
                                        <label for="majorName">专业名</label>
                                        <input name="majorName" type="text" class="form-control" id="majorName"
                                               v-model="saveOneMajorInfo.name" placeholder="请输入专业名">
                                    </div>
                                    <div class="form-group">
                                        <label>选择学院</label>
                                        <select class="form-control" name="collegeId"
                                                v-model="saveOneMajorInfo.collegeId">
                                            <option v-for="collegeInfo in collegeInfoList" :key="collegeInfo.id"
                                                    :value="collegeInfo.id">{{collegeInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="saveMajorInfoSubmit" type="button" class="btn btn-primary"
                                        v-if="saveOneMajorInfo.id !== null">更新
                                </button>
                                <button id="saveMajorInfoSubmit" type="button" class="btn btn-primary" v-else>添加
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
    <#include "../common/copyright.ftl" />
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
                    uploadMajorFileShow: false,
                    saveOneMajorInfo: {
                        id: null,
                        name: null,
                        collegeId: null
                    },
                    collegeInfoList: null
                },
                methods: {
                    clearSaveOneMajorInfo: function () {
                        Main.saveOneMajorInfo.id = null;
                        Main.saveOneMajorInfo.name = null;
                        Main.saveOneMajorInfo.collegeId = null;
                    }
                }
            });
            const datatable = $('#majorList').DataTable({
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
                pageLength: 35,
                ajax: (data, callback, settings) => {
                    NS.post('/teachingSecretary/majorManagement/majorInfoList', {
                        draw: data.draw,
                        start: data.start,
                        length: data.length
                    }, (res) => {
                        callback(res.data);
                    });
                },
                columns: [
                    {data: 'id'},
                    {data: 'college'},
                    {data: 'name'},
                    {data: 'countStudents'},
                    {data: 'datetime'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<div class="btn-group-sm"><button onclick="NS.updateMajorInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteMajorInfo(' + data.id + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    }
                ]
            });
            const saveMajorModal = $('#saveMajorModal');
            const saveOneMajorInfoForm = $('#saveOneMajorInfo');
            saveOneMajorInfoForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    majorName: {
                        validators: {
                            notEmpty: {
                                message: '专业名不能为空'
                            },
                            stringLength: {
                                min: 4,
                                max: 64,
                                message: '专业名长度不符合规定'
                            }
                        }
                    },
                    collegeId: {
                        validators: {
                            notEmpty: {
                                message: '请选择学院'
                            }
                        }
                    }
                }
            });
            $('#saveMajorInfoSubmit').on('click', () => {
                const bootstrapValidator = saveOneMajorInfoForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const saveLoad = xtip.load(NS.isNull(Main.saveOneMajorInfo.id) ? '添加中...' : '更新中');
                    NS.post("/teachingSecretary/majorManagement/saveOneMajorInfo", {
                        id: Main.saveOneMajorInfo.id,
                        majorName: Main.saveOneMajorInfo.name,
                        collegeId: Main.saveOneMajorInfo.collegeId
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg(NS.isNull(Main.saveOneMajorInfo.id) ? '添加成功' : '更新成功', {icon: 's'});
                            datatable.ajax.reload();
                            if (NS.isNull(Main.saveOneMajorInfo.id)) {
                                Main.clearSaveOneMajorInfo();
                            } else {
                                saveMajorModal.modal('hide');
                            }
                        } else {
                            xtip.msg(NS.isNull(Main.saveOneMajorInfo.id) ? '添加失败' : '更新失败', {icon: 'e'});
                        }
                        xtip.close(saveLoad);
                    });
                }
            });
            const importBtn = $('#importBtn');
            importBtn.on('click', () => {
                Main.uploadMajorFileShow = !Main.uploadMajorFileShow;
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
                        xtip.close(importingMsg);
                    });
            });
            saveMajorModal.on('show.bs.modal', () => {
                NS.post("/teachingSecretary/collegeManagement/collegeInfoAllList", null, (res) => {
                    Main.collegeInfoList = res.data.colleges;
                });
            });
            saveMajorModal.on('hide.bs.modal', () => {
                Main.clearSaveOneMajorInfo();
            });
            NS.updateMajorInfo = (row) => {/* 更新专业信息 */
                const data = datatable.row(row).data();
                Main.saveOneMajorInfo.id = data.id;
                Main.saveOneMajorInfo.name = data.name;
                Main.saveOneMajorInfo.collegeId = data.collegeId;
                saveMajorModal.modal('show');
            };
            NS.deleteMajorInfo = (majorId) => {/*删除专业*/
                xtip.confirm('你正在进行一个非常危险的操作！！<br/><b>确定删除吗？</b>', () => {
                    const deleteLoad = xtip.load('删除中...');
                    NS.post('/teachingSecretary/majorManagement/deleteOneMajorInfo', {majorId: majorId}, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('删除成功！', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('删除失败！', {icon: 'e'});
                        }
                        xtip.close(deleteLoad);
                    });
                }, {icon: 'w'})
            }
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
