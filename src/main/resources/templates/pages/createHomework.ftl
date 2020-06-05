<#-- 教师发布作业 -->
<#assign activeIndex = true />
<#assign pageTitle>发布作业</#assign>
<#include "../common/head.ftl"/>
<!-- Full Width Column -->
<div class="content-wrapper">
    <#include "../common/header.ftl"/>
    <div class="container">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                发布作业
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
                <li><a href="#">我的课群</a></li>
                <li class="active">发布作业</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" <#--style="width: 800px"-->>
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border callout callout-info bg-red" style="text-align: center">
                            <h6 class="box-title ">填写发布作业信息</h6>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="form-group box box-info ">
                                <br>
                                <h5 class="box-title">作业标题</h5>
                                <input class="form-control" placeholder="作业标题">
                            </div>
                            <!--<div class="form-group">-->
                            <!--<input class="form-control" placeholder="">-->
                            <!--</div>-->

                            <div class="form-group box box-info">
                                <br>
                                <h5 class="box-title ">作业内容</h5>
                                <textarea class="form-control" placeholder="作业内容"
                                          style="width: 100%; height: 180px; font-size: 14px; line-height: 16px; border: 1px solid #dddddd; padding: 10px;">

                                    </textarea>
                                <div class="bootstrap-timepicker">
                                    <div class="form-group box box-info">
                                        <br>
                                        <label>Date and time range:</label>

                                        <div class="input-group">
                                            <div class="input-group-addon">
                                                <i class="fa fa-clock-o"></i>
                                            </div>
                                            <input type="text" class="form-control pull-right" id="reservationtime">
                                        </div>
                                        <!-- /.input group -->
                                    </div>
                                </div>
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
                                <button type="button" class="btn btn-info">发布</button>
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
<!-- /.content-wrapper -->
<#include "../common/copyright.ftl"/>
<#assign restFooter>
    <!-- date-range-picker -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
    <script src="/static/plugins/daterangepicker/daterangepicker.js"></script>
    <script>
        $(function () {
            $('#reservationtime').daterangepicker({
                timePicker: true,
                timePickerIncrement: 30,
                format: 'MM/DD/YYYY h:mm A'
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
