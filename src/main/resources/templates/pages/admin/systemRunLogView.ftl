<#-- 系统运行日志 -->
<#assign pageTitle>系统运行日志</#assign>
<#assign isSystemRelated = true />
<#assign isRunLog = true />
<#assign restHead>
    <style>
        pre {
            border: none;
        }

        pre.prettyprint {
            background: #001F3F;
            color: #ff0080;
            font-family: Consolas, Menlo, 'Bitstream Vera Sans Mono', 'DejaVu Sans Mono', Monaco, monospace;
            font-size: 15px;
            line-height: 1.2;
            border: 1px solid #dedede !important;
            padding: 10px;
            min-height: 520px;
            max-height: 520px;
            width: auto;
            overflow: auto !important;
        }

        pre.prettyprint > code {
            width: auto;
            overflow: auto !important;
        }
    </style>
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
                运行日志
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
                <li><a href="#">系统相关</a></li>
                <li class="active">运行日志</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" id="main">
            <div class="row">
                <div class="col-md-3">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">日志文件列表</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="list-group">
                                <button type="button" v-for="(log, index) in logList" :key="index"
                                        :class="logListItemClass(index)" v-on:click="switchLogFile(index)">
                                    <span class="badge">{{log.size}}</span>{{log.fileName}}
                                </button>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button class="btn btn-default btn-sm pull-left" :disabled="preBtn"
                                    v-on:click="clickPreBtn()">上一页
                            </button>
                            <button class="btn btn-default btn-sm pull-right" :disabled="nextBtn"
                                    v-on:click="clickNextBtn()">下一页
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="box box-danger">
                        <!-- form start -->
                        <div class="box-body">
                            <pre class="prettyprint">
                                {{logFileContent}}
                            </pre>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../../common/admin/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <script>
        $(function () {
            new Vue({
                el: '#main',
                data: {
                    logFileList: [],
                    logFileListLength: 0,
                    currentLogIndex: -1,
                    logFileContent: '',
                    logList: [],
                    page: -1,
                    showLength: 10,
                    preBtn: true,
                    nextBtn: true
                },
                methods: {
                    loadLogFileList: function () {
                        NS.post('/admin/systemRunLog/allLogFileList', null, (res) => {
                            if (res.code === 1000) {
                                this.logFileList = res.data.list.reverse();
                                this.logFileListLength = res.data.length;
                                if (this.logFileListLength > 0) {
                                    this.page = 0;
                                }
                            } else {
                                xtip.msg('加载日志列表失败！', {icon: 'e'});
                            }
                        });
                    },
                    logListItemClass: function (index) {
                        if (index !== this.currentLogIndex) {
                            return "list-group-item";
                        } else {
                            return "list-group-item active";
                        }
                    },
                    switchLogFile: function (index) {
                        if (this.currentLogIndex !== index) {
                            this.currentLogIndex = index;
                        }
                    },
                    clickPreBtn: function () {
                        if (this.page > 0) {
                            --this.page;
                        }
                    },
                    clickNextBtn: function () {
                        if (this.logFileListLength - this.page * this.showLength - 1 > this.showLength) {
                            ++this.page;
                        }
                    }
                },
                watch: {
                    currentLogIndex: function (newV, oldV) {
                        NS.post('/admin/systemRunLog/getLogFileContent', {fileName: this.logFileList[newV].fileName}, (res) => {
                            if (res.code === 1000) {
                                this.logFileContent = res.data.content;
                            } else {
                                xtip.msg('系统错误！', {icon: 'e'});
                            }
                        });
                    },
                    page: function (newV, oldV) {
                        if (this.page < 0) {
                            return;
                        }
                        const startIndex = newV * this.showLength;
                        const endIndex = startIndex + Math.min((this.logFileListLength - startIndex), this.showLength);
                        this.nextBtn = (endIndex - startIndex + 1 <= this.showLength);
                        this.preBtn = (this.page <= 0);
                        this.logList = this.logFileList.slice(startIndex, endIndex);
                        this.currentLogIndex = startIndex;
                    }
                },
                created: function () {
                    this.loadLogFileList();//加载列表
                }
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
