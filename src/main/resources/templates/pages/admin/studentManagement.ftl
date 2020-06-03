<#-- author: _Struggler -->
<#-- 学生管理界面 -->
<#assign  pageTitle>学生管理</#assign>
<#assign isUserManagement = true />
<#assign isStudentManagement = true />
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
</#assign>
<#include "../../common/admin/head.ftl"/>
<div class="wrapper">
    <#include "../../common/admin/header.ftl"/>
    <#include "../../common/admin/sidebar.ftl"/>
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                学生管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i> 控制台</a></li>
                <li><a href="#">用户管理</a></li>
                <li class="active">学生管理</li>
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
                                        data-target="#saveStudentModal">添加
                                </button>
                                <button type="button" class="btn bg-orange">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="studentTable" class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>学号</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>班级</th>
                                    <th>加入的课群数</th>
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
                </div>
                <!-- /.col -->
                <div class="modal fade" id="saveStudentModal" tabindex="-1" role="dialog"
                     aria-labelledby="saveStudentModal">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="saveStudentModalLabel"
                                    v-if="saveStudentInfo.userId !== null">
                                    更新学生信息
                                </h4>
                                <h4 class="modal-title" id="saveStudentModalLabel" v-else>
                                    添加学生
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="saveStudentFrom">
                                    <div class="form-group">
                                        <label for="studentId">学号</label>
                                        <input name="studentId" type="text" class="form-control" id="studentId"
                                               v-model="saveStudentInfo.studentId" placeholder="请输入学号">
                                    </div>
                                    <div class="form-group">
                                        <label for="realName">姓名</label>
                                        <input name="realName" type="text" class="form-control" id="realName"
                                               v-model="saveStudentInfo.realName" placeholder="请输入姓名">
                                    </div>
                                    <div class="form-group">
                                        <label for="sexRadios">性别</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" v-model="saveStudentInfo.sex" name="sex"
                                                       id="sexRadios" value="M">男
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" v-model="saveStudentInfo.sex" name="sex"
                                                       id="sexRadios" value="F">女
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="collegeList">学院</label>
                                        <select id="collegeList" class="form-control" name="collegeId"
                                                v-model="saveStudentInfo.collegeId">
                                            <option v-for="college in dataList.collegeList" :key="college.id"
                                                    :value="college.id">{{college.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="majorList">专业</label>
                                        <select id="majorList" class="form-control" name="majorId"
                                                v-model="saveStudentInfo.majorId">
                                            <option v-for="major in dataList.majorList" :key="major.id"
                                                    :value="major.id">{{major.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="classList">班级</label>
                                        <select id="classList" class="form-control" name="classId"
                                                v-model="saveStudentInfo.classId">
                                            <option v-for="clazz in dataList.classList" :key="clazz.id"
                                                    :value="clazz.id">{{clazz.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="phone">手机号</label>
                                        <input name="phone" type="text" class="form-control" id="phone"
                                               v-model="saveStudentInfo.phone" placeholder="手机号(非必填)">
                                    </div>
                                    <div class="form-group">
                                        <label for="email">电子邮箱</label>
                                        <input name="email" type="email" class="form-control" id="email"
                                               v-model="saveStudentInfo.email" placeholder="邮箱地址(非必填)">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="saveStudentBtn" type="button" class="btn btn-primary"
                                        v-if="saveStudentInfo.userId !== null">更新
                                </button>
                                <button id="saveStudentBtn" type="button" class="btn btn-primary" v-else>添加
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
                                        <label for="collegeList">选择学院</label>
                                        <select id="collegeList" class="form-control" v-model="dataFilter.collegeId">
                                            <option value="-1">所有学院</option>
                                            <option v-for="college in dataList.collegeList" :key="college.id"
                                                    :value="college.id">{{college.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group" v-if="dataFilter.collegeId != -1">
                                        <label for="majorList">选择专业</label>
                                        <select id="majorList" class="form-control" v-model="dataFilter.majorId">
                                            <option value="-1">所有专业</option>
                                            <option v-for="major in dataList.majorList" :key="major.id"
                                                    :value="major.id">{{major.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group" v-if="dataFilter.majorId != -1">
                                        <label for="classList">选择班级</label>
                                        <select id="classList" class="form-control" v-model="dataFilter.classId">
                                            <option value="-1">所有班级</option>
                                            <option v-for="clazz in dataList.classList" :key="clazz.id"
                                                    :value="clazz.id">{{clazz.name}}
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
    </div>
    <!-- /.content -->
    <#include "../../common/admin/copyright.ftl">
</div>
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
                    filterDataReloadFlag: false,
                    firstUpdateStudentOpenModalFlag: false,
                    saveStudentInfo: {
                        userId: null,
                        studentId: null,
                        realName: null,
                        sex: 'M',
                        email: null,
                        phone: null,
                        collegeId: -1,
                        majorId: -1,
                        classId: -1
                    },
                    dataList: {
                        collegeList: null,
                        majorList: null,
                        classList: null
                    },
                    dataFilter: {
                        collegeId: -1,
                        majorId: -1,
                        classId: -1
                    }
                },
                methods: {
                    clearSaveStudentInfo: function () {
                        this.saveStudentInfo = {
                            userId: null,
                            studentId: null,
                            realName: null,
                            sex: 'M',
                            email: null,
                            phone: null,
                            collegeId: -1,
                            majorId: -1,
                            classId: -1
                        };
                    },
                    loadCollegeList: function () {
                        NS.post('/teachingSecretary/collegeManagement/collegeInfoAllList', null, (res) => {
                            res.code === 1000 && (this.dataList.collegeList = res.data.colleges);
                        });
                    },
                    loadMajorList: function (collegeId) {
                        NS.post('/teachingSecretary/majorManagement/majorInfoListByCollege', {collegeId: collegeId}, (res) => {
                            res.code === 1000 && (this.dataList.majorList = res.data.majors);
                        });
                    },
                    loadClassList: function (majorId) {
                        NS.post('/teachingSecretary/classManagement/allClassListByMajor', {majorId: majorId}, (res) => {
                            res.code === 1000 && (this.dataList.classList = res.data.clazzs);
                        });
                    }
                },
                watch: {
                    'dataFilter.collegeId': function (newV, oldV) {
                        if (parseInt(newV) !== -1) {
                            this.loadMajorList(newV);
                        }
                        this.dataFilter.majorId = -1;
                        this.filterDataReloadFlag = true;
                    },
                    'dataFilter.majorId': function (newV, oldV) {
                        if (parseInt(newV) !== -1) {
                            this.loadClassList(newV);
                        }
                        this.dataFilter.classId = -1;
                        this.filterDataReloadFlag = true;
                    },
                    'dataFilter.classId': function (newV, oldV) {
                        this.filterDataReloadFlag = true;
                    },
                    'saveStudentInfo.collegeId': function (newV, oldV) {
                        if (parseInt(newV) !== -1) {
                            this.loadMajorList(newV);
                        }
                        this.firstUpdateStudentOpenModalFlag || (this.saveStudentInfo.majorId = -1);
                    },
                    'saveStudentInfo.majorId': function (newV, oldV) {
                        if (parseInt(newV) !== -1) {
                            this.loadClassList(newV);
                        }
                        if (this.firstUpdateStudentOpenModalFlag) {
                            this.firstOpenUpdateClassModalFlag = false;
                        } else {
                            this.saveStudentInfo.classId = -1;
                        }
                    }
                }
            });
            const datatable = $('#studentTable').DataTable({
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
                    NS.post("/teachingSecretary/studentManagement/studentList", {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        collegeId: Main.dataFilter.collegeId,
                        majorId: Main.dataFilter.majorId,
                        classId: Main.dataFilter.classId
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
                    {data: 'name'},
                    {data: 'studentId'},
                    {data: 'realName'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return data.sex === 'M' ? '男' : '女';
                        }
                    },
                    {data: 'collegeName'},
                    {data: 'majorName'},
                    {data: 'className'},
                    {data: 'countJoinCourseGroup'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<button onclick="NS.updateStudentInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteStudent(' + meta.row + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    },
                ]
            });
            const dataFilterModal = $('#dataFilterModal');
            const saveStudentModal = $('#saveStudentModal');
            const saveStudentFrom = $('#saveStudentFrom');

            function loadSaveStudentFormValidator() {
                saveStudentFrom.bootstrapValidator({
                    verbose: false,     /* 对field内的条件按顺序验证 */
                    message: '数据校验失败',
                    fields: {
                        studentId: {
                            validators: {
                                notEmpty: {
                                    message: '学号不能为空'
                                },
                                stringLength: {
                                    min: 12,
                                    max: 12,
                                    message: '学号保持在12位'
                                }
                            }
                        },
                        realName: {
                            validators: {
                                notEmpty: {
                                    message: '姓名不能为空'
                                },
                                stringLength: {
                                    min: 2,
                                    max: 5,
                                    message: '姓名长度规定在2-5个字符'
                                }
                            }
                        },
                        sex: {
                            validators: {
                                notEmpty: {
                                    message: '请确定学生的性别'
                                }
                            }
                        },
                        collegeId: {
                            validators: {
                                callback: {
                                    message: '请选择学院',
                                    callback: function (value, validator) {
                                        return (parseInt(value) > 0);
                                    }
                                }
                            }
                        },
                        majorId: {
                            validators: {
                                callback: {
                                    message: '请选择专业',
                                    callback: function (value, validator) {
                                        return (parseInt(value) > 0);
                                    }
                                }
                            }
                        },
                        classId: {
                            validators: {
                                callback: {
                                    message: '请选择班级',
                                    callback: function (value, validator) {
                                        return (parseInt(value) > 0);
                                    }
                                }
                            }
                        },
                        phone: {
                            validators: {
                                regexp: {
                                    regexp: /^((1[358][0-9])|(14[57])|(17[0678]))\d{8}$/,
                                    message: '手机号格式错误'
                                }
                            }
                        },
                        email: {
                            validators: {
                                regexp: {
                                    regexp: /^[a-zA-Z][\w.]{1,30}@[a-zA-Z]\w{1,50}\.((cn)|(com)|(org))$/,
                                    message: '邮箱地址格式错误'
                                }
                            }
                        },
                    }
                });
            }

            function reloadSaveStudentFromValidator() {
                try {
                    saveStudentFrom.data('bootstrapValidator').destroy();
                    saveStudentFrom.data('bootstrapValidator', null);
                } catch (e) {
                }
                loadSaveStudentFormValidator();
            }

            $('#saveStudentBtn').on('click', () => {
                reloadSaveStudentFromValidator();/* 刷新数据校验器 */
                const bootstrapValidator = saveStudentFrom.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const saveLoad = xtip.load(NS.isNull(Main.saveStudentInfo.userId) ? '添加中...' : '更新中');
                    NS.post('/teachingSecretary/studentManagement/saveStudent', Main.saveStudentInfo, (res) => {
                        if (res.code === 1000) {
                            xtip.msg(NS.isNull(Main.saveStudentInfo.userId) ? '添加成功' : '更新成功', {icon: 's'});
                            datatable.ajax.reload();
                            if (NS.isNull(Main.saveStudentInfo.userId)) {
                                Main.clearSaveStudentInfo();
                            } else {
                                saveStudentModal.modal('hide');
                            }
                        } else {
                            xtip.msg(NS.isNull(Main.saveStudentInfo.userId) ? '添加失败' : '更新失败', {icon: 'e'});
                        }
                        xtip.close(saveLoad);
                    });
                }
            });
            dataFilterModal.on('show.bs.modal', () => {
                Main.loadCollegeList();
            });
            dataFilterModal.on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
            });
            saveStudentModal.on('show.bs.modal', () => {
                Main.loadCollegeList();
                reloadSaveStudentFromValidator();
            });
            saveStudentModal.on('hide.bs.modal', () => {
                Main.clearSaveStudentInfo();
            });
            NS.updateStudentInfo = (row) => {
                const student = datatable.row(row).data();
                Main.saveStudentInfo = {
                    userId: student.id,
                    studentId: student.studentId,
                    realName: student.realName,
                    sex: student.sex,
                    email: student.email,
                    phone: student.phone,
                    collegeId: student.collegeId,
                    majorId: student.majorId,
                    classId: student.classId
                };
                Main.loadMajorList(student.collegeId);
                Main.loadClassList(student.majorId);
                Main.firstUpdateStudentOpenModalFlag = true;
                saveStudentModal.modal('show');
            };
            NS.deleteStudent = (row) => {
                const student = datatable.row(row).data();
                xtip.confirm('你正在进行一个极度危险的操作！<br/>一旦执行，所有与该名学生相关的数据均会删除，是否确定删除？<br/><b>学号：</b>'
                    + student.studentId + '<br/><b>姓名：</b>' + student.realName, () => {
                    const deleting = xtip.load('删除中...');
                    NS.post('/teachingSecretary/studentManagement/deleteStudent', {userId: student.id}, (res) => {
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
        });
    </script>
</#assign>
<#include "../../common/footer.ftl">