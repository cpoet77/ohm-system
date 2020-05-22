<#assign nowYear = .now?string("yyyy") />
<#assign createYear = appInfo("ohms.create.year") />
<footer class="main-footer">
    <strong> 版权所有 &copy; <#if createYear == nowYear>${createYear}<#else>${createYear} - ${nowYear}</#if>
        <a href="/">${OHMS_NAME}&nbsp;Version${OHMS_VERSION}</a>&nbsp;
    </strong>
    保留所有权利。
</footer>
