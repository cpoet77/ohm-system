<#-- 全局公共函数和变量定义 -->
<#--variable-->
<#assign is_auth = shiroIsAuth() /><#-- 判断是否验证身份 -->
<#assign u = shiroGetUserInfo() /><#-- 如果登录获取个人信息，否则为一个新的实例化对象 -->
<#assign m_avatar = "/static/images/m_avatar.png" /><#-- 默认头像男 -->
<#assign f_avatar = "/static/images/f_avatar.png" /><#-- 默认头像女 -->
<#assign siteTitle = appInfo("ohms.name") /><#-- 网站名，标题 -->
<#assign siteSubTitle = appInfo("ohms.name.sub") /><#-- 网站副标题 -->
<#assign siteNameFormat = appInfo("ohms.name.format") /><#-- 格式化标题 -->
<#-- function -->
