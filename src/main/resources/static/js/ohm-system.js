//
// Provide proprietary object and function methods for the ohms project
// project ohms-system
// version 1.0.0
// author nsleaf
// 2020.05.06
// www.nsleaf.cn
//
(() => {
    window.NS = {
        /**
         * js组件名
         */
        name: 'ohms-system.js',
        /**
         * 组件版本
         */
        version: '1.0.0',
        /**
         * 默认语言
         */
        language: 'en',
        /**
         * language内容，从指定语言的语言包中获取
         */
        message: {},

        /**
         *  textboxio编辑器全局默认配置
         */
        textboxioConfig: (() => {
            return ({});
        })(),

        /**
         * 与后端交换api地址，包含post和get请求
         */
        api: {
            /**
             * 上传普通文件地址
             */
            uploadCommonFileUrl: '/file/upload/common',
            /**
             * 上传受保护的文件地址
             */
            uploadSentinelResourceUrl: '/file/upload/sentinel',
            /**
             * 普通文件访问基地址
             */
            baseCommonFileUrl: '/file/common',
            /**
             * 受保护的文件访问基地址
             */
            baseSentinelResourceUrl: '/file/resource',

            /**
             * 删除文件资源
             */
            deleteFileUrl: '/file/deleteFile'
        },
        /**
         * 发起post请求
         * 通过.then(success(res),fail())进行结果处理
         * @param url url地址
         * @param data 发送的数据
         * @param success 请求成功处理
         * @param fail 自定义请求失败处理
         * @param contentType 请求的contentType,默认为application/x-www-form-urlencoded; charset=UTF-8
         * @returns {*} ajax request
         */
        post: function (url, data, success, fail, contentType) {
            return $.ajax({
                type: 'POST',
                url: url,
                data: data,
                contentType: NS.isNull(contentType) ? 'application/x-www-form-urlencoded; charset=UTF-8' : contentType,
                dataType: 'json',
                success: success,
                fail: !NS.isNull(fail) ? fail : () => {
                    xtip.closeAll();
                    xtip.msg(NS.message.tips.operationFailed, {times: 3000, icon: 'e'});
                }
            });
        },

        /**
         * post请求上传文件
         * @param url
         * @param data
         * @param success
         * @param fail
         * @returns {*}
         */
        postFile: function (url, data, success, fail) {
            return $.ajax({
                type: 'POST',
                url: url,
                data: data,
                processData: false,
                contentType: false,
                dataType: 'json',
                success: success,
                fail: !NS.isNull(fail) ? fail : () => {
                    xtip.closeAll();
                    xtip.msg(NS.message.tips.operationFailed, {times: 3000, icon: 'e'});
                }
            });
        },

        /**
         * 生成具有唯一性的uuid
         *
         * @returns {string}
         */
        uuid: function () {
            let d = new Date().getTime();
            if (window.performance && typeof window.performance.now === "function") {
                d += performance.now();
            }
            return 'xxxxxxxxxxxx943xyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                const r = (d + Math.random() * 16) % 16 | 0;
                d = Math.floor(d / 16);
                return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
            });
        },

        /**
         * 判断对象是否未定义或者为null
         * @param obj 任意值
         * @returns {boolean} true|false
         */
        isNull: function (obj) {
            return (obj == null || typeof (obj) == 'undefined');
        },

        /**
         * 查询url中的参数
         * @param variable 参数名
         * @returns {string|null}
         */
        getQueryVariable: function (variable) {
            const query = window.location.search.substring(1);
            const vars = query.split("&");
            for (let i = 0; i < vars.length; i++) {
                let pair = vars[i].split("=");
                if (pair[0] === variable) {
                    return pair[1];
                }
            }
            return null;
        },

        /**
         * 实施页面跳转
         * @param url 地址
         * @param times 定时跳转
         */
        to: function (url = '/', times = 0) {
            if (times <= 0) {
                window.location.href = url;
            } else {
                setTimeout(() => {
                    window.location.href = url;
                }, times);
            }
        },

        /**
         * 刷新页面
         */
        reload: function () {
            window.location.reload();
        },

        /**
         * 获取普通文件访问url地址,需要传入后端返回的file path
         * @param path
         * @returns {*}
         */
        getCommonFileUrl: function (path) {
            return (NS.isNull(path) ? '' : NS.api.baseCommonFileUrl + "/" + path);
        },

        /**
         * 获取受保护文件的url地址，需传入resource id, name以及fix
         * @param id
         * @param name
         * @param fix
         * @returns {string}
         */
        getSentinelResourceUrl: function (id, name, fix) {
            return ((NS.isNull(id) || NS.isNull(name) || NS.isNull(fix)) ? '' : NS.api.baseSentinelResourceUrl
                + "/" + id + "/" + name + fix);
        },

        /**
         * 获取受保护文件的url地址，传入上传成功后的返回的resource
         *
         * @param resource
         * @returns {string}
         */
        getSentinelResourceUrlI: function (resource) {
            return (NS.getSentinelResourceUrl(resource.id, resource.name, resource.suffix));
        },

        /**
         * 退出登录
         * 前提条件是当前用户已经登录，否则不可访问
         */
        logout: function () {
            xtip.confirm('确定要注销登录吗？', () => {
                NS.post("/login/doLogout", '', () => {
                    NS.reload();
                });
            }, {icon: 'a'});
        }
    };
    (() => {/* 阻止回车提交表单 */
        document.onkeydown = function (event) {
            let target, code, tag;
            if (!event) {
                event = window.event;
                target = event.srcElement;
                code = event.keyCode;
                if (code === 13) {
                    tag = target.tagName;
                    return tag === "TEXTAREA";
                }
            } else {
                target = event.target;
                code = event.keyCode;
                if (code === 13) {
                    tag = target.tagName;
                    return tag !== "INPUT";
                }
            }
        }
    })();
})();
