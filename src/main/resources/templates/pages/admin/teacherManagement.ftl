<#-- author: _Struggler -->
<#-- author: nsleaf www.nsleaf.cn -->
<#-- 教师管理界面 -->
<#assign  pageTitle>教师管理</#assign>
<#assign isUserManagement = true />
<#assign isTeacherManagement = true />
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
                教师管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i> 控制台</a></li>
                <li><a href="#">用户管理</a></li>
                <li class="active">教师管理</li>
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
                                        data-target="#saveTeacherModal">添加
                                </button>
                                <button type="button" class="btn bg-orange" id="exportXlsxBtn">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="teacherTable" class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>教职工号</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>管理课群数</th>
                                    <th>邮箱</th>
                                    <th>手机号</th>
                                    <th>教学秘书</th>
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
                <!-- Modal -->
                <div class="modal fade" id="saveTeacherModal" tabindex="-1" role="dialog"
                     aria-labelledby="saveTeacherModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="saveTeacherModalLabel"
                                    v-if="saveTeacherInfo.userId !== null">
                                    更新教师信息
                                </h4>
                                <h4 class="modal-title" id="saveTeacherModalLabel" v-else>
                                    添加教师
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form id="saveTeacherInfoForm">
                                    <div class="form-group">
                                        <label for="teacherId">教职工号</label>
                                        <input name="teacherId" type="number" class="form-control" id="teacherId"
                                               v-model="saveTeacherInfo.teacherId" placeholder="请输入教职工号"
                                               :disabled="saveTeacherInfo.userId !== null">
                                    </div>
                                    <div class="form-group">
                                        <label for="realName">姓名</label>
                                        <input name="realName" type="text" class="form-control" id="realName"
                                               v-model="saveTeacherInfo.realName" placeholder="请输入姓名">
                                    </div>
                                    <div class="form-group">
                                        <label for="sexRadios">性别</label>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" v-model="saveTeacherInfo.sex" name="sex"
                                                       id="sexRadios" value="M">男
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" v-model="saveTeacherInfo.sex" name="sex"
                                                       id="sexRadios" value="F">女
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="phone">手机号</label>
                                        <input name="phone" type="text" class="form-control" id="phone"
                                               v-model="saveTeacherInfo.phone" placeholder="手机号(非必填)">
                                    </div>
                                    <div class="form-group">
                                        <label for="email">电子邮箱</label>
                                        <input name="email" type="email" class="form-control" id="email"
                                               v-model="saveTeacherInfo.email" placeholder="邮箱地址(非必填)">
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button id="saveTeacherInfoBtn" type="button" class="btn btn-primary"
                                        v-if="saveTeacherInfo.userId !== null">更新
                                </button>
                                <button id="saveTeacherInfoBtn" type="button" class="btn btn-primary" v-else>添加
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
                                        <label for="filterTeacherName">按教师姓名( <= 5个字符)</label>
                                        <input name="filterTeacherName" maxlength="5" type="text" class="form-control"
                                               id="filterTeacherName" v-model="filterTeacherName" placeholder="例如：张科">
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
                    filterTeacherName: null,
                    filterDataReloadFlag: false,/* 过滤条件下，数据重新加载标志。避免过度请求给服务器带来压力。 */
                    saveTeacherInfo: {
                        userId: null,
                        teacherId: null,
                        realName: null,
                        sex: 'M',
                        email: null,
                        phone: null
                    },
                },
                methods: {
                    clearSaveTeacherInfo: function () {
                        this.saveTeacherInfo.userId = null;
                        this.saveTeacherInfo.teacherId = null;
                        this.saveTeacherInfo.realName = null;
                        this.saveTeacherInfo.sex = 'M';
                        this.saveTeacherInfo.email = null;
                        this.saveTeacherInfo.phone = null;
                    }
                },
                watch: {
                    filterTeacherName: function (newV, oldV) {
                        this.filterDataReloadFlag = true;/* 允许重新加载数据 */
                    }
                }
            });
            const datatable = $('#teacherTable').DataTable({
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
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'excelHtml5',
                        text: 'export-Vice button',
                        filename: '教师信息-${siteTitle}-' + NS.uuid(),
                        title: '教师信息-${siteTitle}',
                        className: 'hidden',
                        exportOptions: {
                            columns: [1, 2, 3, 4, 5, 6, 7, 8]
                        }
                    }
                ],
                ajax: (data, callback, settings) => {
                    NS.post("/teachingSecretary/teacherManagement/teacherList", {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        findTeacherName: Main.filterTeacherName
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
                    {data: 'username'},
                    {data: 'teacherId'},
                    {data: 'realName'},
                    {data: 'sex'},
                    {data: 'countCourseGroup'},
                    {data: 'email'},
                    {data: 'phone'},
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return (data.isTeachingSecretary ? '是' : '否');
                        }
                    },
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<#if isRoles("admin")><div class="btn-group-sm"><button onclick="NS.changeTeacherRole(' + meta.row + ')" type="button" class="btn bg-navy btn-sm"><i class="fa fa-magic"></i></button></#if> <button onclick="NS.updateTeacherInfo(' + meta.row + ')" type="button" class="btn btn-warning btn-sm"><i class="fa fa-pencil-square-o"></i></button> <button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteTeacher(' + meta.row + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    },
                ]
            });
            $('#exportXlsxBtn').on('click', () => {
                $('.dt-buttons .buttons-excel').click();
            });

            const dataFilterModal = $('#dataFilterModal');
            const saveTeacherInfoForm = $('#saveTeacherInfoForm');
            const saveTeacherModal = $('#saveTeacherModal');
            saveTeacherInfoForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    teacherId: {
                        validators: {
                            notEmpty: {
                                message: '教职工号不能为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 24,
                                message: '教职工号长度保持在6-24个字符'
                            }
                        }
                    },
                    realName: {
                        validators: {
                            notEmpty: {
                                message: '教师姓名不能为空'
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
                                message: '请确定教师的性别'
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
                                regexp: /^[0-9a-zA-Z][\w.]{1,30}@[a-zA-Z]\w{1,50}\.((cn)|(com)|(org))$/,
                                message: '邮箱地址格式错误'
                            }
                        }
                    },
                }
            });
            $('#saveTeacherInfoBtn').on('click', () => {
                const bootstrapValidator = saveTeacherInfoForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const saveLoad = xtip.load(NS.isNull(Main.saveTeacherInfo.userId) ? '添加中...' : '更新中');
                    NS.post('/teachingSecretary/teacherManagement/saveTeacher', Main.saveTeacherInfo, (res) => {
                        if (res.code === 1000) {
                            xtip.msg(NS.isNull(Main.saveTeacherInfo.userId) ? '添加成功' : '更新成功', {icon: 's'});
                            datatable.ajax.reload();
                            if (NS.isNull(Main.saveTeacherInfo.userId)) {
                                Main.clearSaveTeacherInfo();
                            } else {
                                saveTeacherModal.modal('hide');
                            }
                        } else {
                            xtip.msg(NS.isNull(Main.saveTeacherInfo.userId) ? '添加失败' : '更新失败', {icon: 'e'});
                        }
                        xtip.close(saveLoad);
                    });
                }
            });
            dataFilterModal.on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {/* 是否需要重新加载数据 */
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
            });
            saveTeacherModal.on('hide.bs.modal', () => {
                Main.clearSaveTeacherInfo();
            });
            NS.updateTeacherInfo = (row) => {
                const data = datatable.row(row).data();
                Main.saveTeacherInfo.userId = data.id;
                Main.saveTeacherInfo.teacherId = data.teacherId;
                Main.saveTeacherInfo.realName = data.realName;
                Main.saveTeacherInfo.sex = data.sex === '男' ? 'M' : 'F';
                Main.saveTeacherInfo.phone = data.phone;
                Main.saveTeacherInfo.email = data.email;
                saveTeacherModal.modal('show');
            };
            NS.deleteTeacher = (row) => {
                const data = datatable.row(row).data();
                xtip.confirm('你正在进行一个极度危险的操作！<br/>一旦执行，所有与该名教师相关的数据均会删除，是否确定删除？<br/><b>教职工号：</b>'
                    + data.teacherId + '<br/><b>姓名：</b>' + data.realName, () => {
                    const deleting = xtip.load('删除中...');
                    NS.post('/teachingSecretary/teacherManagement/deleteTeacher', {userId: data.id}, (res) => {
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
            <#if isRoles("admin")>
            NS.changeTeacherRole = (row) => {
                const data = datatable.row(row).data();
                xtip.confirm((data.isTeachingSecretary ? '撤销' : '授予') + '教职工 ' + data.teacherId + ' 教学秘书权限？', () => {
                    const ing = xtip.load('操作中...');
                    NS.post('/teachingSecretary/teacherManagement/changeTeachingSecretaryRole', {userId: data.id}, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('操作成功！', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('操作失败！', {icon: 'e'});
                        }
                        xtip.close(ing);
                    });
                }, {icon: 'w'});
            }
            </#if>
        });
    </script>
</#assign>
<#include "../../common/footer.ftl">