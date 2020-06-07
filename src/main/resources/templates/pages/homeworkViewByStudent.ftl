<#-- 课群作业 -->
<#assign pageTitle>${courseGroup.courseGroupName!""}|我的课群</#assign>
<#include "../common/head.ftl" />
<div class="wrapper">
    <#include "../common/header.ftl"/>
    <div class="content-wrapper">
        <div class="container">
            <section class="content-header">
                <h1>
                    作业管理
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i>首页</a></li>
                    <li><a href="/myCourseGroup">我的课群</a></li>
                    <li class="active">${courseGroup.courseGroupName!""}</li>
                </ol>
            </section>
            <section class="content" id="main">
                <div class="box-header with-border">
                    <div class="info-box bg-blue">
                        <span class="info-box-icon"><i class="ion ion-ios-heart-outline"></i></span>
                        <div class="info-box-content">
                            <h3>${courseGroup.courseGroupName!""}</h3>
                            <br/>
                            <h4>学生人数：${courseGroup.countStudent!0}</h4>
                        </div>
                    </div>
                </div>
                <div class="box-body">
                    <div class="box box-solid">
                        <div class="box-header with-border">
                            <form>
                                <div class="input-group input-group-sm">
                                    <input v-model="filterData.name" type="text" class="form-control"
                                           placeholder="搜索作业">
                                    <span class="input-group-btn">
                            <button id="searchHomeworkBtn" type="button" class="btn btn-info btn-flat">
                                <span style="vertical-align: inherit;">
                                    <span class="fa fa-search" style="vertical-align: inherit;"></span>
                                </span>
                            </button>
                            </span>
                                </div>
                                <br/>
                                <div class="form-group">
                                    <label>
                                        <input v-model="filterData.state" type="radio" name="optionsRadios"
                                               id="optionsRadios1" value="0"
                                               checked="">全部作业
                                    </label>
                                    <label>
                                        <input v-model="filterData.state" type="radio" name="optionsRadios"
                                               id="optionsRadios2" value="1">进行中
                                    </label>
                                    <label>
                                        <input v-model="filterData.state" type="radio" name="optionsRadios"
                                               id="optionsRadios3" value="2">已结束
                                    </label>
                                </div>
                            </form>
                        </div>
                        <div class="box-body">
                            <div type="button" v-on:click="toHomeworkView(homework.id)"
                                 class="btn btn-block btn-social btn-default btn-lg"
                                 v-for="homework in homeworkList">
                                <i class="fa fa-file-text-o" style="width: 60px"></i>
                                <div>
                                    <span class="box box-info box-solid"
                                          style="font-size: 15px; color: #35ee29" v-if="homework.state">进行中</span>
                                    <span class="box box-info box-solid" style="font-size: 15px; color: #ee062c" v-else>已结束</span>
                                    <span style="font-size: 15px;">{{homework.title}}</span>
                                </div>
                                <div style="float:left;font-size:12px;">
                                    <span>共 </span>
                                    <span>{{homework.countPush}}/{{homework.countStudent}}</span>
                                    <span>人参与</span>
                                    <span>|</span>
                                    <span>{{homework.startTime}}</span>
                                    <span>|</span>
                                    <span class="fa fa-hourglass-half" style="color: #FF8A00; font-size:12px">剩余 {{homework.lastDay}} 天 {{homework.lastHour}} 小时 {{homework.lastMin}} 分钟</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <#include "../common/copyright.ftl"/>
</div>
<#assign restFooter>
    <script>
        $(function () {
            const Main = new Vue({
                el: '#main',
                data: {
                    page: 0,
                    size: 10,
                    loadLock: false,
                    filterData: {
                        name: null,
                        state: 0
                    },
                    homeworkList: []
                },
                methods: {
                    loadHomeworkList: function () {
                        NS.post('/homework/homeworkList', {
                            courseGroupId: ${courseGroup.id},
                            page: this.page,
                            size: this.size,
                            name: this.filterData.name,
                            state: this.filterData.state
                        }, (res) => {
                            if (res.code === 1000) {
                                if (res.data.size === 0) {
                                    xtip.msg('没有数据了...');
                                } else {
                                    ++this.page;
                                    this.homeworkList = this.homeworkList.concat(res.data.list);
                                }
                            } else {
                                xtip.msg('获取数据失败！!');
                            }
                            this.loadLock = true;
                        })
                    },
                    reloadHomeworkList: function () {
                        this.page = 0;
                        this.homeworkList = [];
                        this.loadHomeworkList();
                    },
                    toHomeworkView: function (homeworkId) {
                        if (NS.isNull(homeworkId)) {
                            return;
                        }
                        NS.to('/pushHomework?courseGroup=${courseGroup.id}&homework=' + homeworkId);
                    }
                },
                created: function () {
                    this.loadHomeworkList();
                },
                watch: {
                    'filterData.state': function (newV, oldV) {
                        this.reloadHomeworkList();
                    }
                }
            });
            window.addEventListener('scroll', () => {/* 监听滚动事件 */
                let scrollTop = $(window).scrollTop();
                let scrollHeight = $(document).height();
                let windowHeight = $(window).height();
                if (scrollTop + windowHeight + 5 >= scrollHeight && Main.loadLock) {
                    Main.loadLock = false;
                    Main.loadHomeworkList();
                }
            });
            $('#searchHomeworkBtn').on('click', () => {
                Main.reloadHomeworkList();
            });
        });
    </script>
</#assign>
<#include "../common/footer.ftl"/>
