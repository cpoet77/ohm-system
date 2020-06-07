<#-- 学生提交作业 -->
<#assign pageTitle>提交作业|${courseGroup.courseGroupName!""}</#assign>
<#assign restHead>
    <link href="/static/plugins/bootstrap-fileinput/css/fileinput.min.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
</#assign>
<#include "../common/head.ftl"/>
<!-- Full Width Column -->
<div class="wrapper">
    <#include "../common/header.ftl"/>
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    提交作业
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li><a href="/myCourseGroup">我的课群</a></li>
                    <li><a href="/homework?courseGroup=${courseGroup.id}">${courseGroup.courseGroupName!""}</a></li>
                    <li class="active">提交作业</li>
                </ol>
            </section>
            <section class="content">
                <!-- general form elements -->
                <div class="box box-danger">
                    <form role="form" id="pushHomeworkForm">
                        <div class="box-body">
                            <h3>${homework.title!""}</h3>
                            <br/>
                            ${homework.content!"无"}
                            <br/>
                            <div class="row">
                                <#list homework.resources as resource>
                                    <div class="col-md-3 col-sm-6 col-xs-12">
                                        <div class="info-box bg-green"
                                             onclick="NS.to(NS.getSentinelResourceUrl('${resource.id}', '${resource.name}', '${resource.suffix}'))">
                                            <span class="info-box-icon bg-yellow"><i class="fa fa-files-o"></i></span>
                                            <div class="info-box-content">
                                                <span class="info-box-text">${resource.name!""}</span>
                                                <span class="info-box-number">${resource.suffix}</span>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                            <hr/>
                            <div class="form-group">
                                <label for="textContentEditor">描述</label>
                                <textarea name="description" id="textContentEditor" rows="20" cols="80"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="uploadFileInput">附件</label>
                                <input id="uploadFileInput" name="file" type="file" class="file-loading">
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button type="button" onclick="NS.to('/homework?courseGroup=${courseGroup.id}')"
                                    class="btn btn-success pull-left">返回上级
                            </button>
                            <button id="publishHomeworkBtn" type="button" class="btn btn-warning pull-right">提交作业
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
    <script>
        $(function () {
            const uploadFiles = new Map();
            const textContentEditor = textboxio.replace('#textContentEditor', NS.textboxioConfig);
            const uploadFileInput = $('#uploadFileInput');
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

            const pushHomeworkForm = $('#pushHomeworkForm');

            function reloadPushHomeworkFormValidator() {
                try {
                    pushHomeworkForm.data('bootstrapValidator').destroy();
                    pushHomeworkForm.data('bootstrapValidator', null);
                } catch (e) {
                }
                pushHomeworkForm.bootstrapValidator({
                    verbose: false,     /* 对field内的条件按顺序验证 */
                    message: '数据校验失败',
                    fields: {
                        file: {
                            validators: {
                                callback: {
                                    message: '描述和上传文件至少选择完成一项!',
                                    callback: function (value, validator) {
                                        return textContentEditor.content.isDirty() || uploadFiles.size > 0;
                                    }
                                }
                            }
                        }
                    }
                });
            }

            $('#publishHomeworkBtn').on('click', () => {
                reloadPushHomeworkFormValidator();
                const bootstrapValidator = pushHomeworkForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const adding = xtip.load('提交中...');
                    let filesArray = [];
                    for (let value of uploadFiles.values()) {
                        filesArray[filesArray.length] = value.id;
                    }
                    const description = textContentEditor.content.get();
                    const files = filesArray.toString();
                    NS.post('/pushHomework/pushHomework', {
                        homeworkId: ${homework.id},
                        description: description,
                        files: files
                    }, (res) => {
                        if (res.code === 1000) {
                            xtip.msg('提交成功！', {icon: 's'});
                            NS.to('/homework?courseGroup=${courseGroup.id}', 3000);
                        } else {
                            xtip.msg('提交失败！', {icon: 'e'});
                        }
                        xtip.close(adding);
                    });
                }
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
