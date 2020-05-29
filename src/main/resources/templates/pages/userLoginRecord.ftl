<#-- 用户登录日志 -->
<#assign activeIndex></#assign>
<#assign pageTitle>登录日志</#assign>
<#assign safetyManagement = true />
<#assign loginLog = true />
<#include "../common/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "../common/header.ftl" />
    <#include "../common/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                登录记录
            </h1>
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="#">安全管理</a></li>
                <li class="active">登录记录</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content" id="main">
            <!-- row -->
            <div class="row">
                <div class="col-md-12">
                    <!-- The time line -->
                    <ul class="timeline">
                        <!-- timeline time label -->
                        <li class="time-label">
                  <span class="bg-green">
                      {{currentDateTime}}
                  </span>
                        </li>
                        <!-- /.timeline-label -->
                        <!-- timeline item -->
                        <li v-for="loginRecord in loginRecordList">
                            <i :class="randomIcon(loginRecord.agent)"></i>
                            <#--     <i class="fa fa-desktop bg-blue" v-else></i>-->
                            <div class="timeline-item">
                                <span class="time"><i class="fa fa-clock-o"></i> {{loginRecord.datetime}}</span>
                                <div class="timeline-body">
                                    <p><b>登录IP:</b>{{loginRecord.loginIp}}</p>
                                    <p><b>登录地址:</b>{{loginRecord.address}}</p>
                                    <p><b>设备信息:</b>{{loginRecord.agent}}</p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <i class="fa fa-clock-o bg-gray"></i>
                        </li>
                    </ul>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <script>
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    page: 0,
                    size: 10,
                    remainder: 0,//剩余数量
                    loginRecordList: null,
                    currentDateTime: null,
                    loadLock: false,
                    colorBgArray: ['blue', 'purple', 'red', 'yellow', 'orange', 'green', 'fuchsia', 'black', 'maroon', 'olive']
                },
                methods: {
                    loadLoginRecordList: function () {
                        NS.post('/common/loginRecord/loginInfoList', {page: this.page, size: this.size}, (res) => {
                            if (res.code === 1000) {
                                if (res.data.size === 0) {
                                    xtip.msg('没有数据了...');
                                } else {
                                    ++this.page;
                                    if (NS.isNull(this.loginRecordList)) {
                                        this.loginRecordList = res.data.list;
                                    } else {
                                        this.loginRecordList = this.loginRecordList.concat(res.data.list);
                                    }
                                }
                            } else {
                                xtip.msg('获取数据失败！!');
                            }
                            this.loadLock = true;
                        })
                    },
                    randomIcon: function (agentStr) {
                        const regexp = new RegExp('^.*iPhone.*$');
                        let classStr = regexp.test(agentStr) ? 'fa fa-tablet bg-' : 'fa fa-desktop bg-';
                        return (classStr + this.colorBgArray[Math.floor(Math.random() * this.colorBgArray.length)]);
                    },
                    updateCurrentDateTime: function () {
                        this.currentDateTime = new Date();
                    }
                },
                created: function () {
                    this.loadLoginRecordList();
                    this.updateCurrentDateTime();
                }
            });
            window.setInterval(() => {
                Main.updateCurrentDateTime();
            }, 1000);
            window.addEventListener('scroll', () => {/* 监听滚动事件 */
                let scrollTop = $(window).scrollTop();
                let scrollHeight = $(document).height();
                let windowHeight = $(window).height();
                if (scrollTop + windowHeight + 5 >= scrollHeight && Main.loadLock) {
                    Main.loadLock = false;
                    Main.loadLoginRecordList();
                }
            });
        })
    </script>
</#assign>
<#include "../common/footer.ftl" />
