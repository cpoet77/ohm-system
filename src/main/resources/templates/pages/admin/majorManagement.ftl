<#-- 专业管理 -->
<#assign pageTitle>专业管理</#assign>
<#assign isCourseManagement = true />
<#assign isMajorManagement = true />
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
                专业管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
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
                                <button type="button" class="btn bg-orange" id="exportXlsxBtn">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="majorList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>学院</th>
                                    <th>专业名</th>
                                    <th>学生数量</th>
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
                                        <label>学院</label>
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
                <div class="modal fade" id="dataFilterModal" tabindex="-1" role="dialog"
                     aria-labelledby="dataFilterModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="dataFilterModalLabel">
                                    数据过滤
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="dataFilterForm">
                                    <div class="form-group">
                                        <label>选择学院</label>
                                        <select class="form-control" name="collegeId"
                                                v-model="filterDataCollegeId">
                                            <option value="null">所有学院</option>
                                            <option v-for="collegeInfo in collegeInfoList" :key="collegeInfo.id"
                                                    :value="collegeInfo.id">{{collegeInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                </form>
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
    <script src="/static/plugins/datatables/dataTables.buttons.min.js"></script>
    <script src="/static/plugins/datatables/jszip.min.js"></script>
    <script src="/static/plugins/datatables/buttons.html5.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script>
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    saveOneMajorInfo: {
                        id: null,
                        name: null,
                        collegeId: null
                    },
                    collegeInfoList: null,
                    filterDataCollegeId: null,
                    filterDataReloadFlag: false
                },
                watch: {
                    filterDataCollegeId: function (newVal, oldVal) {
                        this.filterDataReloadFlag = true;
                    }
                },
                methods: {
                    clearSaveOneMajorInfo: function () {
                        Main.saveOneMajorInfo.id = null;
                        Main.saveOneMajorInfo.name = null;
                        Main.saveOneMajorInfo.collegeId = null;
                    },
                    loadCollegeInfoList: function () {
                        NS.post("/teachingSecretary/collegeManagement/collegeInfoAllList", null, (res) => {
                            Main.collegeInfoList = res.data.colleges;
                        });
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
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'excelHtml5',
                        text: 'export-Vice button',
                        filename: '专业信息-${siteTitle}-' + NS.uuid(),
                        title: '专业信息-${siteTitle}',
                        className: 'hidden',
                        exportOptions: {
                            columns: [1, 2, 3, 4]
                        }
                    }
                ],
                ajax: (data, callback, settings) => {
                    NS.post('/teachingSecretary/majorManagement/majorInfoList', {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        filterCollegeId: Main.filterDataCollegeId === 'null' ? null : Main.filterDataCollegeId,
                    }, (res) => {
                        if (res.code === 1000) {
                            callback(res.data);
                        } else {
                            xtip.msg('加载数据出错！', {icon: 'e'});
                        }
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
            $('#exportXlsxBtn').on('click', () => {
                $('.dt-buttons .buttons-excel').click();
            });
            const saveMajorModal = $('#saveMajorModal');
            const dataFilterModal = $('#dataFilterModal');
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
                            callback: {
                                message: '请选择本专业所属的学院',
                                callback: function (value, validator) {
                                    return !NS.isNull(Main.saveOneMajorInfo.collegeId);
                                }
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
            saveMajorModal.on('show.bs.modal', () => {
                Main.loadCollegeInfoList();
            });
            dataFilterModal.on('show.bs.modal', () => {
                Main.loadCollegeInfoList();
            });
            saveMajorModal.on('hide.bs.modal', () => {
                Main.clearSaveOneMajorInfo();
            });
            dataFilterModal.on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
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
<#include "../../common/footer.ftl" />
