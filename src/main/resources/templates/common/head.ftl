<#-- 全局公共头部 -->
<#include "global.ftl" />
<!DOCTYPE html>
    <html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${pageTitle!siteTitle} | <#if pageSubtitle??>${pageSubtitle}<#elseif siteSubTitle??>${siteSubTitle}<#else>${OHMS_NAME}</#if></title>
    <link rel="icon" sizes="any" href="/static/images/favicon.ico">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/dist/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="/static/dist/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/static/dist/css/skins/all-skins.min.css">
    <link rel="stylesheet" href="/static/plugins/xtiper-plugins/css/xtiper.css">
    ${restHead!""}
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<#if bodyClass??>
<body class="${bodyClass}">
<#else>
<body class="hold-transition skin-${u.skin!"green"} sidebar-mini fixed">
</#if>