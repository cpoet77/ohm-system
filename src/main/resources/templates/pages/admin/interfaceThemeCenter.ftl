<#-- 界面主题管理 skin更换 -->
<#-- 首页 -->
<#assign activeIndex></#assign>
<#assign pageTitle>更换主题</#assign>
<#assign personalCenter = true />
<#assign themeCenter = true />
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
                主题中心
            </h1>
            <ol class="breadcrumb">
                <li><a href="/teachingSecretary"><i class="fa fa-dashboard"></i>控制台</a></li>
                <li><a href="#">个人中心</a></li>
                <li class="active">主题中心</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <ul class="list-unstyled clearfix">
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('blue')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'blue'>full-opacity-hover</#if>">
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 7px; background: #367fa9;"></span><span
                                    class="bg-light-blue"
                                    style="display:block; width: 80%; float: left; height: 7px;"></span></div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin">Blue</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('black')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'black'>full-opacity-hover</#if>">
                        <div style="box-shadow: 0 0 2px rgba(0,0,0,0.1)" class="clearfix"><span
                                    style="display:block; width: 20%; float: left; height: 7px; background: #fefefe;"></span><span
                                    style="display:block; width: 80%; float: left; height: 7px; background: #fefefe;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #222;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin">Black</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('purple')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'purple'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-purple-active"></span><span class="bg-purple"
                                                                         style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin">Purple</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('green')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'green'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-green-active"></span><span class="bg-green"
                                                                        style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin">Green</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('red')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'red'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-red-active"></span><span class="bg-red"
                                                                      style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin">Red</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('yellow')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'yellow'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-yellow-active"></span><span class="bg-yellow"
                                                                         style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #222d32;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin">Yellow</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('blue-light')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'blue-light'>full-opacity-hover</#if>">
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 7px; background: #367fa9;"></span><span
                                    class="bg-light-blue"
                                    style="display:block; width: 80%; float: left; height: 7px;"></span></div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin" style="font-size: 12px">Blue Light</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('black-light')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'black-light'>full-opacity-hover</#if>">
                        <div style="box-shadow: 0 0 2px rgba(0,0,0,0.1)" class="clearfix"><span
                                    style="display:block; width: 20%; float: left; height: 7px; background: #fefefe;"></span><span
                                    style="display:block; width: 80%; float: left; height: 7px; background: #fefefe;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin" style="font-size: 12px">Black Light</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('purple-light')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'purple-light'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-purple-active"></span><span class="bg-purple"
                                                                         style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin" style="font-size: 12px">Purple Light</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('green-light')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'green-light'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-green-active"></span><span class="bg-green"
                                                                        style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin" style="font-size: 12px">Green Light</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('red-light')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'red-light'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-red-active"></span><span class="bg-red"
                                                                      style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin" style="font-size: 12px">Red Light</p></li>
                <li style="float:left; width: 33.33333%; padding: 5px;" onclick="NS.setSink('yellow-light')"><a
                            href="#"
                            style="display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)"
                            class="clearfix <#if u.skin != 'yellow-light'>full-opacity-hover</#if>">
                        <div><span style="display:block; width: 20%; float: left; height: 7px;"
                                   class="bg-yellow-active"></span><span class="bg-yellow"
                                                                         style="display:block; width: 80%; float: left; height: 7px;"></span>
                        </div>
                        <div>
                            <span style="display:block; width: 20%; float: left; height: 20px; background: #f9fafc;"></span><span
                                    style="display:block; width: 80%; float: left; height: 20px; background: #f4f5f7;"></span>
                        </div>
                    </a>
                    <p class="text-center no-margin" style="font-size: 12px;">Yellow Light</p></li>
            </ul>
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
            const currentUserSkin = '${u.skin}';
            NS.setSink = (skinName) => {
                if (NS.isNull(skinName) || skinName === currentUserSkin) {
                    return;
                }
                const setSinkLoad = xtip.load('设置主题中...');
                NS.post('/user/setSkin', {skinName: skinName}, (res) => {
                    if (res.code === 1000) {
                        NS.reload();/* 设置皮肤成功，重新加载界面 */
                    } else {
                        xtip.msg('界面主题设置失败！');
                    }
                    xtip.close(setSinkLoad);
                });
            }
        });
    </script>
</#assign>
<#include "../../common/footer.ftl" />

