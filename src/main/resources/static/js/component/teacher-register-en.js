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
        message: 'Data verification failed',
        fields: {
            teacherId: {
                validators: {
                    notEmpty: {
                        message: 'teacher ID cannot be empty'
                    },
                    stringLength: {
                        max: 24,
                        min: 6,
                        message: 'Teacher ID must be between 6 and 24',
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: 'Teacher ID must be a number',
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Emai cannot be empty'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-]+$/,
                        message: 'Please enter the correct email address such as 123@qq.com'
                    }
                },

            },
            username: {
                validators: {
                    notEmpty: {
                        message: 'username cannot be empty',
                    },
                    stringLength: {
                        max: 12,
                        min: 2,
                        message: 'username must be between 2 and 12'
                    }

                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'password cannot be empty',
                    },
                    stringLength: {
                        max: 32,
                        min: 6,
                        message: 'password must be between 6 and 32',

                    },
                    regexp: {
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: 'Password can only contain uppercase, lowercase and number',
                        }
                    }
                }
            },
            rePassword: {
                validators: {
                    notEmpty: {
                        message: 'Please confirm the password. It cannot be empty'
                    },
                    identical: {
                        field: 'password',
                        message: 'Two input passwords do not match'
                    }
                }
            }
        }
    })
    //提交
    registerForm.submit(() => {
        let b = registerForm.data('bootstrapValidator');
        let input = $('input');
        let data = {};

        alert("校验中！！");
        //遍历存储input值
        for (let i = 0; i < input.length; ++i) {
            data[input[i].name] = input[i].value;
        }
        console.log(data);
        //判断校验是否完成,完成后传递表单值到后端进行校验
        if (b.isValid()) {
            $.ajax({
                method: 'post',
                url: 'register/finish/teacher',
                data: data,//$('#basic_form').serialize()
                success: (e) => {
                    if (e.code === 1000) {
                        alert("register was successful！！");
                        console.log(e.msg);
                    } else {
                        alert("register was failed！！");
                        console.log(e.msg);
                    }

                },
            });
            return false;

        }
        console.log('Verification failed！！');
        return false;
    });
})