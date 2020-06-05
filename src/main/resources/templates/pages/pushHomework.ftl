<#assign activeIndex = true />
<#assign pageTitle>提交作业</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl">
    <!-- Full Width Column -->
    <div class="content-wrapper">
        <div class="container">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    提交作业
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
                    <li><a href="#">我的作业</a></li>
                    <li class="active">提交作业</li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary">
                            <div class="box-header with-border callout callout-info bg-red">
                                <h6 class="box-title ">填写提交作业信息</h6>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <div class="form-group box box-info">
                                    <br>
                                    <h3 class="box-title">第一章、虎落平阳你不陪，东山再起你是谁</h3>
                                </div>
                                <div class="form-group box box-info ">
                                    <br>
                                    <h4 class="box-title">作业详情</h4>
                                    <h5 class="box-title"> 4个附件</h5>
                                    <div class="box-group">
                                        <img src="http://placehold.it/150x100" alt="..." class="margin">
                                        <img src="http://placehold.it/150x100" alt="..." class="margin">
                                        <img src="http://placehold.it/150x100" alt="..." class="margin">
                                        <img src="http://placehold.it/150x100" alt="..." class="margin">
                                        <a style="background-image: url(../../static/dist/img/user1-128x128.jpg)"
                                           href="#"> 下载 </a>
                                    </div>
                                </div>
                                <div class="form-group box box-info">
                                    <br>
                                    <h4 class="box-title ">作业内容</h4>
                                    <textarea class="form-control" placeholder="作业内容"
                                              style="width: 100%; height: 180px; font-size: 14px; line-height: 16px; border: 1px solid #dddddd; padding: 10px;">
                                    </textarea>
                                </div>
                                <div class="form-group box box-info"><br>
                                    <div class="btn btn-default btn-file">
                                        <i class="fa fa-paperclip"></i> 添加附件
                                        <input type="file" name="attachment">
                                    </div>
                                    <div class="bg-yellow">
                                        <b>
                                            注意! 最多可添加 30 个附件，单个文件最大限制 300 MB，视频支持 MP4 格式, H.264+AAC, 建议大小: 640*480,
                                            更多信息请参考
                                        </b>
                                    </div>
                                </div>

                                <div class="table table-bordered text-center">
                                    <button type="button" class="btn btn-info">提交</button>
                                    <button type="reset" class="btn btn-info">重置</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.container -->
    </div>
</div>
<!-- /.content-wrapper -->
<#include "../common/copyright.ftl">
<#include "../common/footer.ftl">
