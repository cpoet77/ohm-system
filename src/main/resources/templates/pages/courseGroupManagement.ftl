<#-- 课群管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>课群管理</#assign>
<#assign isCourseManagement = true />
<#assign isCourseGroupManagement = true />
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
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                课群管理
                <!--<small>advanced tables</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="#">课程管理</a></li>
                <li class="active">课群管理</li>
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
                                data-target="#saveCourseGroupModal">添加
                                </button>
                                <button id="importBtn" type="button" class="btn btn-warning">导入</button>
                                <button type="button" class="btn btn-success">导出</button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div v-show="uploadCourseGroupFileShow">
                                <form id="importCourseGroupForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="courseGroupXlsFile">请选择课群信息的表格(仅支持后缀为.xlsx的文件)&nbsp;【<a
                                                    href="/static/docs/课群信息导入模板.xlsx">下载模板</a>】</label>
                                        <input name="courseGroupXls" accept=".xlsx" type="file" id="courseGroupXlsFile">
                                    </div>
                                    <button id="submitImport" type="button" class="btn btn-warning btn-sm">立即导入</button>
                                    <button id="closeImport" type="button" class="btn btn-info btn-sm">取消导入</button>
                                </form>
                            </div>
                            <table id="courseGroupList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>教师</th>
                                    <th>课程</th>
                                    <th>介绍</th>
                                    <th>创建时间</th>
                                    <th>状态</th>
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
            </div>
            <!-- /.row -->
            <!-- Modal -->
            <div class="modal fade" id="saveCourseGroupModal" tabindex="-1" role="dialog"
                 aria-labelledby="saveCourseGroupModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="saveCourseGroupModalLabel"
                                v-if="saveOneCourseGroupInfo.id !== null">
                                更新课群信息
                            </h4>
                            <h4 class="modal-title" id="saveCourseGroupModalLabel" v-else>
                                添加课群信息
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form id="saveOneCourseGroupInfo">
                                <div class="form-group">
                                    <label for="teacherRealName">教师</label>
                                    <input name="teacherRealName" type="text" class="form-control" id="teacherRealName"
                                           v-model="saveOneCourseGroupInfo.teacherRealName" placeholder="请输入教师">
                                </div><div class="form-group">
                                <div class="form-group">
                                    <label for="courseGroupName">课群名</label>
                                    <input name="courseGroupName" type="text" class="form-control" id="courseGroupName"
                                           v-model="saveOneCourseGroupInfo.courseGroupName" placeholder="请输入课群名">
                                </div>
                                <div>
                                    <label for="description">介绍</label>
                                    <input name="description" type="text" class="form-control" id="description"
                                           v-model="saveOneCourseGroupInfo.description" placeholder="请输入简要介绍">
                                </div>
                                    <div>
                                    <label for="state">状态</label>
                                    <input name="state" type="text" class="form-control" id="state"
                                           v-model="saveOneCourseGroupInfo.state" placeholder="请输入课程状态">
                                </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button id="saveCourseGroupInfoSubmit" type="button" class="btn btn-primary"
                                    v-if="saveOneCourseGroupInfo.id !== null">更新
                            </button>
                            <button id="saveCourseGroupInfoSubmit" type="button" class="btn btn-primary" v-else>添加
                            </button>
                        </div>
                    </div>
                </div>
            </div>
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
        /*const Main = new Vue({
            el: '#main',
            data: {
                uploadCourseGroupFileShow: false
            }
        });*/
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    uploadCourseGroupFileShow: false,
                    saveOneCourseGroupInfo: {
                        id: null,
                        teacherRealName: null,
                        courseGroupName: null,
                        description:null,
                        state:null
                    }
                },
                methods: {
                    clearSaveOneCourseGroupInfo: function () {
                        Main.saveOneCourseGroupInfo.id = null;
                        Main.saveOneCourseGroupInfo.teacherRealName = null;
                        Main.saveOneCourseGroupInfo.courseGroupName = null;
                        Main.saveOneCourseGroupInfo.description = null;
                        Main.saveOneCourseGroupInfo.state = null;
                    }
                }
            });
            const datatable = $('#courseGroupList').DataTable({
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
                    NS.post('/teachingSecretary/courseGroupManagement/courseGroupInfoList', {
                        draw: data.draw,
                        start: data.start,
                        length: data.length
                    }, (res) => {
                        callback(res.data);
                    });
                },
                columns: [
                    {data: 'id'},
                    {data: 'teacherRealName'},
                    {data: 'courseGroupName'},
                    {data: 'description'},
                    {data: 'datetime'},
                    {data: 'state'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<div class="btn-group-sm"><button onclick="NS.updateCourseGroupInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteCourseGroupInfo(' + data.id + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    }
                ]
            });
            const saveCourseGroupModal = $('#saveCourseGroupModal');
            const saveOneCourseGroupInfoForm = $('#saveOneCourseGroupInfo');
            saveOneCourseGroupInfoForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    teacherRealName: {
                        validators: {
                            notEmpty: {
                                message: '教师名不能为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 5,
                                message: '教师名不符合规定'
                            }
                        }
                    },
                    courseGroupName: {
                        validators: {
                            notEmpty: {
                                message: '课程名不能为空'
                            },
                            stringLength: {
                                min: 5,
                                max: 64,
                                message: '课程名长度不符合规定'
                            }
                        }
                    }
                }
            });
            $('#saveCourseGroupInfoSubmit').on('click', () => {
                const bootstrapValidator = saveOneCourseGroupInfoForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const saveLoad = xtip.load(NS.isNull(Main.saveOneCourseGroupInfo.id) ? '添加中...' : '更新中');
                    NS.post("/teachingSecretary/courseGroupManagement/saveOneCourseGroupInfo", {
                        id: Main.saveOneCourseGroupInfo.id,
                        teacherRealName: Main.saveOneCourseGroupInfo.teacherRealName,
                        courseGroupName: Main.saveOneCourseGroupInfo.courseGroupName,
                        description: Main.saveOneCourseGroupInfo.description,
                        state:Main.saveOneCourseGroupInfo.state
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg(NS.isNull(Main.saveOneCourseGroupInfo.id) ? '添加成功' : '更新成功', {icon: 's'});
                            datatable.ajax.reload();
                            if (NS.isNull(Main.saveOneCourseGroupInfo.id)) {
                                Main.clearSaveOneCourseGroupInfo();
                            } else {
                                saveCourseGroupModal.modal('hide');
                            }
                        } else {
                            xtip.msg(NS.isNull(Main.saveOneCourseGroupInfo.id) ? '添加失败' : '更新失败', {icon: 'e'});
                        }
                        xtip.close(saveLoad);
                    });
                }
            });
            const importBtn = $('#importBtn');
            importBtn.on('click', () => {
                Main.uploadCourseGroupFileShow = !Main.uploadCourseGroupFileShow;
            });
            $('#closeImport').on('click', () => {
                importBtn.click();
            });
            $('#submitImport').on('click', () => {
                const importingMsg = xtip.load('导入中...');
                NS.postFile('/teachingSecretary/courseGroupManagement/importCourseGroupInfo'
                    , new FormData($('#importCourseGroupForm')[0]), (res) => {
                        if (res.code === 1000) {
                            let tips = '总数：' + res.data.count + '<br/>成功：' + res.data.success + '<br/>失败：' + res.data.fail;
                            let tipIcon = 's';
                            if (res.data.count !== res.data.success) {
                                tips += '<br />错误列表：<br/><ol>';
                                let errList = res.data.errList;
                                for (let key in errList) {
                                    tips += '<li><b>课程名：</b>' + errList[key].name + '</li>';
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
            saveCourseGroupModal.on('hide.bs.modal', () => {
                Main.clearSaveOneCourseGroupInfo();
            });
            NS.updateCourseGroupInfo = (row) => {/* 更新课群信息信息 */
                const data = datatable.row(row).data();
                Main.saveOneCourseGroupInfo.id = data.id;
                Main.saveOneCourseGroupInfo.teacherRealName = data.teacherRealName;
                Main.saveOneCourseGroupInfo.courseGroupName = data.courseGroupName;
                Main.saveOneCourseGroupInfo.description = data.description;
                Main.saveOneCourseGroupInfo.state = data.state;
                saveCourseGroupModal.modal('show');
            };
            NS.deleteCourseGroupInfo = (id) => {/*删除课群*/
                xtip.confirm('你正在进行一个非常危险的操作！！<br/><b>确定删除吗？</b>', () => {
                    const deleteLoad = xtip.load('删除中...');
                    NS.post('/teachingSecretary/courseGroupManagement/deleteOneCourseGroupInfo', {id: id}, (res) => {
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
