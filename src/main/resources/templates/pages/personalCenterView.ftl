<#-- 个人中心 -->
<#assign activePersonalCenter = true />
<#assign pageTitle>个人中心</#assign>
<#assign restHead>
    <link href="/static/plugins/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl" />
    <!-- Full Width Column -->
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    个人中心
                    <#if listenToMyGoodWords??>
                        <small>${listenToMyGoodWords}</small>
                    </#if>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li class="active">个人中心</li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content" id="main">


                <div class="row">
                    <div class="col-md-3">

                        <!-- Profile Image -->
                        <div class="box box-danger">
                            <div class="box-body box-profile">
                                <img class="profile-user-img img-responsive img-circle"
                                     src="<#if u.avatar??>${u.avatar}<#elseif u.sex == 'M'>${m_avatar}<#else>${f_avatar}</#if>"
                                     alt="User profile picture">

                                <h3 class="profile-username text-center">${u.realName!"获取姓名失败"}</h3>

                                <p class="text-muted text-center">${u.name}</p>

                                <ul class="list-group list-group-unbordered">
                                    <li class="list-group-item">
                                        <#if isRoles("student")>
                                            <b>学&nbsp;&nbsp;&nbsp;&nbsp;号：</b>${userInfo.student.studentId!""}
                                        <#else>
                                            <b>职工号：</b>${userInfo.teacher.teacherId!""}
                                        </#if>
                                    </li>
                                    <li class="list-group-item">
                                        <b>身&nbsp;&nbsp;&nbsp;&nbsp;份：</b><#if isRoles("admin")>超级管理员<#elseif isRoles("teachingSecretary")>教学秘书<#elseif isRoles("teacher")>教师<#else>学生</#if>
                                    </li>
                                    <#if isRoles("student")>
                                        <li class="list-group-item">
                                            <b>学&nbsp;&nbsp;&nbsp;&nbsp;院：</b>${userInfo.student.college.name}
                                        </li>
                                        <li class="list-group-item">
                                            <b>专&nbsp;&nbsp;&nbsp;&nbsp;业：</b>${userInfo.student.major.name}
                                        </li>
                                        <li class="list-group-item">
                                            <b>班&nbsp;&nbsp;&nbsp;&nbsp;级：</b>${userInfo.student.clazz.name}
                                        </li>
                                    </#if>
                                    <li class="list-group-item">
                                        <b>性&nbsp;&nbsp;&nbsp;&nbsp;别：</b><#if userInfo.sex == 'M'>男<#else>女</#if>
                                    </li>
                                </ul>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->
                    <div class="col-md-9">
                        <div class="nav-tabs-custom">
                            <ul class="nav nav-tabs">
                                <li class="active" id="loginRecordTabLi">
                                    <a href="#loginRecordTab" data-toggle="tab">登录记录</a></li>
                                <li><a href="#themeCenter" data-toggle="tab">主题中心</a></li>
                                <li><a href="#uploadAvatar" data-toggle="tab">上传头像</a></li>
                                <li><a href="#changePassword" data-toggle="tab">修改密码</a></li>
                                <li><a href="#settings" data-toggle="tab">设置</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="active tab-pane" id="loginRecordTab">
                                    <!-- The timeline -->
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
                                <!-- /.tab-pane -->
                                <div class="tab-pane" id="themeCenter">
                                    <ul class="list-unstyled clearfix">
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('blue')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'blue'>full-opacity-hover</#if>">
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 7px; background: #367fa9;"></span><span
                                                            class="bg-light-blue"
                                                            style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin">Blue</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('black')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'black'>full-opacity-hover</#if>">
                                                <div style="box-shadow: 0 0 2px rgba(0,0,0,0.1)" class="clearfix"><span
                                                            style="display:block; width: 20%; float: left; height: 7px; background: #fefefe;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 7px; background: #fefefe;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #222;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin">Black</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('purple')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'purple'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-purple-active"></span><span class="bg-purple"
                                                                                                 style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin">Purple</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('green')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'green'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-green-active"></span><span class="bg-green"
                                                                                                style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin">Green</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('red')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'red'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-red-active"></span><span class="bg-red"
                                                                                              style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin">Red</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('yellow')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'yellow'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-yellow-active"></span><span class="bg-yellow"
                                                                                                 style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin">Yellow</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('blue-light')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'blue-light'>full-opacity-hover</#if>">
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 7px; background: #367fa9;"></span><span
                                                            class="bg-light-blue"
                                                            style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin" style="font-size: 12px">Blue Light</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('black-light')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'black-light'>full-opacity-hover</#if>">
                                                <div style="box-shadow: 0 0 2px rgba(0,0,0,0.1)" class="clearfix"><span
                                                            style="display:block; width: 20%; float: left; height: 7px; background: #fefefe;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 7px; background: #fefefe;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin" style="font-size: 12px">Black Light</p>
                                        </li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('purple-light')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'purple-light'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-purple-active"></span><span class="bg-purple"
                                                                                                 style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin" style="font-size: 12px">Purple Light</p>
                                        </li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('green-light')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'green-light'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-green-active"></span><span class="bg-green"
                                                                                                style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin" style="font-size: 12px">Green Light</p>
                                        </li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('red-light')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'red-light'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-red-active"></span><span class="bg-red"
                                                                                              style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin" style="font-size: 12px">Red Light</p></li>
                                        <li style="float:left; width: 33.33333%; padding: 5px;"
                                            onclick="NS.setSink('yellow-light')"><a
                                                    href="#"
                                                    style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                                                    class="clearfix <#if u.skin != 'yellow-light'>full-opacity-hover</#if>">
                                                <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                                           class="bg-yellow-active"></span><span class="bg-yellow"
                                                                                                 style="display:block; width: 80%; float: left; height: 7px;"></span>
                                                </div>
                                                <div>
                                                    <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                                            style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                                                </div>
                                            </a>
                                            <p class="text-center no-margin" style="font-size: 12px;">Yellow Light</p>
                                        </li>
                                    </ul>
                                </div>
                                <div class="tab-pane" id="uploadAvatar">
                                    <form class="form-horizontal">
                                        <div class="form-group">
                                            <div class="col-sm-offset-1 col-sm-10">
                                                <input id="uploadAvatarInput" name="file" type="file"
                                                       class="file-loading">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="tab-pane" id="changePassword">
                                    <form class="form-horizontal" id="changePasswordForm">
                                        <div class="form-group">
                                            <label for="password" class="col-sm-2 control-label">原密码</label>
                                            <div class="col-sm-10">
                                                <input name="password" type="password" class="form-control"
                                                       id="password"
                                                       placeholder="输入原密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="newPassword" class="col-sm-2 control-label">密码</label>
                                            <div class="col-sm-10">
                                                <input name="newPassword" type="password" class="form-control"
                                                       id="newPassword"
                                                       placeholder="输入新密码">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="confirmNewPassword" class="col-sm-2 control-label">确定新密码</label>
                                            <div class="col-sm-10">
                                                <input name="confirmNewPassword" type="password" class="form-control"
                                                       id="confirmNewPassword"
                                                       placeholder="再次输入密码">
                                            </div>
                                        </div>
                                        <hr/>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-10 btn-group">
                                                <button id="changePasswordBtn" type="button" class="btn btn-danger">修改
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="tab-pane" id="settings">
                                    <form class="form-horizontal" id="updateUserInfoForm">
                                        <div class="form-group">
                                            <label for="email" class="col-sm-2 control-label">邮箱地址</label>
                                            <div class="col-sm-10">
                                                <input v-model="updateUserInfo.email" name="email" type="email"
                                                       class="form-control" id="email"
                                                       placeholder="请输入邮箱地址">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="phone" class="col-sm-2 control-label">手机号</label>
                                            <div class="col-sm-10">
                                                <input v-model="updateUserInfo.phone" name="phone" type="text"
                                                       class="form-control" id="phone"
                                                       placeholder="请输入手机号">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="sexRadios" class="col-sm-2 control-label">性别</label>
                                            <div class="radio col-sm-1">
                                                <label>
                                                    <input type="radio" v-model="updateUserInfo.sex" name="sex"
                                                           id="sexRadios1" value="M">男
                                                </label>
                                            </div>
                                            <div class="radio col-sm-1">
                                                <label>
                                                    <input type="radio" v-model="updateUserInfo.sex" name="sex"
                                                           id="sexRadios2" value="F">女
                                                </label>
                                            </div>
                                            <div class="col-sm-pull-6"></div>
                                        </div>
                                        <hr/>
                                        <div class="form-group">
                                            <div class="col-sm-offset-2 col-sm-10 btn-group">
                                                <button id="updateUserInfoBtn" type="button" class="btn btn-danger">保存
                                                </button>
                                                <button type="reset" class="btn btn-warning">重置</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <!-- /.tab-pane -->
                            </div>
                            <!-- /.tab-content -->
                        </div>
                        <!-- /.nav-tabs-custom -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
        </div>
        <!-- /.container -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <script src="/static/plugins/bootstrap-fileinput/js/plugins/piexif.min.js" type="text/javascript"></script>
    <script src="/static/plugins/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
    <script src="/static/plugins/bootstrap-fileinput/js/locales/zh.js" type="text/javascript"></script>
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script>
        $(function () {
            const currentUserSkin = '${u.skin}';
            const Main = new Vue({
                el: '#main',
                data: {
                    page: 0,
                    size: 6,
                    loginRecordList: null,
                    currentDateTime: null,
                    loadLock: false,
                    colorBgArray: ['blue', 'purple', 'red', 'yellow', 'orange', 'green', 'fuchsia', 'black', 'maroon', 'olive'],
                    updateUserInfo: {
                        email: <#if userInfo.email??>'${userInfo.email}'<#else>null</#if>,
                        phone: <#if userInfo.phone??>'${userInfo.phone}'<#else>null</#if>,
                        sex: '${userInfo.sex}'
                    }
                },
                methods: {
                    loadLoginRecordList: function () {
                        NS.post('/personalCenter/loginInfoList', {page: this.page, size: this.size}, (res) => {
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
                if (scrollTop + windowHeight + 5 >= scrollHeight && Main.loadLock && $('#loginRecordTabLi')[0]
                    .className === "active") {
                    Main.loadLock = false;
                    Main.loadLoginRecordList();
                }
            });
            NS.setSink = (skinName) => {
                if (NS.isNull(skinName) || skinName === currentUserSkin) {
                    return;
                }
                const setSinkLoad = xtip.load('设置主题中...');
                NS.post('/personalCenter/setSkin', {skinName: skinName}, (res) => {
                    if (res.code === 1000) {
                        xtip.msg('主题设置成功！页面刷新后即可看见效果。');
                    } else {
                        xtip.msg('界面主题设置失败！');
                    }
                    xtip.close(setSinkLoad);
                });
            };
            const uploadAvatarInput = $('#uploadAvatarInput');
            const changePasswordForm = $('#changePasswordForm');
            const updateUserInfoForm = $('#updateUserInfoForm');
            uploadAvatarInput.fileinput({
                language: 'zh',
                uploadUrl: NS.api.uploadCommonFileUrl,
                allowedFileExtensions: ['jpg', 'gif', 'png'],
                showUpload: true,
                showCaption: true,
                maxFileCount: 1,
                maxFileSize: 1024,
                enctype: 'multipart/form-data',
                validateInitialCount: true,
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
            });
            uploadAvatarInput.on('fileuploaded', (event, data, previewId, index) => {
                const res = data.response;
                if (res.code === 1000) {
                    NS.post('/personalCenter/saveAvatar', {avatarUrl: NS.getCommonFileUrl(res.data.file)}, (r) => {
                        if (r.code === 1000) {
                            xtip.msg('设置头像成功！刷新页面即可看见结果。', {icon: 's'});
                        } else {
                            xtip.msg('头像上传成功，但是在保存的时候发生错误。', {icon: 'e'});
                        }
                    })
                } else {
                    xtip.msg('系统错误！', {icon: 'e'});
                }
            });
            changePasswordForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败！',
                fields: {
                    password: {
                        validators: {
                            notEmpty: {
                                message: '原密码不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]{6,32}$/,
                                message: '密码由6-32位的字母、数字和"_"组成'
                            }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: '新密码不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]{6,32}$/,
                                message: '密码由6-32位的字母、数字和"_"组成'
                            },
                            identical: {
                                field: 'confirmNewPassword',
                                message: '输入的两次新密码不一致'
                            },
                            different: {
                                field: 'password',
                                message: '新密码不能和原密码相同'
                            }
                        }
                    },
                    confirmNewPassword: {
                        validators: {
                            notEmpty: {
                                message: '重复密码不能为空'
                            },
                            identical: {
                                field: 'newPassword',
                                message: '输入的两次新密码不一致'
                            },
                            different: {
                                field: 'password',
                                message: '新密码不能和原密码相同'
                            }
                        }
                    }
                }
            });
            updateUserInfoForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    sex: {
                        validators: {
                            notEmpty: {
                                message: '请确定自己的性别'
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
                    }
                }
            });
            $('#changePasswordBtn').on('click', () => {
                const bootstrapValidator = changePasswordForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const changeing = xtip.load('修改中...');
                    NS.post('/personalCenter/changePassword', changePasswordForm.serializeArray(), (res) => {
                        if (res.code === 1000) {
                            xtip.msg('修改成功', {icon: 's'});
                            changePasswordForm[0].reset();
                        } else {
                            xtip.msg('修改失败！', {icon: 'e'});
                        }
                        xtip.close(changeing);
                    });
                }
            });
            $('#updateUserInfoBtn').on('click', () => {
                const bootstrapValidator = updateUserInfoForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const changeing = xtip.load('更新中...');
                    NS.post('/personalCenter/updateUserInfo', Main.updateUserInfo, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('更新成功', {icon: 's'});
                        } else {
                            xtip.msg('更新失败！', {icon: 'e'});
                        }
                        xtip.close(changeing);
                    });
                }
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />