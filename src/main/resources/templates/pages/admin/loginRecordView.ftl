<#-- 登录日志 -->
<#assign pageTitle>登录日志</#assign>
<#assign isSafetyManagement = true />
<#assign isLoginLog = true />
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
                登录日志
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
                <li><a href="#">安全管理</a></li>
                <li class="active">登录日志</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" id="main">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <div class="btn-group">
                                <button type="button" class="btn bg-orange" id="exportXlsxBtn">导出</button>
                                <button type="button" class="btn btn-success" data-toggle="modal"
                                        data-target="#dataFilterModal">过滤
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="loginRecordTable" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>登录IP</th>
                                    <th>登录时间</th>
                                    <th>省份</th>
                                    <th>省份代码</th>
                                    <th>城市</th>
                                    <th>城市代码</th>
                                    <th>详细地址</th>
                                    <th>设备信息</th>
                                    <#if isRoles("admin")>
                                        <th>操作</th>
                                    </#if>
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
                                        <label for="filterUserName">用户名</label>
                                        <input name="filterUserName" maxlength="48" type="text" class="form-control"
                                               id="filterUserName" v-model="filterUserName"
                                               placeholder="例如：gzmu-2017420">
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
    <script>
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    filterDataReloadFlag: false,
                    filterUserName: null,
                },
                methods: {},
                watch: {
                    'filterUserName': function (newV, oldV) {
                        this.filterDataReloadFlag = true;
                    }
                }
            });
            const datatable = $('#loginRecordTable').DataTable({
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
                pageLength: 98,
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'excelHtml5',
                        text: 'export-Vice button',
                        filename: '用户登录记录-${siteTitle}-' + NS.uuid(),
                        title: '用户登录记录-${siteTitle}',
                        className: 'hidden',
                        exportOptions: {
                            columns: [1, 2, 3, 4, 5, 6, 7, 8, 9]
                        }
                    }
                ],
                ajax: (data, callback, settings) => {
                    NS.post("/teachingSecretary/loginRecord/loginRecordList", {
                        draw: data.draw,
                        start: data.start,
                        length: data.length,
                        filterUserName: Main.filterUserName
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
                    {data: 'userName'},
                    {data: 'loginIp'},
                    {data: 'datetime'},
                    {data: 'province'},
                    {data: 'provinceCode'},
                    {data: 'city'},
                    {data: 'cityCode'},
                    {data: 'address'},
                    {data: 'agent'},
                    <#if isRoles("admin")>
                    {
                        data: null,
                        render: (data, type, row, meta) => {
                            return '<button type="button" class="btn btn-danger btn-sm" onclick="NS.deleteLoginRecord(' + meta.row + ')"><i class="fa fa-trash-o"></i></button></div>';
                        }
                    },
                    </#if>
                ]
            });
            $('#exportXlsxBtn').on('click', () => {
                $('.dt-buttons .buttons-excel').click();
            });

            $('#dataFilterModal').on('hide.bs.modal', () => {
                if (Main.filterDataReloadFlag) {
                    datatable.ajax.reload();
                    Main.filterDataReloadFlag = false;
                }
            });
            <#if isRoles("admin")>
            NS.deleteLoginRecord = (row) => {
                const data = datatable.row(row).data();
                xtip.confirm('确定删除用户<b>' + data.userName + '</b>在<b>' + data.datetime + '</b>的登录记录？(虽然系统不建议那么做。)', () => {
                    const deleting = xtip.load('删除中...');
                    NS.post('/teachingSecretary/loginRecord/deleteLoginRecord', {loginRecordId: data.id}, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('删除成功！', {icon: 's'});
                            datatable.ajax.reload();
                        } else {
                            xtip.msg('删除失败！', {icon: 'e'});
                        }
                        xtip.close(deleting);
                    });
                }, {icon: 'w'});
            }
            </#if>
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
