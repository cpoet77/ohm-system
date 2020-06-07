<#-- 教师查看学生已提交的作业详情并完成评分 -->
<#assign pageTitle>作业详情|${homework.title!""}|${courseGroup.courseGroupName!""}</#assign>
<#assign restHead>
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
                    提交作业详情
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li><a href="/myCourseGroup">我的课群</a></li>
                    <li><a href="/homework?courseGroup=${courseGroup.id}">${courseGroup.courseGroupName!""}</a></li>
                    <li class="active">提交作业详情</li>
                </ol>
            </section>
            <section class="content">
                <!-- general form elements -->
                <div class="box box-danger">
                    <form role="form" id="markForm">
                        <div class="box-body">
                            ${pushHomework.text!""}
                            <br/>
                            <div class="row">
                                <#list pushHomework.resources as resource>
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
                                <label for="score">评分</label>
                                <input name="score" min="0" max="100" type="number" class="form-control" id="score"
                                       placeholder="输入作业评分" value="${pushHomework.score!""}">
                            </div>
                            <div class="form-group">
                                <label for="textContentEditor">评价</label>
                                <textarea name="description" id="textContentEditor" rows="20"
                                          cols="80">${pushHomework.assess!""}</textarea>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <button type="button"
                                    onclick="NS.to('/pushHomeworkList?courseGroup=${courseGroup.id}&homework=${homework.id}')"
                                    class="btn btn-success pull-left">返回上级
                            </button>
                            <button id="markBtn" type="button" class="btn btn-warning pull-right">保存评价
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
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script type="text/javascript" src="/static/plugins/textboxio/textboxio.js"></script>
    <script>
        $(function () {
            const textContentEditor = textboxio.replace('#textContentEditor', NS.textboxioConfig);
            const markForm = $('#markForm');

            function reloadMarkFormValidator() {
                try {
                    markForm.data('bootstrapValidator').destroy();
                    markForm.data('bootstrapValidator', null);
                } catch (e) {
                }
                markForm.bootstrapValidator({
                    verbose: false,     /* 对field内的条件按顺序验证 */
                    message: '数据校验失败',
                    fields: {
                        score: {
                            validators: {
                                notEmpty: {
                                    message: '请输入分数！'
                                }
                            }
                        }
                    }
                });
            }

            $('#markBtn').on('click', () => {
                reloadMarkFormValidator();
                const bootstrapValidator = markForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const adding = xtip.load('保存中...');
                    const description = textContentEditor.content.get();
                    NS.post('/pushHomeworkInfo/saveScore', {
                            pushHomeworkId: ${pushHomework.id},
                            score: new FormData(markForm[0]).get('score'),
                            assess: description
                        },
                        (res) => {
                            if (res.code === 1000) {
                                xtip.msg('保存成功！', {icon: 's'});
                                NS.to('/pushHomeworkList?courseGroup=${courseGroup.id}&homework=${homework.id}', 3000);
                            } else {
                                xtip.msg('保存失败！', {icon: 'e'});
                            }
                            xtip.close(adding);
                        });
                }
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
