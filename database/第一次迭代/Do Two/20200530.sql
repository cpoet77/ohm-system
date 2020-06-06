--
-- 第一次迭代 Do Two 数据库sql语句
-- nsleaf
-- 2020.03.30
--

# DROP DATABASE IF EXISTS ohm_subsystem;

CREATE DATABASE IF NOT EXISTS ohm_subsystem CHAR SET 'UTF8';

USE ohm_subsystem;

CREATE TABLE IF NOT EXISTS ohms_role
(
    id          TINYINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色id',
    name        NVARCHAR(25) NOT NULL COMMENT '用户名',
    description NVARCHAR(64) NULL COMMENT '角色描述',
    datetime    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加角色时间',
    UNIQUE (name),
    CHECK ( LENGTH(name) > 2 )
) ENGINE = InnoDB COMMENT '用户角色表';

CREATE TABLE IF NOT EXISTS ohms_user
(
    id        INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户id',
    name      VARCHAR(48)  NOT NULL COMMENT '用户名',
    real_name NVARCHAR(5)  NOT NULL COMMENT '姓名',
    password  CHAR(32)     NOT NULL COMMENT '加密过的密码',
    salt      CHAR(32)     NOT NULL COMMENT '加密用到的盐',
    sex       CHAR(1)      NOT NULL DEFAULT 'M' COMMENT '用户性别',
    avatar    VARCHAR(128) NULL COMMENT '用户头像地址',
    email     VARCHAR(78)  NULL COMMENT '用户邮箱',
    phone     VARCHAR(15)  NULL COMMENT '用户手机',
    skin      VARCHAR(15)  NOT NULL DEFAULT 'green' COMMENT '界面皮肤',
    UNIQUE (name),
    CHECK ( LENGTH(name) > 3 ),
    CHECK ( LENGTH(name) > 1 ),
    CHECK ( LENGTH(password) = 32 ),
    CHECK ( LENGTH(salt) = 32 ),
    CHECK ( sex = 'M' OR sex = 'F' )
) ENGINE = InnoDB COMMENT '用户表';

CREATE TABLE IF NOT EXISTS ohms_user_role
(
    user_id  INT      NOT NULL COMMENT '用户id',
    role_id  TINYINT  NOT NULL COMMENT '角色id',
    datetime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '授于角色的时间',
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES ohms_role (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT '用户权限中间表';

CREATE TABLE IF NOT EXISTS ohms_college
(
    id          INT PRIMARY KEY AUTO_INCREMENT COMMENT '学院id',
    name        NVARCHAR(64) NOT NULL COMMENT '学院名',
    description TEXT         NULL COMMENT '学院描述',
    datetime    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导入时间',
    UNIQUE (name),
    CHECK ( LENGTH(name) > 2 )
) ENGINE = InnoDB COMMENT '学院表';

CREATE TABLE IF NOT EXISTS ohms_major
(
    id         INT PRIMARY KEY AUTO_INCREMENT COMMENT '专业id',
    name       NVARCHAR(64) NOT NULL COMMENT '专业名',
    college_id INT          NOT NULL COMMENT '学院id',
    datetime   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导入时间',
    FOREIGN KEY (college_id) REFERENCES ohms_college (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (name),
    CHECK ( LENGTH(name) > 4 )
) ENGINE = InnoDB COMMENT '专业表';

CREATE TABLE IF NOT EXISTS ohms_class
(
    id       INT PRIMARY KEY AUTO_INCREMENT COMMENT '班级id',
    name     NVARCHAR(45) NOT NULL COMMENT '名称',
    major_id INT          NOT NULL COMMENT '专业id',
    datetime DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导入时间',
    FOREIGN KEY (major_id) REFERENCES ohms_major (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK ( LENGTH(name) > 0 )
) ENGINE = InnoDB COMMENT '班级表';

CREATE TABLE IF NOT EXISTS ohms_teacher
(
    teacher_id VARCHAR(24) PRIMARY KEY COMMENT '教职工号',
    user_id    INT NOT NULL COMMENT '用户id',
    FOREIGN KEY (user_id) REFERENCES ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT '老师表';

CREATE TABLE IF NOT EXISTS ohms_course_group
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课群id',
    teacher_id  VARCHAR(24)   NOT NULL COMMENT '教职工号',
    name        NVARCHAR(64)  NOT NULL COMMENT '课群名',
    description NVARCHAR(255) NULL COMMENT '课群介绍',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    state       BOOLEAN       NOT NULL DEFAULT TRUE COMMENT '课群状态',
    FOREIGN KEY (teacher_id) REFERENCES ohms_teacher (teacher_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK ( LENGTH(name) > 5 )
) ENGINE = InnoDB COMMENT '课群表';

CREATE TABLE IF NOT EXISTS ohms_student
(
    student_id CHAR(12) PRIMARY KEY COMMENT '学号',
    user_id    INT NOT NULL COMMENT '用户id',
    class_id   INT NOT NULL COMMENT '专业id',
    FOREIGN KEY (user_id) REFERENCES ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (class_id) REFERENCES ohms_class (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK ( LENGTH(student_id) = 12 )
) ENGINE = InnoDB COMMENT '学生表';

CREATE TABLE IF NOT EXISTS ohms_student_course_group
(
    student_id      VARCHAR(12) NOT NULL COMMENT '学号',
    course_group_id BIGINT      NOT NULL COMMENT '课群id',
    join_time       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '学生加入的时间',
    PRIMARY KEY (student_id, course_group_id),
    FOREIGN KEY (student_id) REFERENCES ohms_student (student_id),
    FOREIGN KEY (course_group_id) REFERENCES ohms_course_group (id)
) ENGINE = InnoDB COMMENT '学生加入的课群';

CREATE TABLE IF NOT EXISTS ohms_login_record
(
    id            INT PRIMARY KEY AUTO_INCREMENT COMMENT '登录记录id',
    user_id       INT          NOT NULL COMMENT '用户id',
    login_ip      VARCHAR(33)  NOT NULL COMMENT '登录的ip',
    province      VARCHAR(30)  NULL COMMENT '登录的省',
    province_code VARCHAR(6)   NULL COMMENT '省代码',
    city          VARCHAR(50)  NULL COMMENT '登录的城市',
    city_code     VARCHAR(6)   NULL COMMENT '城市代码',
    address       VARCHAR(98)  NULL COMMENT '登录的地址',
    agent         VARCHAR(255) NULL COMMENT 'user-agent',
    datetime      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    FOREIGN KEY (user_id) REFERENCES ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT '用户登录记录';


-- 创建站内消息记录表
create table if not exists ohms_messagebox
(
    id        int auto_increment primary key comment '记录id',
    title     nvarchar(45) not null comment '标题',
    content   text         null comment '内容',
    send_time datetime     not null default current_timestamp comment '发送时间',
    check ( length(title) > 0)
) engine = innodb comment '站内消息记录表';


-- 创建站内消息中间表
create table if not exists ohms_user_messagebox
(
    user_id    int     not null comment '用户id',
    message_id int     not null comment '消息id',
    is_read    boolean not null default false comment '是否已读',
    primary key (user_id, message_id),
    foreign key (user_id) references ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (message_id) references ohms_messagebox (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT '站内消息中间表';

-- 创建资源记录表
create table if not exists ohms_resource
(
    id        char(32) comment 'UUID',
    user_id   int          null comment '上传用户id',
    name      nvarchar(98) not null comment '资源名',
    suffix    varchar(12)  not null comment '后缀',
    datetime  datetime     not null default CURRENT_TIMESTAMP comment '上传时间',
    publicity boolean      not null default false comment '是否公开',
    path      varchar(128) not null comment '文件存储的path',
    primary key (id),
    check ( length(id) = 32 ),
    foreign key (user_id) references ohms_user (id) on delete set null on update cascade
) engine = innoDB comment '资源记录表';


-- 创建作业表
create table if not exists ohms_homework
(
    id              int AUTO_INCREMENT comment '作业id',
    course_group_id bigint comment '课群id',
    title           nvarchar(64) not null comment '作业名',
    content         text         null comment '内容',
    start_time      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP comment '开始时间',
    end_time        datetime     NULL comment '截止时间',
    state           BOOLEAN      NOT NULL DEFAULT TRUE COMMENT '是否已关闭',
    primary key (id),
    foreign key (course_group_id) references ohms_course_group (id) on delete cascade on update cascade
) engine = innoDB comment '作业表';

-- 创建学生已上传作业表
create table if not exists ohms_push_homework
(
    id          int auto_increment primary key comment 'ID',
    homework_id int      not null comment '作业id',
    student_id  CHAR(12) not null comment '学号',
    push_time   datetime not null default current_timestamp comment '上传时间',
    text        text     null comment '作业描述',
    foreign key (homework_id) references ohms_homework (id) on delete cascade on update cascade,
    foreign key (student_id) references ohms_student (student_id) on delete cascade on update cascade
) engine innoDB comment '学生已上传的作业';

-- 创建作业资源中间表
create table if not exists ohms_homework_resource
(
    homework_id int      not null comment '作业id',
    resource_id char(32) not null comment '资源id',
    primary key (homework_id, resource_id),
    foreign key (homework_id) references ohms_homework (id) on delete cascade on update cascade,
    foreign key (resource_id) references ohms_resource (id) on delete cascade on update cascade
) engine innoDB comment '作业资源中间表';


-- 创建已上传作业资源中间表
create table if not exists ohms_push_homework_resource
(
    push_homework_id int      not null comment '作业id',
    resource_id      char(32) not null comment '资源id',
    primary key (push_homework_id, resource_id),
    foreign key (push_homework_id) references ohms_push_homework (id) on delete cascade on update cascade,
    foreign key (resource_id) references ohms_resource (id) on delete cascade on update cascade
) engine = innoDB comment '已上传作业资源中间表';


--
-- 视图
--

-- 专业视图
CREATE VIEW ohms_view_major AS
SELECT om.*, oc.name college_name
FROM ohms_college oc,
     ohms_major om
WHERE oc.id = om.college_id;

-- 班级视图
CREATE VIEW ohms_view_class AS
SELECT oc.*, ovm.name major_name, ovm.college_id, ovm.college_name
FROM ohms_class oc,
     ohms_view_major ovm
WHERE oc.major_id = ovm.id;

-- 学生视图
CREATE VIEW ohms_view_student AS
SELECT os.*, ovc.name class_name, ovc.major_id, ovc.major_name, ovc.college_id, ovc.college_name
FROM ohms_student os,
     ohms_view_class ovc
WHERE os.class_id = ovc.id;

--
-- 初始化数据
--
INSERT INTO ohms_role(name, description)
VALUES ('admin', '超级管理员'),
       ('teachingSecretary', '教学秘书'),
       ('teacher', '教师'),
       ('student', '学生');

-- 注意：默认密码为12345678
INSERT INTO ohms_user(name, real_name, password, salt, sex)
VALUES ('admin12340', '王国富', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'M'),
       ('admin12341', '徐旭峰', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'M'),
       ('admin12342', '汪海浪', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'M'),
       ('admin12343', '程桥凤', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'F'),
       ('admin12344', '李仁材', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'M'),
       ('admin12345', '尚宏程', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da', 'M');

INSERT INTO ohms_teacher(teacher_id, user_id)
VALUES ('110110110110', 1),
       ('110110110111', 2),
       ('110110110112', 3),
       ('110110110113', 4),
       ('110110110114', 5),
       ('110110110115', 6);

INSERT INTO ohms_user_role(user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1);
