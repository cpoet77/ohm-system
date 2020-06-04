<#-- 后台首页 -->
<#assign pageTitle>导入用户数据</#assign>
<#assign isUserManagement = true />
<#assign isUserImportData = true />
<#include "../../common/admin/head.ftl" />
<!-- Site wrapper -->
<div class="wrapper">
    <#include "../../common/admin/header.ftl" />
    <#include "../../common/admin/sidebar.ftl" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                导入用户数据
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
                <li><a href="#">用户管理</a></li>
                <li class="active">导入数据</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="callout callout-warning">
                <h3>导入须知：</h3>
                <ol>
                    <li>
                        <h4>作为管理员或者教学秘书：</h4>
                        <p>您可以下载我们提供的导入模板进行填写，按照模板上的格式填写完毕后即可上传并导入用户信息。</p>
                        <p>在上传文件并准备好要导入信息之前，您需要先确定导入的是学生信息还是教师信息。</p>
                        <p>由于某些原因（比如：部分用户信息已经存在）会导致某些数据导入失败，这部分数据系统会自动排除，并在导入完成后让您可以下载失败的数据，以便修正。</p>
                    </li>
                    <li>
                        <h4>作为管理员：</h4>
                        <p>系统对管理员几乎不做任何限制。</p>
                    </li>
                    <li>
                        <h4>作为教学秘书：</h4>
                        <p>教学秘书在导入学生信息的时候，系统不会自动为其创建不存在的学院信息，这部分数据会被跳过。</p>
                    </li>
                </ol>
            </div>
            <div class="box box-warning">
                <!-- form start -->
                <form role="form" id="importUserDataForm">
                    <div class="box-body">
                        <div class="btn-group-sm">
                            <button type="button" class="btn bg-navy btn-sm"><a href="/static/docs/学生信息导入模板.xlsx">下载
                                    学生信息导入模板.xlsx</a></button>

                            <button type="button" class="btn bg-navy btn-sm"><a href="/static/docs/教师信息导入模板.xlsx">下载
                                    教师信息导入模板.xlsx</a></button>
                        </div>
                        <hr/>
                        <div class="form-group">
                            <label for="importUserDataXlsx">包含用户信息的文件</label>
                            <input name="importUserDataXlsx" accept=".xlsx" type="file" id="importUserDataXlsx">
                        </div>
                        <div class="form-group">
                            <label for="targetRadios">导入目标</label>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="target" id="targetRadios" value="S" checked>学生
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="target" id="targetRadios" value="T">教师
                                </label>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                    <div class="box-footer">
                        <button id="importUserDataBtn" type="button" class="btn btn-warning">立即导入</button>
                    </div>
                </form>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <#include "../../common/admin/copyright.ftl" />
</div>
<!-- ./wrapper -->
<#assign restFooter>
    <script>
        $(function () {
            $('#importUserDataBtn').on('click', () => {
                const formData = new FormData($('#importUserDataForm')[0]);
                const file = formData.get('importUserDataXlsx');
                if (NS.isNull(file) || file.name.length <= 5 || file.size === 0 || NS.isNull(formData.get('target'))) {
                    xtip.msg('请确保导入数据的正确性！', {icon: 'w'});
                    return;
                }
                const importIng = xtip.load('数据导入中...');
                NS.postFile('/teachingSecretary/importUserData/uploadXlsxAndImpostUserData', formData
                    , (res) => {
                        if (res.code === 1000) {
                            xtip.msg('导入成功！', {icon: 's'});
                        } else if (res.code === 1001) {
                            xtip.confirm('部分数据导入失败，您可以点击"确定"来下载失败的数据信息列表。之后您可以选择在数据修正后重新导入用户信息！', () => {
                                NS.to(NS.getCommonFileUrl(res.data.url));
                            }, {icon: 'w'});
                        } else {
                            xtip.msg('系统出错，导入失败！', {icon: 'e'});
                        }
                        xtip.close(importIng);
                    });
            });
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />
