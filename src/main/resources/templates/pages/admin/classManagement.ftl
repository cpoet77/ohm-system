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
                                <button type="button" class="btn bg-orange" id="exportXlsxBtn">导出</button>
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
                                        <label>学院</label>
                                        <select class="form-control" name="collegeId"
                                                v-model="saveOneClassInfo.collegeId">
                                            <option v-for="collegeInfo in collegeInfoList" :key="collegeInfo.id"
                                                    :value="collegeInfo.id">{{collegeInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>专业</label>
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
                                        <select class="form-control" v-model="filterCollegeId">
                                            <option value="-1">所有学院</option>
                                            <option v-for="collegeInfo in collegeInfoList" :key="collegeInfo.id"
                                                    :value="collegeInfo.id">{{collegeInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group" v-if="filterCollegeId != -1">
                                        <label>选择专业</label>
                                        <select class="form-control" v-model="filterMajorId">
                                            <option value="-1">所有专业</option>
                                            <option v-for="majorInfo in majorInfoList" :key="majorInfo.id"
                                                    :value="majorInfo.id">{{majorInfo.name}}
                                            </option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="createCourseGroupModal" tabindex="-1" role="dialog"
                     aria-labelledby="createCourseGroupModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="createCourseGroupModalLabel">
                                    快速创建课群
                                </h4>
                            </div>
                            <div class="modal-body">
                                <p><b>提示：</b>该操作将把本班级中的所有学生加入到新建的课群当中。</p>
                                <p class="text-red"><b>当前进行操作的班级为：</b>{{createCourseGroupInfo.classInfo}}</p>
                                <hr/>
                                <form id="createCourseGroupForm">
                                    <div class="form-group">
                                        <label for="courseGroupName">课群名称</label>
                                        <input name="courseGroupName" type="text" class="form-control"
                                               id="courseGroupName" v-model="createCourseGroupInfo.courseGroupName"
                                               placeholder="请输入课群名称">
                                    </div>
                                    <div class="form-group">
                                        <label for="teacherId">教师用户名</label>
                                        <input name="teacherId" type="text" class="form-control"
                                               id="teacherId" v-model="createCourseGroupInfo.teacherId"
                                               placeholder="请输入教师用户名">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="createCourseGroupBtn" type="button" class="btn btn-primary">创建</button>
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
                    collegeInfoList: null,
                    majorInfoList: null,
                    filterCollegeId: -1,
                    filterMajorId: -1,
                    firstOpenUpdateClassModalFlag: false,
                    filterDataReloadFlag: false,
                    saveOneClassInfo: {
                        classId: null,
                        className: null,
                        collegeId: null,
                        majorId: null
                    },
                    createCourseGroupInfo: {
                        classId: null,
                        classInfo: '',
                        courseGroupName: null,
                        teacherId: null
                    }
                },
                methods: {
                    clearSaveOneClassInfo: function () {
                        this.saveOneClassInfo.classId = null;
                        this.saveOneClassInfo.className = null;
                        this.saveOneClassInfo.collegeId = null;
                        this.saveOneClassInfo.majorId = null;
                    },
                    clearCreateCourseGroupInfo: function () {
                        this.createCourseGroupInfo.classId = null;
                        this.createCourseGroupInfo.classInfo = '';
                        this.createCourseGroupInfo.courseGroupName = null;
                        this.createCourseGroupInfo.teacherId = null;
                    },
                    loadCollegeInfoList: function () {
                        NS.post("/teachingSecretary/collegeManagement/collegeInfoAllList", null, (res) => {
                            Main.collegeInfoList = res.data.colleges;
                        });
                    },
                    loadMajorInfoList: function (collegeId) {
                        NS.post("/teachingSecretary/majorManagement/majorInfoListByCollege", {collegeId: collegeId}, (res) => {
                            Main.majorInfoList = res.data.majors;
                        });
                    }
                },
                watch: {
                    'saveOneClassInfo.collegeId': function (newV, oldV) {
                        if (this.firstOpenUpdateClassModalFlag) {
                            this.firstOpenUpdateClassModalFlag = false;
                        } else {
                            this.saveOneClassInfo.majorId = null;
                        }
                        this.loadMajorInfoList(this.saveOneClassInfo.collegeId);
                        destroyFormValidator();
                    },
                    filterCollegeId: function (newV, oldV) {
                        if (newV === -1 || newV === '-1') {
                            this.filterMajorId = -1;
                        } else {
                            this.loadMajorInfoList(this.filterCollegeId)
                        }
                        this.filterDataReloadFlag = true;
                    },
                    filterMajorId: function (newV, oldV) {
                        this.filterDataReloadFlag = true;
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
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'excelHtml5',
                        text: 'export-Vice button',
                        filename: '班级信息-${siteTitle}-' + NS.uuid(),
                        title: '班级信息-${siteTitle}',
                        className: 'hidden',
                        exportOptions: {
                            columns: [1, 2, 3, 4, 5, 6]
                        }
                    }
                ],
                ajax: (data, callback, settings) => {
                    NS.post("/teachingSecretary/classManagement/classInfoList", {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        collegeId: Main.filterCollegeId,
                        majorId: Main.filterMajorId
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
            $('#exportXlsxBtn').on('click', () => {
                $('.dt-buttons .buttons-excel').click();
            });
            const saveClassModal = $('#saveClassModal');
            const dataFilterModal = $('#dataFilterModal');
            const createCourseGroupModal = $('#createCourseGroupModal');
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
                        }
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
            dataFilterModal.on('show.bs.modal', () => {
                Main.loadCollegeInfoList();
            });
            dataFilterModal.on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
            });
            NS.deleteClassInfo = (row) => {
                const clazz = datatable.row(row).data();
                xtip.confirm('确定删除<br/><b>' + clazz.collegeName + '&nbsp;' + clazz.majorName + '&nbsp;' + clazz.name + '</b>？', () => {
                    NS.post('/teachingSecretary/classManagement/deleteClass', {classId: clazz.id}, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('删除成功！', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('删除失败！', {icon: 'e'});
                        }
                    });
                }, {icon: 'w'});
            };
            NS.updateClassInfo = (row) => {
                const clazz = datatable.row(row).data();
                Main.saveOneClassInfo.classId = clazz.id;
                Main.saveOneClassInfo.className = clazz.name;
                Main.saveOneClassInfo.collegeId = clazz.collegeId;
                Main.saveOneClassInfo.majorId = clazz.majorId;
                Main.loadMajorInfoList(clazz.collegeId);
                Main.firstOpenUpdateClassModalFlag = true;
                saveClassModal.modal('show');
            };
            NS.createCourseGroup = (row) => {
                const clazz = datatable.row(row).data();
                if (clazz.countStudent <= 0) {
                    xtip.msg('该班级一个学生也没有，不允许的操作！', {icon: 'e'});
                    return;
                }
                Main.createCourseGroupInfo.classId = clazz.id;
                Main.createCourseGroupInfo.classInfo = clazz.collegeName + ' ' + clazz.majorName + ' ' + clazz.name;
                createCourseGroupModal.modal('show');
            };
            createCourseGroupModal.on('hide.bs.modal', () => {
                Main.clearCreateCourseGroupInfo();
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
