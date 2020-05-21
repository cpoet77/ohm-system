/**
 *
 * @author _struggler
 * 2020/05/05
 *
 */

/**
 * 立即函数（匿名函数）
 * 避免其成为window的子属性，
 * 其他人可在浏览器开发工具中获取我们定义的校验对象
 */

$(() => {
    const registerForm = $('form');
    registerForm.bootstrapValidator({
        verbose: false,
        message: '数据校验失败',
        fields: {
            teacherId: {
                validators: {
                    notEmpty: {
                        message: '教师工号不能为空'
                    },
                    stringLength: {
                        max: 24,
                        min: 6,
                        message: '教师工号必须在6到24位之间',
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '教师工号必须是数字',
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-]+$/,
                        message: '请输入正确的邮箱地址如：123@qq.com'
                    }
                },

            },
            username: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空',
                    },
                    stringLength: {
                        max: 12,
                        min: 2,
                        message: '用户名必须在2到12位之间'
                    },
                    threshold: 2,
                    remote: {
                        type: 'post',
                        url: '/user/containUserName',
                        message: '用户名已存在'
                    }

                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空',
                    },
                    stringLength: {
                        max: 32,
                        min: 6,
                        message: '密码必须在6到32位之间',

                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '密码只能包含大写、小写、数字',

                    }
                }
            },
            rePassword: {
                validators: {
                    notEmpty: {
                        message: '请确认密码，不能为空'
                    },
                    identical: {
                        field: 'password',
                        message: '两次输入密码不匹配'
                    }
                }
            },
            'agree[]': {
                validators: {
                    notEmpty: {
                        message: '请认真阅读内容并勾选左边的选框'
                    }
                }
            }
        }
    })

        /* 解决在默认情况下，bootstrapValidator不能对iCheck选框验证的问题 */
        .find('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    }).on('ifChanged', (e) => {
        /* 对目标表单进行重新验证，e.target.name获取标签的name属性值 */
        registerForm.bootstrapValidator('revalidateField', e.target.name);
    });
    $('#submitBtn').on('click', () => {
        const bootstrapValidator = registerForm.data('bootstrapValidator');
        const loginLoad = xtip.load('登录中...');
        if (bootstrapValidator.validate().isValid()) {
            $.ajax({
                method: 'post',
                url: '/register/finish/student',
                data: registerForm.serializeArray(),
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                success: (res) => {
                    if (res.code === 1000) {
                        xtip.confirm('恭喜你，注册成功！<br /> 是否立即前往登录页面？', () => {
                            window.location.href = '/login';
                        }, {icon: 's'});

                    } else {
                        xtip.confirm('注册失败！！！', () => {
                            console.log('2222');
                        }, {icon: 'e'});
                    }
                },
                fail: () => {
                },
            });
        } else {
            xtip.close(loginLoad);
        }
    });
})
;


