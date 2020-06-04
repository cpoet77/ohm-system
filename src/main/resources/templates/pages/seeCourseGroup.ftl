<#assign activeIndex = true />
<#assign pageTitle>我的课群</#assign>
<#include "../common/head.ftl" />
<body class="hold-transition skin-blue layout-top-nav">
<div class="wrapper">
    <#include "../common/header.ftl">
    <!-- Full Width Column -->
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    查看课群
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
                    <li><a href="#"></a>我的课群</li>
                    <li class="active">查看课群</li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <!-- 加入课程/创建课程 -->
                    <div class="col-md-2">

                    </div>
                    <div class="col-md-1">
                        <div class="btn-group margin">
                            <button type="button" class="btn btn-default dropdown-toggle bg-red"
                                    data-toggle="dropdown" aria-expanded="false"><span
                                        style="vertical-align: inherit;"><span style="vertical-align: inherit;">操作
                                        </span></span><span class="fa fa-caret-down"></span></button>
                            <ul class="dropdown-menu">
                                <li><a href="#"><span style="vertical-align: inherit;"><span
                                                    style="vertical-align: inherit;">创建课群</span></span></a></li>
                                <li><a href="#"><span style="vertical-align: inherit;"><span
                                                    style="vertical-align: inherit;">加入课群</span></span></a></li>
                            </ul>
                        </div>
                    </div>

                    <!-- 课群搜索 -->
                    <div class="col-md-8">
                        <!--<div class="box">-->
                        <!--<div class="box-header ">-->
                        <div class="input-group margin">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle bg-purple"
                                        data-toggle="dropdown" aria-expanded="false"><span
                                            style="vertical-align: inherit;"><span style="vertical-align: inherit;">课群
                                        </span></span><span class="fa fa-caret-down"></span></button>
                                <ul class="dropdown-menu">
                                    <li><a href="#"><span style="vertical-align: inherit;"><span
                                                        style="vertical-align: inherit;">我加入的</span></span></a></li>
                                    <li><a href="#"><span style="vertical-align: inherit;"><span
                                                        style="vertical-align: inherit;">我创建的</span></span></a></li>
                                </ul>
                            </div>
                            <input type="text" class="form-control" placeholder="搜索加入的课群">
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                <br/>
                <br/>
                <!-- 课群显示 -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="box">
                            <div class="panel box box-danger">
                                <div class="box-header">
                                    <h4 class="box-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#"><span
                                                    style="vertical-align: inherit;"><span style="vertical-align: inherit;">
                                                    我加入的
                                                </span></span></a>
                                        <a data-toggle="collapse" data-parent="#accordion" href="#"><span
                                                    style="vertical-align: inherit;"><span style="vertical-align: inherit;">
                                                    我创建的
                                                </span></span></a>
                                    </h4>
                                </div>

                            </div>
                            <div class="box box-body">
                                <div class="col-md-3">
                                    <!-- Widget: user widget style 1 -->
                                    <div class="box box-widget widget-user">
                                        <!-- Add the bg color to the header using any of the bg-* classes -->
                                        <div class="widget-user-header bg-black"
                                             style="background: url('../../static/dist/img/photo1.png') center center;">
                                            <h3 class="widget-user-username"><span
                                                        style="vertical-align: inherit;"><span
                                                            style="vertical-align: inherit;">伊丽莎白·皮尔斯</span></span></h3>
                                            <h5 class="widget-user-desc"><span style="vertical-align: inherit;"><span
                                                            style="vertical-align: inherit;">网页设计者</span></span></h5>
                                        </div>
                                        <div class="widget-user-image">
                                            <img class="img-circle" src="../static/dist/img/user1-128x128.jpg" alt="用户头像">
                                        </div>
                                        <div class="box-footer">
                                            <div class="row">
                                                <div class="col-sm-4 border-right">
                                                    <div class="description-block">
                                                        <h5 class="description-header"><span
                                                                    style="vertical-align: inherit;"><span
                                                                        style="vertical-align: inherit;">软件工程</span></span></h5>
                                                        <span class="description-text"><span
                                                                    style="vertical-align: inherit;"><span
                                                                        style="vertical-align: inherit;">销售</span></span></span>
                                                    </div>
                                                    <!-- /.description-block -->
                                                </div>
                                                <!-- /.col -->
                                                <div class="col-md-4 border-right">

                                                </div>
                                                <!-- /.col -->
                                                <div class="col-sm-4">
                                                    <div class="description-block">
                                                        <a href="#" class="btn btn-primary btn-block"><b>进入</b></a>
                                                    </div>
                                                    <!-- /.description-block -->
                                                </div>
                                                <!-- /.col -->
                                            </div>
                                            <!-- /.row -->
                                        </div>
                                    </div>
                                    <!-- /.widget-user -->
                                </div>
                                <div class="col-md-3">
                                    <div class="box box-widget widget-user">
                                        <div class="widget-user-header bg-black"
                                             style="background: url('../../static/dist/img/photo1.png') center center;">

                                        </div>
                                        <div class="widget-user-image">
                                            <img class="img-circle" src="../static/dist/img/user3-128x128.jpg" alt="用户头像">
                                        </div>
                                        <div class="box-footer">
                                            <h3 class="profile-username text-center">多媒体技术</h3>

                                            <p class="text-muted text-center">赵敏</p>

                                            <ul class="list-group list-group-unbordered">
                                                <li class="list-group-item">
                                                    <b>状态</b> <a class="pull-right">进行中</a>
                                                </li>
                                            </ul>
                                            <a href="#" class="btn btn-primary btn-block"><b>进入</b></a>
                                        </div>
                                    </div>
                                    <!-- /.widget-user -->
                                </div>
                                <div class="col-md-3">
                                    <div class="box box-widget widget-user">
                                        <div class="widget-user-header bg-black"
                                             style="background: url('../../static/dist/img/photo1.png') center center;">

                                        </div>
                                        <div class="widget-user-image">
                                            <img class="img-circle" src="../static/dist/img/user2-160x160.jpg" alt="用户头像">
                                        </div>
                                        <div class="box-footer">
                                            <h3 class="profile-username text-center">数据库</h3>
                                            <p class="text-muted text-center">王娟</p>

                                            <ul class="list-group list-group-unbordered">
                                                <li class="list-group-item">
                                                    <b>状态</b> <a class="pull-right">进行中</a>
                                                </li>
                                            </ul>
                                            <a href="#" class="btn btn-primary btn-block"><b>进入</b></a>
                                        </div>
                                    </div>
                                    <!-- /.widget-user -->
                                </div>
                                <div class="col-md-3">
                                    <div class="box box-widget widget-user">
                                        <div class="widget-user-header bg-black"
                                             style="background: url('../../static/dist/img/photo1.png') center center;">

                                        </div>
                                        <div class="widget-user-image">
                                            <img class="img-circle" src="../static/dist/img/user4-128x128.jpg" alt="用户头像">
                                        </div>
                                        <div class="box-footer">
                                            <h3 class="profile-username text-center">Java程序设计</h3>

                                            <p class="text-muted text-center">某某</p>

                                            <ul class="list-group list-group-unbordered">
                                                <li class="list-group-item">
                                                    <b>状态</b> <a class="pull-right">已结束</a>
                                                </li>
                                            </ul>
                                            <a href="#" class="btn btn-primary btn-block"><b>进入</b></a>
                                        </div>
                                    </div>
                                    <!-- /.widget-user -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <!-- /.container -->
    </div>
    <#include "../common/copyright.ftl">
</div>
<!-- /.content-wrapper -->
<#include "../common/footer.ftl">
