<#-- 学院管理 -->
<#assign activeIndex></#assign>
<#assign pageTitle>专业课群</#assign>
<#include "../common/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "../common/header.ftl" />
    <#include "../common/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                课程管理
                <!--<small>advanced tables</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
                <li><a href="#">课程管理</a></li>
                <li class="active">专业管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">专业管理</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>学制</th>
                                    <th>介绍</th>
                                    <th>学科</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th>1</th>
                                    <td>数据科学与信息工程学院</td>
                                    <td>计算机科学与技术</td>
                                    <td>4</td>
                                    <th>******</th>
                                    <td>工学</td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>序号</th>
                                    <th>学院</th>
                                    <th>专业</th>
                                    <th>学制</th>
                                    <th>介绍</th>
                                    <th>学科</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../common/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#include "../common/footer.ftl" />
