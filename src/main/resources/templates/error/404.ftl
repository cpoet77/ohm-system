<#-- 404 not found 页面 -->
<#assign pageTitle>404 Not found</#assign>
<#assign notUser></#assign>
<#include "../common/admin/head.ftl" />
<div class="wrapper">
    <!-- Full Width Column -->
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> 控制台</a></li>
                <li>error</li>
                <li class="active">404</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow"> 404</h2>

                <div class="error-content">
                    <h3><i class="fa fa-warning text-yellow"></i> 哎呀！找不到页面。</h3>

                    <p>
                        我们找不到您要找的页面。<br />\
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
                <!-- /.error-content -->
            </div>
            <!-- /.error-page -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/admin/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#include "../common/footer.ftl" />
