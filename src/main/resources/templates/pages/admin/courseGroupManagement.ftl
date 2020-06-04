<#-- 课群管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>课群管理</#assign>
<#assign isCourseManagement = true />
<#assign isCourseGroupManagement = true />
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
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                课群管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
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
                                        data-target="#addCourseGroupModal">添加
                                </button>
                                <button type="button" class="btn bg-orange" id="exportXlsxBtn">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="courseGroupList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>课群名</th>
                                    <th>教职工号</th>
                                    <th>教师姓名</th>
                                    <th>学生数</th>
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
                <!-- Modal -->
                <div class="modal fade" id="addCourseGroupModal" tabindex="-1" role="dialog"
                     aria-labelledby="addCourseGroupModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="addCourseGroupModalLabel">
                                    添加课群
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="addCourseGroupForm">
                                    <div class="form-group">
                                        <label for="courseGroupName">课群名</label>
                                        <input name="courseGroupName" type="text" class="form-control"
                                               id="courseGroupName"
                                               v-model="addCourseGroupInfo.courseGroupName" placeholder="请输入课群名">
                                    </div>
                                    <div class="form-group">
                                        <label for="teacherId">教职工号</label>
                                        <input name="teacherId" type="number" class="form-control" id="teacherId"
                                               v-model="addCourseGroupInfo.teacherId" placeholder="请输入课群教师的教职工号">
                                    </div>
                                    <div class="form-group">
                                        <label for="description">课群介绍</label>
                                        <textarea name="description" id="description" class="form-control"
                                                  v-model="addCourseGroupInfo.description" rows="5"
                                                  placeholder="请输入课群介绍"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="studentIds">加入课群的学生(<a
                                                    href="javascript:void(0)" onclick="NS.showLoadStudentIdsTxt()">#从txt文件加载#</a>)</label>
                                        <div v-show="loadStudentIdsTxtFormShow">
                                            <div class="form-group">
                                                <input id="loadStudentIdsTxtIpt" type="file" accept=".txt"
                                                       name="studentIdsTxtFile"
                                                       id="studentIdsTxtFile"/>
                                            </div>
                                            <button id="loadStudentIdsTxtBtn" type="button"
                                                    class="btn btn-warning btn-sm">
                                                加载
                                            </button>
                                            <button id="closeStudentIdsTxtBtn" type="button"
                                                    class="btn btn-danger btn-sm">
                                                取消
                                            </button>
                                        </div>
                                        <textarea name="studentIds" id="studentIds" class="form-control"
                                                  v-model="addCourseGroupInfo.studentIds" rows="5"
                                                  placeholder="填写学生学号，每行一个！"
                                                  v-show="!loadStudentIdsTxtFormShow"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="addCourseGroupBtn" type="button" class="btn btn-primary">添加
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="updateCourseGroupModal" tabindex="-1" role="dialog"
                     aria-labelledby="updateCourseGroupModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="updateCourseGroupModalLabel">
                                    更新课群信息
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="updateCourseGroupForm">
                                    <div class="form-group">
                                        <label for="courseGroupName">课群名</label>
                                        <input name="courseGroupName" type="text" class="form-control"
                                               id="courseGroupName"
                                               v-model="updateCourseInfo.courseGroupName" placeholder="请输入课群名">
                                    </div>
                                    <div class="form-group">
                                        <label for="teacherId">教职工号</label>
                                        <input name="teacherId" type="number" class="form-control" id="teacherId"
                                               v-model="updateCourseInfo.teacherId" placeholder="请输入课群教师的教职工号">
                                    </div>
                                    <div class="form-group">
                                        <label for="description">课群介绍</label>
                                        <textarea name="description" id="description" class="form-control"
                                                  v-model="updateCourseInfo.description" rows="5"
                                                  placeholder="请输入课群介绍"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="updateCourseGroupBtn" type="button" class="btn btn-primary">更新
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
                                        <label for="filterStudentName">课群名( <= 64个字符)</label>
                                        <input name="filterStudentName" maxlength="64" type="text" class="form-control"
                                               id="filterStudentName" v-model="filterData.courseGroupName"
                                               placeholder="例如：JAVA实践课程">
                                    </div>
                                    <div class="form-group">
                                        <label for="filterStudentId">教职工号( <= 24个字符)</label>
                                        <input name="filterStudentId" maxlength="24" type="number" class="form-control"
                                               id="filterStudentId" v-model="filterData.teacherId"
                                               placeholder="例如：201742010">
                                    </div>
                                    <div class="form-group">
                                        <label for="logicalRadios">过滤关系</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" v-model="filterData.logical" id="logicalRadios"
                                                       value="0">AND
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" v-model="filterData.logical" id="logicalRadios"
                                                       value="1">OR
                                            </label>
                                        </div>
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
                    loadStudentIdsTxtFormShow: false,
                    filterDataReloadFlag: false,
                    addCourseGroupInfo: {
                        courseGroupName: null,
                        teacherId: null,
                        description: null,
                        studentIds: null
                    },
                    updateCourseInfo: {
                        courseGroupId: null,
                        courseGroupName: null,
                        teacherId: null,
                        description: null
                    },
                    filterData: {
                        courseGroupName: null,
                        teacherId: null,
                        logical: 0
                    }
                },
                methods: {
                    clearAddCourseGroupInfo: function () {
                        this.addCourseGroupInfo.courseGroupId = null;
                        this.addCourseGroupInfo.courseGroupName = null;
                        this.addCourseGroupInfo.teacherId = null;
                        this.addCourseGroupInfo.description = null;
                        this.addCourseGroupInfo.studentIds = null;
                        this.addCourseGroupInfo.state = false;
                    },
                    clearUpdateCourseGroupInfo: function () {
                        this.updateCourseInfo = {
                            courseGroupId: null,
                            courseGroupName: null,
                            teacherId: null,
                            description: null
                        };
                    },
                    testStudentId: function (studentId) {
                        let regexp = new RegExp('^[1-9][0-9]{11}$');
                        return regexp.test(studentId);
                    }
                },
                watch: {
                    'filterData.courseGroupName': function () {
                        this.filterDataReloadFlag = true;
                    },
                    'filterData.teacherId': function () {
                        this.filterDataReloadFlag = true;
                    },
                    'filterData.logical': function () {
                        this.filterDataReloadFlag = true;
                    },
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
                pageLength: 45,
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'excelHtml5',
                        text: 'export-Vice button',
                        filename: '课群信息-${siteTitle}-' + NS.uuid(),
                        title: '课群信息-${siteTitle}',
                        className: 'hidden',
                        exportOptions: {
                            columns: [1, 2, 3, 4, 5]
                        }
                    }
                ],
                ajax: (data, callback, settings) => {
                    NS.post('/teachingSecretary/courseGroupManagement/courseGroupInfoList', {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        filterCourseGroupName: Main.filterData.courseGroupName,
                        filterTeacherId: Main.filterData.teacherId,
                        filterLogical: Main.filterData.logical
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
                    {data: 'courseGroupName'},
                    {data: 'teacherId'},
                    {data: 'teacherRealName'},
                    {data: 'countStudent'},
                    {data: 'datetime'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return (data.state ? '已开启' : '已关闭');
                        }
                    },
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<div class="btn-group-sm"><button onclick="NS.viewCourseGroupInfo(' + meta.row + ')" type="button" class="btn bg-navy btn-sm"><i class="fa  fa-eye"></i></button> <button onclick="NS.updateCourseGroupInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteCourseGroupInfo(' + meta.row + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    }
                ]
            });
            $('#exportXlsxBtn').on('click', () => {
                $('.dt-buttons .buttons-excel').click();
            });
            $('#loadStudentIdsTxtBtn').on('click', () => {
                const file = $('#loadStudentIdsTxtIpt')[0].files[0];
                if (!window.FileReader) {
                    xtip.msg('您的浏览器不支持文件读取！请更换浏览器重试。', {icon: 'e'});
                    return;
                }
                if (NS.isNull(file)) {
                    xtip.msg('未选择任何文件！', {icon: 'e'});
                    return;
                }
                const loading = xtip.load('读取文件内容中...');
                let reader = new FileReader();
                reader.readAsText(file);
                reader.onloadend = (e) => {
                    if (e.target.readyState === FileReader.DONE) {
                        Main.addCourseGroupInfo.studentIds = e.target.result;
                        Main.loadStudentIdsTxtFormShow = false;
                        xtip.close(loading);
                    }
                }
            });
            $('#closeStudentIdsTxtBtn').on('click', () => {
                Main.loadStudentIdsTxtFormShow = false;
            });
            NS.showLoadStudentIdsTxt = () => {
                Main.loadStudentIdsTxtFormShow = !Main.loadStudentIdsTxtFormShow;
            };
            const addCourseGroupForm = $('#addCourseGroupForm');
            const updateCourseGroupForm = $('#updateCourseGroupForm');
            const updateCourseGroupModal = $('#updateCourseGroupModal');
            const dataFilterModal = $('#dataFilterModal');
            addCourseGroupForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    courseGroupName: {
                        validators: {
                            notEmpty: {
                                message: '课群名不能为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 64,
                                message: '课群名为6至64字符'
                            }
                        }
                    },
                    teacherId: {
                        validators: {
                            notEmpty: {
                                message: '负责管理课群的教师不能为空'
                            }
                        }
                    },
                    studentIds: {
                        validators: {
                            callback: {
                                message: '加入课群的学生信息填写不符合规定',
                                callback: function (value, validator) {
                                    let studentIds = new Set(value.split('\n'));
                                    if (studentIds.size === 0) {
                                        return false;
                                    }
                                    for (let val of studentIds.values()) {
                                        if (!Main.testStudentId(val)) {
                                            return false;
                                        }
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            });
            updateCourseGroupForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    courseGroupName: {
                        validators: {
                            notEmpty: {
                                message: '课群名不能为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 64,
                                message: '课群名为6至64字符'
                            }
                        }
                    },
                    teacherId: {
                        validators: {
                            notEmpty: {
                                message: '负责管理课群的教师不能为空'
                            }
                        }
                    }
                }
            });
            $('#addCourseGroupBtn').on('click', () => {
                const bootstrapValidator = addCourseGroupForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const adding = xtip.load('添加中...');
                    NS.post('/teachingSecretary/courseGroupManagement/addCourseGroup', {
                        courseGroupName: Main.addCourseGroupInfo.courseGroupName,
                        teacherId: Main.addCourseGroupInfo.teacherId,
                        description: Main.addCourseGroupInfo.description,
                        studentIds: Main.addCourseGroupInfo.studentIds
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('添加成功！', {icon: 's'});
                            datatable.ajax.reload();
                            Main.clearAddCourseGroupInfo();
                        } else {
                            xtip.msg('添加失败！请检查输入是否正确。', {icon: 'e'});
                        }
                        xtip.close(adding);
                    });
                }
            });
            $('#updateCourseGroupBtn').on('click', () => {
                const bootstrapValidator = updateCourseGroupForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const updating = xtip.load('更新中...');
                    NS.post('/teachingSecretary/courseGroupManagement/updateCourseGroup', Main.updateCourseInfo, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('更新成功！', {icon: 's'});
                            datatable.ajax.reload();
                            updateCourseGroupModal.modal('hide');
                        } else {
                            xtip.msg('更新失败！', {icon: 'e'});
                        }
                        xtip.close(updating);
                    });
                }
            });
            NS.viewCourseGroupInfo = (row) => {
                const courseGroup = datatable.row(row).data();
                NS.to("/teachingSecretary/courseGroupManagement?courseGroup=" + courseGroup.id)
            };
            NS.deleteCourseGroupInfo = (row) => {
                const data = datatable.row(row).data();
                xtip.confirm('确定要删除课群<b>' + data.courseGroupName + '</b>?', () => {
                    const deleting = xtip.load('删除中...');
                    NS.post('/teachingSecretary/courseGroupManagement/deleteOneCourseGroupInfo', {courseGroupId: data.id}, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('删除成功！', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('删除失败！', {icon: 'e'});
                        }
                        xtip.close(deleting);
                    });
                }, {icon: 'w'});
            };
            NS.updateCourseGroupInfo = (row) => {
                const courseGroup = datatable.row(row).data();
                Main.updateCourseInfo = {
                    courseGroupId: courseGroup.id,
                    courseGroupName: courseGroup.courseGroupName,
                    teacherId: courseGroup.teacherId,
                    description: courseGroup.description
                };
                updateCourseGroupModal.modal('show');
            };
            updateCourseGroupModal.on('hide.bs.modal', () => {
                Main.clearUpdateCourseGroupInfo();
            });
            dataFilterModal.on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
