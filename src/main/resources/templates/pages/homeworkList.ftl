<#-- 作业管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>作业管理</#assign>
<#assign isCourseManagement = true />
<#assign isCollegeManagement = true />
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl"/>
    <div class="content-wrapper">
        <div class="container">
            <div class="col-md-12">
                <div class="box box-solid">
                    <section class="content-header">
                        <h1>
                            作业管理
                        </h1>
                        <ol class="breadcrumb">
                            <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
                            <li><a href="#">我的课群</a></li>
                            <li class="active">作业管理</li>
                        </ol>
                    </section>
                    <div class="box-header with-border">
                        <div class="info-box bg-green">
                            <span class="info-box-icon"><i class="ion ion-ios-heart-outline"></i></span>
                            <div class="info-box-content">
                                <h3>计科2班</h3>
                                <h6>软件工程</h6>
                            </div>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="col-md-12">
                            <div class="box box-solid">
                                <div class="box-header with-border">
                                    <div class="box-header">
                                        <h3 class="box-title">
                                    <span style="vertical-align: inherit;">
                                    <span style="vertical-align: inherit;">作业列表</span></span>
                                        </h3>
                                        <div class="box-tools">
                                            <div class="input-group input-group-sm" style="width: 150px;">
                                                <input type="text" name="table_search" class="form-control pull-right"
                                                       placeholder="搜索">
                                                <div class="input-group-btn">
                                                    <button type="submit" class="btn btn-default"><i
                                                                class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="box-body">
                                    <div>
                                        <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                            <i class="fa fa-file-text-o"></i>
                                            <span class="box box-success box-solid">进行中</span>
                                            作业6
                                            <div class="" style="width:822px;">
                                                <div class="" style="float:left;">
                                                    <span>共 </span>
                                                    <span>1</span>
                                                    <span>人参与</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span>2020-06-03</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span style="color: #cc0000">10 经验</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span class="fa fa-hourglass-half"
                                                          style="color: #cc0000; vertical-align:middle;">剩余 9 天 7 小时 </span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </button>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                            <i class="fa fa-file-text-o"></i>
                                            <span class="box box-success box-solid">进行中</span>
                                            作业5
                                            <div class="" style="width:822px;">
                                                <div class="" style="float:left;">
                                                    <span>共 </span>
                                                    <span>1</span>
                                                    <span>人参与</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span>2020-06-03</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span style="color: #cc0000">10 经验</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span class="fa fa-hourglass-half"
                                                          style="color: #cc0000; vertical-align:middle;">剩余 9 天 7 小时 </span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </button>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                            <i class="fa fa-file-text-o"></i>
                                            <span class="box box-success box-solid">进行中</span>
                                            作业4
                                            <div class="" style="width:822px;">
                                                <div class="" style="float:left;">
                                                    <span>共 </span>
                                                    <span>1</span>
                                                    <span>人参与</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span>2020-06-03</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span style="color: #cc0000">10 经验</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span class="fa fa-hourglass-half"
                                                          style="color: #cc0000; vertical-align:middle;">剩余 9 天 7 小时 </span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </button>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                            <i class="fa fa-file-text-o"></i>
                                            <span class="box box-success box-solid">进行中</span>
                                            作业3
                                            <div class="" style="width:822px;">
                                                <div class="" style="float:left;">
                                                    <span>共 </span>
                                                    <span>1</span>
                                                    <span>人参与</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span>2020-06-03</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span style="color: #cc0000">10 经验</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span class="fa fa-hourglass-half"
                                                          style="color: #cc0000; vertical-align:middle;">剩余 9 天 7 小时 </span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </button>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                            <i class="fa fa-file-text-o"></i>
                                            <span class="box box-success box-solid">进行中</span>
                                            作业2
                                            <div class="" style="width:822px;">
                                                <div class="" style="float:left;">
                                                    <span>共 </span>
                                                    <span>1</span>
                                                    <span>人参与</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span>2020-06-03</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span style="color: #cc0000">10 经验</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span class="fa fa-hourglass-half"
                                                          style="color: #cc0000; vertical-align:middle;">剩余 9 天 7 小时 </span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </button>
                                    </div>
                                    <div>
                                        <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                            <i class="fa fa-file-text-o"></i>
                                            <span class="box box-success box-solid">进行中</span>
                                            作业
                                            <div class="" style="width:822px;">
                                                <div class="" style="float:left;">
                                                    <span>共 </span>
                                                    <span>1</span>
                                                    <span>人参与</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span>2020-06-03</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span style="color: #cc0000">10 经验</span>
                                                    <span style="margin:0 5px;">|</span>
                                                    <span class="fa fa-hourglass-half"
                                                          style="color: #cc0000; vertical-align:middle;">剩余 9 天 7 小时 </span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "../common/copyright.ftl"/>
</div>
<#include "../common/footer.ftl"/>
