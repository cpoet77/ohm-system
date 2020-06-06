<#-- 课群作业 -->
<#assign pageTitle>${courseGroup.courseGroupName!""}|我的课群</#assign>
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
                    <li><a href="/"><i class="fa fa-dashboard"></i>首页</a></li>
                    <li><a href="/myCourseGroup">我的课群</a></li>
                    <li class="active">${courseGroup.courseGroupName!""}</li>
                </ol>
            </section>
            <div class="box-header with-border">
                <div class="info-box bg-blue">
                    <span class="info-box-icon"><i class="ion ion-ios-heart-outline"></i></span>
                    <div class="info-box-content">
                        <h3>${courseGroup.courseGroupName!""}</h3>
                        <br/>
                        <h4>学生人数：${courseGroup.countStudent!0}</h4>
                    </div>
                </div>
            </div>
            <div class="box-body">
                <div class="box box-solid">
                    <div class="box-header with-border">
                        <form>
                            <div class="input-group input-group-sm">
                                <input type="text" class="form-control" placeholder="搜索作业">
                                <span class="input-group-btn">
                            <button type="button" class="btn btn-info btn-flat">
                                <span style="vertical-align: inherit;">
                                    <span class="fa fa-search" style="vertical-align: inherit;"></span>
                                </span>
                            </button>
                            </span>
                            </div>
                            <br/>
                            <div class="form-group">
                                <label>
                                    <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1"
                                           checked="">全部作业
                                </label>
                                <label>
                                    <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">进行中
                                </label>
                                <label>
                                    <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">已结束
                                </label>
                            </div>
                        </form>
                    </div>
                    <div class="box-body">
                        <div>
                            <div type="button" class="btn btn-block btn-social btn-default btn-lg">
                                <i class="fa fa-file-text-o" style="width: 60px"></i>
                                <div>
                                    <span class="box box-info box-solid"
                                          style="font-size: 15px; color: #00b3ee">进行中</span>
                                    <span style="font-size: 15px;">作业3</span>
                                </div>
                                <div style="float:left;font-size:12px;">
                                    <span>共 </span>
                                    <span>1</span>
                                    <span>人参与</span>
                                    <span>|</span>
                                    <span>2020-06-03</span>
                                    <span>|</span>
                                    <span class="fa fa-hourglass-half" style="color: #FF8A00; font-size:12px">剩余 9 天 7 小时</span>
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
