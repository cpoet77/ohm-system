<#-- 提示模板页面 -->
<#-- nsleaf 2020.05.07 www.nsleaf.cn -->
<#import "../function/spring.ftl" as spring />
<#assign pageTitle><@spring.message tipsTitle /></#assign>
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
                <li class="active">tips</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="error-page">
                <h2 class="headline <#if tipsType == "y">text-yellow<#elseif tipsType == "e">text-red<#else>text-green</#if>">
                    <#if tipsType == "y"><@spring.message "dict.tips.warning" />
                    <#elseif tipsType == "e"><@spring.message "dict.tips.error" />
                    <#else><@spring.message "dict.common.tips" /></#if>
                </h2>
                <div class="error-content">
                    <h3>
                        <i class="fa <#if tipsType == "y"> fa-warning text-yellow<#elseif tipsType == "e">fa-bug text-red<#else>fa-check text-green</#if>"></i> ${pageTitle}
                    </h3>
                    <p>
                        <@spring.message tipsContent />
                    </p>
                    <#if tipsJump??>
                        <p>
                            <@spring.message "dict.tips.waitTips" />...
                        <p/>
                    </#if>
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
<#assign restFooter>
    <#if tipsJump??>
        <script>
            let tipsJumpSecond = ${tipsJumpSecond!3};
            const tipsJumpSecondObj = $('#tipsJumpSecond');
            window.setInterval(() => {
                if (tipsJumpSecond <= 0) {
                    NS.to('${tipsJump}');
                    return;
                }
                tipsJumpSecondObj.text(tipsJumpSecond--);
            }, 1000);
        </script>
    </#if>
</#assign>
<#include "../common/footer.ftl" />
