<#-- 登录 -->
<#assign pageTitle>登录</#assign>
<#assign bodyClass>hold-transition login-page</#assign>
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
    <link rel="stylesheet" href="/static/plugins/xtiper-plugins/css/xtiper.css">
    <link rel="stylesheet" href="/static/plugins/slideunlock/slide-unlock.css">
</#assign>
<#include "common/head.ftl" />
<div class="login-box">
    <div class="login-logo">
        <a href="/">${siteNameFormat!""}</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>

        <form method="post">
            <div class="form-group has-feedback">
                <input name="uname" type="text" class="form-control"
                       placeholder="用户名">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="password" type="password" class="form-control"
                       placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row form-group has-feedback">
                <div id="slider">
                    <div id="slider_bg"></div>
                    <span id="label">>></span>
                    <span id="labelTip">拖动滑块验证</span>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <button id="submitBtn" type="button"
                            class="btn btn-primary btn-block btn-flat">登&nbsp;&nbsp;录
                    </button>
                </div>
            </div>
        </form>
        <!-- /.social-auth-links -->
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<#assign restFooter>
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/xtiper-plugins/js/xtiper.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/zh.js"></script>
    <script src="/static/plugins/slideunlock/slideunlock.js"></script>
    <script>
        /***********************************************************/
        /*********** nsleaf www.nsleaf.cn 2020.05.05 **************/
        /***********************************************************/
        $(() => {
            const form = $('form');
            form.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    uname: {
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空'
                            },
                            stringLength: {
                                min: 8,
                                max: 32,
                                message: '用户名长度不符合规定'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]{6,20}$/,
                                message: '密码为字母、数字以及_的6-20位字符'
                            }
                        }
                    }
                }
            });
            const sliderUnlock = new SliderUnlock('#slider', {successLabelTip: '验证通过'}, () => {
            });
            sliderUnlock.init();
            /* 绑定点击事件 */
            $('#submitBtn').on('click', () => {
                const bootstrapValidator = form.data('bootstrapValidator');
                if (!sliderUnlock.isOk) {
                    xtip.msg("请先拖动滑块在进行登录！");
                    return;
                }
                if (bootstrapValidator.validate().isValid()) {
                    const loginLoad = xtip.load('登录中...');
                    NS.post('/login/finishLogin', form.serializeArray(), (res) => {
                        if (res.code === 1000) {
                            xtip.msg('登录成功', {icon: 's'});
                            NS.to('${backUrl}', 2000);
                        } else {
                            xtip.msg('登录失败！！', {icon: 'e'});
                        }
                        xtip.close(loginLoad);
                    });
                }
            });
        })
    </script>
</#assign>
<#include "common/footer.ftl" />
