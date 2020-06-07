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
                    <form role="form" id="publishHomeworkForm">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="title">标题</label>
                                <input name="title" type="text" maxlength="64" class="form-control" id="title"
                                       placeholder="例如：第一次作业">
                            </div>
                            <div class="form-group">
                                <label for="textContentEditor">描述</label>
                                <textarea name="description" id="textContentEditor" rows="20" cols="80"></textarea>
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
                                    <input type="text" name="startTimeAndEndTime" class="form-control pull-right"
                                           id="startTimeAndEndTime">
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button type="button" onclick="NS.to('/homework?courseGroup=${courseGroup.id}')"
                                    class="btn btn-success pull-left">返回上级
                            </button>
                            <button id="publishHomeworkBtn" type="button" class="btn btn-warning pull-right">发布作业
                            </button>
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
            const uploadFiles = new Map();
            const textContentEditor = textboxio.replace('#textContentEditor', NS.textboxioConfig);
            const uploadFileInput = $('#uploadFileInput');
            let startTime = null;
            let endTime = null;
            $('#startTimeAndEndTime').daterangepicker({
                'locale': {
                    "format": 'YYYY-MM-DD HH:mm:ss',
                    "separator": " 至 ",
                    "applyLabel": "确定",
                    "cancelLabel": "取消",
                    "fromLabel": "开始时间",
                    "toLabel": "结束时间'",
                    "customRangeLabel": "自定义",
                    "weekLabel": "W",
                    "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                    "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    "firstDay": 1
                },
                "timePicker": true,
                "timePicker24Hour": true,
                "startDate": moment(),
                "endDate": moment(),
                "minDate": moment(),
                "maxDate": moment().add(45, 'days'),
                "opens": "center",
                "drops": "up"
            }, function (start, end, label) {
                startTime = start.format('YYYY-MM-DD HH:mm:ss');
                endTime = end.format('YYYY-MM-DD HH:mm:ss');
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
                removeFromPreviewOnError: true,
                validateInitialCount: true,
                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
            });
            uploadFileInput.on('fileuploaded', (event, data, previewId, index) => {
                const res = data.response;
                if (res.code === 1000) {
                    insertResourceToFiles(previewId, res.data.resource);
                } else {
                    xtip.msg('系统错误！', {icon: 'e'});
                }
            }).on('filesuccessremove', (event, previewId, extra) => {
                const resource = uploadFiles.get(previewId);
                if (NS.isNull(resource)) {
                    return;
                }
                NS.post(NS.api.deleteFileUrl, {resourceId: resource.id}, (res) => {
                    if (res.code === 1000) {
                        deleteResourceForFiles(previewId);
                    } else {
                        xtip.msg('系统错误！', {icon: 'e'});
                    }
                });
            });

            function insertResourceToFiles(previewId, resource) {
                uploadFiles.set(previewId, resource);
            }

            function deleteResourceForFiles(previewId) {
                if (!uploadFiles.delete(previewId)) {
                    xtip.msg('系统错误！', {icon: 'e'});
                }
            }

            const publishHomeworkForm = $('#publishHomeworkForm');

            function reloadPublishHomeworkFormValidator() {
                try {
                    publishHomeworkForm.data('bootstrapValidator').destroy();
                    publishHomeworkForm.data('bootstrapValidator', null);
                } catch (e) {
                }
                publishHomeworkForm.bootstrapValidator({
                    verbose: false,     /* 对field内的条件按顺序验证 */
                    message: '数据校验失败',
                    fields: {
                        title: {
                            validators: {
                                notEmpty: {
                                    message: '作业标题不能为空'
                                },
                                stringLength: {
                                    min: 2,
                                    max: 64,
                                    message: '作业标题长度在1-64位'
                                }
                            }
                        },
                        startTimeAndEndTime: {
                            validators: {
                                callback: {
                                    message: '请确定作业的开始时间和结束时间',
                                    callback: function (value, validator) {
                                        return !(NS.isNull(startTime) || NS.isNull(endTime) || startTime === endTime);
                                    }
                                }
                            }
                        }
                    }
                });
            }

            $('#publishHomeworkBtn').on('click', () => {
                reloadPublishHomeworkFormValidator();
                const bootstrapValidator = publishHomeworkForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const adding = xtip.load('发布中...');
                    let filesArray = [];
                    for (let value of uploadFiles.values()) {
                        filesArray[filesArray.length] = value.id;
                    }
                    const title = (new FormData(publishHomeworkForm[0])).get('title');
                    const description = textContentEditor.content.get();
                    const files = filesArray.toString();
                    NS.post('/publishHomework/addHomework', {
                        courseGroupId: ${courseGroup.id},
                        title: title,
                        description: description,
                        files: files,
                        startTime: startTime,
                        endTime: endTime
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('发布成功！', {icon: 's'});
                            NS.to('/homework?courseGroup=${courseGroup.id}', 3000);
                        } else {
                            xtip.msg('发布失败！', {icon: 'e'});
                        }
                        xtip.close(adding);
                    });
                }
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
