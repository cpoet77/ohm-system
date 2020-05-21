<#-- 登录 -->
<#assign pageTitle>登录</#assign>
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
                <input name="doId" type="text" class="form-control"
                       placeholder="学号">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="password" type="password" class="form-control"
                       placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
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
    <script>
        /***********************************************************/
        /*********** nsleaf www.nsleaf.cn 2020.05.05 **************/
        /***********************************************************/
        $(() => {
            const registerForm = $('form');
            registerForm.bootstrapValidator({
                verbose: false,     /* 对field内的条件按顺序验证 */
                message: '数据校验失败',
                fields: {
                    doId: {
                        validators: {
                            notEmpty: {
                                message: '填写不不正确'
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
            /* 绑定点击事件 */
            $('#submitBtn').on('click', () => {
                const bootstrapValidator = registerForm.data('bootstrapValidator');
                if (bootstrapValidator.validate().isValid()) {
                    const loginLoad = xtip.load('登录中...');
                    NS.post('/login/finishLogin', registerForm.serializeArray(), (res) => {
                        if (res.code === 1000) {
                            xtip.msg('登录成功', {icon: 's'});
                            NS.to('${backUrl}', 2000);
                        } else {
                            xtip.msg('登录失败！！', {icon: 'e'});
                        }
                        xtip.close(loginLoad);
                    });
                }
            })
        })
    </script>
</#assign>
<#include "common/footer.ftl" />
