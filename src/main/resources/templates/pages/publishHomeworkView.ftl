<#-- 教师发布作业 -->
<#assign pageTitle>发布作业|${courseGroup.name!""}</#assign>
<#assign restHead>
    <link href="/static/plugins/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
    <link rel="stylesheet" href="/static/plugins/daterangepicker/daterangepicker.css">
</#assign>
<#include "../common/head.ftl"/>
<!-- Full Width Column -->
<div class="wrapper">
    <#include "../common/header.ftl"/>
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    发布作业
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li><a href="/myCourseGroup">我的课群</a></li>
                    <li><a href="/homework?courseGroup=${courseGroup.id}">${courseGroup.name!""}</a></li>
                    <li class="active">发布作业</li>
                </ol>
            </section>
            <section class="content">
                <!-- general form elements -->
                <div class="box box-danger">
                    <form role="form">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">标题</label>
                                <input name="title" type="text" maxlength="64" class="form-control" id="title"
                                       placeholder="例如：第一次作业">
                            </div>
                            <div class="form-group">
                                <label for="textContentEditor">描述</label>
                                <textarea id="textContentEditor" rows="20" cols="80"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="uploadFileInput">附件</label>
                                <input id="uploadFileInput" name="file" type="file" class="file-loading">
                            </div>
                            <div class="form-group">
                                <label for="startTimeAndEndTime">开始时间-结束时间</label>
                                <div class="input-group">
                                    <div class="input-group-addon">
                                        <i class="fa fa-clock-o"></i>
                                    </div>
                                    <input type="text" class="form-control pull-right" id="startTimeAndEndTime">
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button type="button" onclick="NS.to('/homework?courseGroup=${courseGroup.id}')"
                                    class="btn btn-success pull-left">返回上级
                            </button>
                            <button type="button" class="btn btn-warning pull-right">发布作业</button>
                        </div>
                    </form>
                </div>
                <!-- /.box -->
            </section>
        </div>
    </div>
    <#include "../common/copyright.ftl"/>
</div>
<!-- /.content-wrapper -->
<#assign restFooter>
    <script src="/static/plugins/bootstrap-fileinput/js/plugins/piexif.min.js" type="text/javascript"></script>
    <script src="/static/plugins/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
    <script src="/static/plugins/bootstrap-fileinput/js/locales/zh.js" type="text/javascript"></script>
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script type="text/javascript" src="/static/plugins/textboxio/textboxio.js"></script>
    <script src="/static/plugins/daterangepicker/moment.min.js"></script>
    <script src="/static/plugins/daterangepicker/daterangepicker.js"></script>
    <script>
        $(function () {
            const textContentEditor = textboxio.replace('#textContentEditor', NS.textboxioConfig);
            const uploadFileInput = $('#uploadFileInput');
            $('#startTimeAndEndTime').daterangepicker({
                timePicker: true,
                timePickerIncrement: 30,
                format: 'MM/DD/YYYY h:mm A'
            });
            uploadFileInput.fileinput({
                language: 'zh',
                uploadUrl: NS.api.uploadSentinelResourceUrl,
                allowedFileExtensions: ['jpg', 'gif', 'png', 'doc', 'docx', 'xls', 'xlsx', 'zip', 'rar', 'mp4', 'mp3'],
                showUpload: true,
                showCaption: true,
                maxFileCount: 25,
                maxFileSize: 10240,
                enctype: 'multipart/form-data',
                validateInitialCount: true,
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
            });
            uploadFileInput.on('fileuploaded', (event, data, previewId, index) => {
                const res = data.response;
                if (res.code === 1000) {

                } else {
                    xtip.msg('系统错误！', {icon: 'e'});
                }
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
