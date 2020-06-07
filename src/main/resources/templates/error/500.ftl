<#-- 404 not found 页面 -->
<#assign pageTitle>404 Internal Server Error</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <!-- Full Width Column -->
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-red">500</h2>

                <div class="error-content">
                    <h3><i class="fa fa-warning text-red"></i> 哎呀！出问题啦。</h3>

                    <p>
                        我们会马上解决的。<br />
                        现在，您可以<a href="/" title="首页">返回主页</a>或尝试使用搜索功能。
                    </p>

                    <form class="search-form" action="/search" method="get">
                        <div class="input-group">
                            <input type="text" name="keyword" class="form-control" placeholder="搜索">

                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-warning btn-flat"><i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.input-group -->
                    </form>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#include "../common/footer.ftl" />
