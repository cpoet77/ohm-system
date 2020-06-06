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
                <div class="info-box bg-blue">
                    <span class="info-box-icon"><i class="ion ion-ios-heart-outline"></i></span>
                    <div class="info-box-content">
                        <h3>计科2班</h3>
                        <h6>软件工程</h6>
                    </div>
                </div>
            </div>
            <div class="box-body">
                <div class="box box-solid">
                    <div class="box-header with-border">
                        <div class="input-group input-group-sm">
                            <input type="text" class="form-control" placeholder="按作业名搜索">
                            <span class="input-group-btn">
                            <button type="button" class="btn btn-info btn-flat">
                                <span style="vertical-align: inherit;">
                                    <span class="fa fa-search" style="vertical-align: inherit;"></span>
                                </span>
                            </button>
                            </span>
                        </div>
                        <div class="form-group">
                            <hr>
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">全部作业
                            </label>
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">进行中
                            </label>
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">已结束
                            </label>
                        </div>
                    </div>
                    <div class="box-body">
                        <div>
                            <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                <i class="fa fa-file-text-o" style="width: 60px"></i>
                                <div>
                                    <span class="box box-info box-solid" style="font-size: 15px; color: #00b3ee">进行中</span>
                                    <span style="font-size: 15px;">作业3</span>
                                </div>

                                <div class="" style="float:left;">
                                    <span style="font-size:12px">共 </span>
                                    <span style="font-size:12px">1</span>
                                    <span style="font-size:12px">人参与</span>
                                    <span style="font-size:12px">|</span>
                                    <span style="font-size:12px">2020-06-03</span>
                                    <span style="font-size:12px">|</span>
                                    <span class="fa fa-hourglass-half"
                                          style="color: #FF8A00; font-size:12px">剩余 9 天 7 小时</span>
                                    <div class="clear"></div>
                                </div>
                            </button>
                        </div>
                        <div>
                            <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                <i class="fa fa-file-text-o" style="width: 60px"></i>
                                <div>
                                    <span class="box box-info box-solid" style="font-size: 15px; color: #00b3ee">进行中</span>
                                    <span style="font-size: 15px;">作业2</span>
                                </div>

                                <div class="" style="float:left;">
                                    <span style="font-size:12px">共 </span>
                                    <span style="font-size:12px">1</span>
                                    <span style="font-size:12px">人参与</span>
                                    <span style="font-size:12px">|</span>
                                    <span style="font-size:12px">2020-06-03</span>
                                    <span style="font-size:12px">|</span>
                                    <span class="fa fa-hourglass-half"
                                          style="color: #FF8A00; font-size:12px">剩余 9 天 7 小时</span>
                                    <div class="clear"></div>
                                </div>
                            </button>
                        </div>
                        <div>
                            <button type="button" class="btn btn-block btn-social btn-default btn-lg">
                                <i class="fa fa-file-text-o" style="width: 60px"></i>
                                <div>
                                    <span class="box box-info box-solid" style="font-size: 15px; color: #00b3ee">进行中</span>
                                    <span style="font-size: 15px;">作业1</span>
                                </div>

                                <div class="" style="float:left;">
                                    <span style="font-size:12px">共 </span>
                                    <span style="font-size:12px">1</span>
                                    <span style="font-size:12px">人参与</span>
                                    <span style="font-size:12px">|</span>
                                    <span style="font-size:12px">2020-06-03</span>
                                    <span style="font-size:12px">|</span>
                                    <span class="fa fa-hourglass-half"
                                          style="color: #FF8A00; font-size:12px">剩余 9 天 7 小时</span>
                                    <div class="clear"></div>
                                </div>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "../common/copyright.ftl"/>
</div>
<#include "../common/footer.ftl"/>
