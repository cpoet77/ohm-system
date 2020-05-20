<#-- 登录 -->
<#import "function/spring.ftl" as spring />
<#assign pageTitle><@spring.message "dict.common.signIn" /></#assign>
<#assign bodyClass>hold-transition login-page</#assign>
<#assign restHead>
    <link rel="stylesheet" href="/static/plugins/bootstrapvalidator/bootstrapValidator.min.css">
    <link rel="stylesheet" href="/static/plugins/xtiper-plugins/css/xtiper.css">
</#assign>
<#include "common/head.ftl" />
<div class="login-box">
    <div class="login-logo">
        <a href="/">${OHMS_NAME_FORMAT}</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>

        <form>
            <div class="form-group has-feedback">
                <input name="email" type="email" class="form-control"
                       placeholder="<@spring.message "dict.common.email" />">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="password" type="password" class="form-control"
                       placeholder="<@spring.message "dict.user.password" />">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <button id="submitBtn" type="button"
                            class="btn btn-primary btn-block btn-flat"><@spring.message "dict.common.signIn" /></button>
                </div>
                <div class="col-xs-4"></div>
                <div class="col-xs-4">
                    <button type="button" onclick="NS.to('/register')"
                            class="btn btn-primary btn-block btn-flat"><@spring.message "dict.common.signUp" /></button>
                </div>
            </div>
        </form>
        <!-- /.social-auth-links -->
        <br/>
        <a href="#"><@spring.message "dict.common.forgetPass" />?</a>
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<#assign restFooter>
    <script src="/static/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="/static/plugins/xtiper-plugins/js/xtiper.min.js"></script>
    <script src="/static/plugins/bootstrapvalidator/<#if .lang == "zh">zh<#else>en</#if>.js"></script>
    <script>
        /***********************************************************/
        /*********** nsleaf www.nsleaf.cn 2020.05.05 **************/
        /***********************************************************/
        $(() => {
            const registerForm = $('form');
            registerForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '<@spring.message "dict.common.dataVerificationFailed"/>',
                fields: {
                    email: {
                        validators: {
                            notEmpty: {
                                message: '<@spring.message "dict.register.emailNotEmpty"/>'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '<@spring.message "dict.register.passwordNotEmpty"/>'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]{6,20}$/,
                                message: '<@spring.message "dict.register.passwordRegexp"/>'
                            }
                        }
                    }
                }
            });
            /* 绑定点击事件 */
            $('#submitBtn').on('click', () => {
                const bootstrapValidator = registerForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const loginLoad = xtip.load('<@spring.message "dict.login.loggingIn"/>...');
                    NS.post('/login/finishLogin', registerForm.serializeArray(), (res) => {
                        if (res.code === 1000) {
                            xtip.msg('登录成功', {icon: 's'});
                            NS.to('${backUrl}', 2000);
                        } else {
                            xtip.msg('<@spring.message "dict.login.fail"/>', {icon: 'e'});
                        }
                        xtip.close(loginLoad);
                    });
                }
            })
        })
    </script>
</#assign>
<#include "common/footer.ftl" />
