<#-- 课群详情管理页面 -->
<#assign activeIndex></#assign>
<#assign pageTitle>${courseGroup.name!""}|课群管理</#assign>
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
                课群详情
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
                <li><a href="#">课程管理</a></li>
                <li><a href="/teachingSecretary/courseGroupManagement">课群管理</a></li>
                <li class="active">课群详情</li>
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
                                        data-target="#addStudentToCourseGroupModal">添加学生
                                </button>
                                <button type="button" class="btn bg-orange">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="courseGroupStudentTable" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>班级</th>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>性别</th>
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
                <div class="modal fade" id="addStudentToCourseGroupModal" tabindex="-1" role="dialog"
                     aria-labelledby="addStudentToCourseGroupModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="addStudentToCourseGroupModalLabel">
                                    添加学生-{{courseGroup.name}}
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="addStudentToCourseGroupForm">
                                    <div class="form-group">
                                        <label for="addToCourseGroupStudentIds">加入学生的学号列表(<a
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
                                        <textarea name="addToCourseGroupStudentIds" id="addToCourseGroupStudentIds"
                                                  class="form-control"
                                                  v-model="addToCourseGroupStudentIds" rows="5"
                                                  placeholder="填写学生学号，每行一个！"
                                                  v-show="!loadStudentIdsTxtFormShow"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="addStudentToCourseGroupBtn" type="button" class="btn btn-primary">添加
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
                                        <label for="filterStudentId">按学号( <= 12个字符)</label>
                                        <input name="filterStudentId" maxlength="12" type="number" class="form-control"
                                               id="filterStudentId" v-model="filterData.studentId"
                                               placeholder="例如：2017">
                                    </div>
                                    <div class="form-group">
                                        <label for="filterStudentName">按姓名( <= 5个字符)</label>
                                        <input name="filterStudentName" maxlength="5" type="text" class="form-control"
                                               id="filterStudentName" v-model="filterData.studentName"
                                               placeholder="例如：张科">
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
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script>
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    loadStudentIdsTxtFormShow: false,
                    filterDataReloadFlag: false,
                    addToCourseGroupStudentIds: null,
                    courseGroup: {
                        id: ${courseGroup.id},
                        name: '${courseGroup.name}',
                    },
                    filterData: {
                        studentId: null,
                        studentName: null,
                        logical: 0
                    }
                },
                methods: {
                    testStudentId: function (studentId) {
                        let regexp = new RegExp('^[1-9][0-9]{11}$');
                        return regexp.test(studentId);
                    }
                },
                watch: {
                    'filterData.studentId': function (newV, oldV) {
                        this.filterDataReloadFlag = true;
                    },
                    'filterData.studentName': function (newV, oldV) {
                        this.filterDataReloadFlag = true;
                    },
                    'filterData.logical': function (newV, oldV) {
                        this.filterDataReloadFlag = true;
                    }
                }
            });
            const datatable = $('#courseGroupStudentTable').DataTable({
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
                pageLength: 50,
                ajax: (data, callback, settings) => {
                    NS.post('/teachingSecretary/studentManagement/courseGroupStudentList', {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        courseGroupId: Main.courseGroup.id,
                        filterStudentId: Main.filterData.studentId,
                        filterStudentName: Main.filterData.studentName,
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
                    {data: 'collegeName'},
                    {data: 'majorName'},
                    {data: 'className'},
                    {data: 'studentId'},
                    {data: 'realName'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return (data.sex === 'M' ? '男' : '女');
                        }
                    },
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<button type="button" class="btn btn-danger btn-sm" onclick="NS.removeStudent(' + meta.row + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    }
                ]
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
                        Main.addToCourseGroupStudentIds = e.target.result;
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
            const addStudentToCourseGroupForm = $('#addStudentToCourseGroupForm');
            const dataFilterModal = $('#dataFilterModal');
            addStudentToCourseGroupForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    addToCourseGroupStudentIds: {
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
            $('#addStudentToCourseGroupBtn').on('click', () => {
                const bootstrapValidator = addStudentToCourseGroupForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const adding = xtip.load('添加中...');
                    NS.post('/teachingSecretary/courseGroupManagement/addStudentToCourseGroup', {
                        courseGroupId: Main.courseGroup.id,
                        studentIds: Main.addToCourseGroupStudentIds
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('添加成功！', {icon: 's'});
                            datatable.ajax.reload();
                            Main.addToCourseGroupStudentIds = null;
                        } else {
                            xtip.msg('添加失败！请检查输入的学号(s)是否正确。', {icon: 'e'});
                        }
                        xtip.close(adding);
                    });
                }
            });
            dataFilterModal.on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
            });
            NS.removeStudent = (row) => {
                const data = datatable.row(row).data();
                xtip.confirm('确定要将<b>' + data.realName + '(' + data.studentId + ')</b>从课群<b>' + Main.courseGroup.name + '</b>中移除？', () => {
                    const removing = xtip.load('移除中...');
                    NS.post('/teachingSecretary/courseGroupManagement/removeStudentForCourseGroup', {
                        courseGroupId: Main.courseGroup.id,
                        studentId: data.studentId
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('移除成功！', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('移除失败！');
                        }
                        xtip.close(removing);
                    });
                }, {icon: 'w'})
            }
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
