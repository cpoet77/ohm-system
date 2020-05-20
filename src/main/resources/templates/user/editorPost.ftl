<#-- 编辑文章 -->
<#import "../function/spring.ftl" as spring />
<#assign pageTitle>发布文章</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl" />
    <!-- Full Width Column -->
    <div class="content-wrapper" id="main">
        <div class="container">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    发表文章
                </h1>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <form role="form">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="postTitle">标题</label>
                                    <input type="text" class="form-control" id="postTitle" name="postTitle"
                                           placeholder="输入文章标题">
                                </div>
                                <div class="form-group">
                                    <label for="postSubtitle">副标题</label>
                                    <input type="text" class="form-control" id="postSubtitle" name="postSubtitle"
                                           placeholder="输入文章副标题">
                                </div>
                                <div class="form-group">
                                    <label>类别：</label>
                                    <select class="form-control" name="postClassify">
                                        <#list postClassifyList as postClassify>
                                            <option value="${postClassify.id}">${postClassify.name}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="postContent">文章内容</label>
                                    <textarea id="postContent" rows="20" cols="80"></textarea>
                                </div>
                                <button id="submitPost" type="button" class="btn btn-primary">直接发布</button>
                                <button type="button" class="btn btn-yahoo">保存为草稿</button>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.container -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <script type="text/javascript" src="/static/plugins/textboxio/textboxio.js"></script>
    <script>
        const postContentEditor = textboxio.replace('#postContent', NS.textboxioConfig);
        const formPost = $('form');
        $('#submitPost').on('click', () => {
            let data = formPost.serializeArray();
            data[data.length] = {name: 'postContent', value: postContentEditor.content.get()};
            NS.post("/user/post/savePost", data, (res) => {
                if (res.code === 1000) {
                    xtip.msg('发布成功！', {icon: 's'});
                } else {
                    xtip.msg('发布失败！', {icon: 'e'});
                }
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl" />
