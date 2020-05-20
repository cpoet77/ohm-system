<#-- 404 not found 页面 -->
<#import "../function/spring.ftl" as spring />
<#assign pageTitle>404 Not found</#assign>
<#assign notUser></#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl" />
    <!-- Full Width Column -->
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="/"><i class="fa fa-dashboard"></i> <@spring.message "dict.common.home" /></a></li>
                <li>error</li>
                <li class="active">404</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline text-yellow"> 404</h2>

                <div class="error-content">
                    <h3><i class="fa fa-warning text-yellow"></i> <@spring.message "dict.error.404.title" /></h3>

                    <p>
                        <@spring.message "dict.error.404.msg" />
                    </p>

                    <form class="search-form" action="/search" method="get">
                        <div class="input-group">
                            <input type="text" name="keyword" class="form-control" placeholder="<@spring.message "dict.common.search" />">

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
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#include "../common/footer.ftl" />
