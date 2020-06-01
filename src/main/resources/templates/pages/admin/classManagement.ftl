<#-- 学院管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>班级管理</#assign>
<#assign isCourseManagement = true />
<#assign isClassManagement = true />
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
                班级管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
                <li><a href="#">课程管理</a></li>
                <li class="active">班级管理</li>
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
                                        data-target="#saveClassModal">添加
                                </button>
                                <button type="button" class="btn bg-orange">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="classList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>学院名</th>
                                    <th>专业名</th>
                                    <th>班级名</th>
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
                <div class="modal fade" id="saveClassModal" tabindex="-1" role="dialog"
                     aria-labelledby="saveClassModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="saveClassModalLabel"
                                    v-if="saveOneClassInfo.classId !== null">
                                    更新班级信息
                                </h4>
                                <h4 class="modal-title" id="saveClassModalLabel" v-else>
                                    添加班级
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="saveOneClassInfoForm">
                                    <div class="form-group">
                                        <label for="className">班级名</label>
                                        <input name="className" type="text" class="form-control" id="className"
                                               v-model="saveOneClassInfo.className" placeholder="请输入班级名">
                                    </div>
                                    <div class="form-group">
                                        <label>选择学院</label>
                                        <select class="form-control" name="collegeId"
                                                v-model="saveOneClassInfo.collegeId">
                                            <option v-for="collegeInfo in collegeInfoList" :key="collegeInfo.id"
                                                    :value="collegeInfo.id">{{collegeInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>选择专业</label>
                                        <select class="form-control" name="majorId"
                                                v-model="saveOneClassInfo.majorId">
                                            <option v-for="majorInfo in majorInfoList" :key="majorInfo.id"
                                                    :value="majorInfo.id">{{majorInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="saveOneClassInfoSubmit" type="button" class="btn btn-primary"
                                        v-if="saveOneClassInfo.classId !== null">更新
                                </button>
                                <button id="saveOneClassInfoSubmit" type="button" class="btn btn-primary" v-else>添加
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
                    collegeInfoList: null,
                    majorInfoList: null,
                    filterMajorId: null,
                    saveOneClassInfo: {
                        classId: null,
                        className: null,
                        collegeId: null,
                        majorId: null
                    }
                },
                methods: {
                    clearSaveOneClassInfo: function () {
                        this.saveOneClassInfo.classId = null;
                        this.saveOneClassInfo.className = null;
                        this.saveOneClassInfo.collegeId = null;
                        this.saveOneClassInfo.majorId = null;
                    },
                    loadCollegeInfoList: function () {
                        NS.post("/teachingSecretary/collegeManagement/collegeInfoAllList", null, (res) => {
                            Main.collegeInfoList = res.data.colleges;
                        });
                    },
                    loadMajorInfoList: function () {
                        NS.post("/teachingSecretary/majorManagement/majorInfoListByCollege", {collegeId: this.saveOneClassInfo.collegeId}
                            , (res) => {
                                Main.majorInfoList = res.data.majors;
                            });
                    }
                },
                watch: {
                    'saveOneClassInfo.collegeId': function (newV, oldV) {
                        this.saveOneClassInfo.majorId = null;
                        this.loadMajorInfoList();
                        destroyFormValidator();
                    }
                }
            });
            const datatable = $('#classList').DataTable({
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
                pageLength: 45,
                ajax: (data, callback, settings) => {
                    NS.post("/teachingSecretary/classManagement/classInfoList", {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        majorId: Main.filterMajorId
                    }, (res) => {
                        callback(res.data);
                    });
                },
                columns: [
                    {data: 'id'},
                    {data: 'collegeName'},
                    {data: 'majorName'},
                    {data: 'name'},
                    {data: 'countStudent'},
                    {data: 'datetime'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<div class="btn-group-sm"><button onclick="NS.createCourseGroup(' + meta.row + ')" type="button" class="btn bg-navy btn-sm"><i class="fa fa-plus-square-o"></i></button> <button onclick="NS.updateClassInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteClassInfo(' + meta.row + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    },
                ]
            });
            const saveClassModal = $('#saveClassModal');
            const saveOneClassInfoForm = $('#saveOneClassInfoForm');

            function loadFormValidator() {
                saveOneClassInfoForm.bootstrapValidator({
                    verbose: false,     /* 对field内的条件按顺序验证 */
                    message: '数据校验失败',
                    fields: {
                        className: {
                            validators: {
                                notEmpty: {
                                    message: '班级名不能为空'
                                },
                                stringLength: {
                                    min: 1,
                                    max: 10,
                                    message: '班级名长度不符合规定'
                                }
                            }
                        },
                        collegeId: {
                            validators: {
                                callback: {
                                    message: '请选择本班级所属的学院',
                                    callback: function (value, validator) {
                                        return !NS.isNull(Main.saveOneClassInfo.collegeId);
                                    }
                                }
                            }
                        },
                        majorId: {
                            validators: {
                                callback: {
                                    message: '请选择本班级所学专业',
                                    callback: function (value, validator) {
                                        return !NS.isNull(Main.saveOneClassInfo.majorId);
                                    }
                                }
                            }
                        },
                    }
                });
            }

            function destroyFormValidator() {
                try {
                    saveOneClassInfoForm.data('bootstrapValidator').destroy();
                    saveOneClassInfoForm.data('bootstrapValidator', null);
                } catch (e) {
                }
            }

            $('#saveOneClassInfoSubmit').on('click', () => {
                loadFormValidator();//加载验证器
                const bootstrapValidator = saveOneClassInfoForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const saveLoad = xtip.load(NS.isNull(Main.saveOneClassInfo.classId) ? '添加中...' : '更新中');
                    NS.post("/teachingSecretary/classManagement/saveOneClassInfo", {
                        classId: Main.saveOneClassInfo.classId,
                        className: Main.saveOneClassInfo.className,
                        majorId: Main.saveOneClassInfo.majorId
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg(NS.isNull(Main.saveOneClassInfo.classId) ? '添加成功' : '更新成功', {icon: 's'});
                            datatable.ajax.reload();
                            if (NS.isNull(Main.saveOneClassInfo.classId)) {
                                Main.clearSaveOneClassInfo();
                            } else {
                                saveClassModal.modal('hide');
                            }
                        } else {
                            xtip.msg(NS.isNull(Main.saveOneClassInfo.classId) ? '添加失败' : '更新失败', {icon: 'e'});
                        }
                        xtip.close(saveLoad);
                    });
                }
            });
            saveClassModal.on('show.bs.modal', () => {
                Main.loadCollegeInfoList();
            });
            saveClassModal.on('hide.bs.modal', () => {
                Main.clearSaveOneClassInfo();
            });
            NS.deleteClassInfo = (row) => {
                const clazz = datatable.row(row).data();
                xtip.confirm('确定删除<br/><b>'+ clazz.collegeName + '&nbsp;' + clazz.majorName + '&nbsp;' + clazz.name +'</b>？', ()=> {
                    NS.post();
                }, {icon : 'w'});
            };
            NS.updateClassInfo = (row) => {
                const clazz = datatable.row(row).data();
            };
            NS.createCourseGroup = (row) => {
                const clazz = datatable.row(row).data();
            }
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
