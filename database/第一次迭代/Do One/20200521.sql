--
-- 第一次迭代 Do One 数据库sql语句
-- nsleaf
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
    skin      VARCHAR(15)  NOT NULL DEFAULT 'purple' COMMENT '界面皮肤',
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
    major_id   INT NOT NULL COMMENT '专业id',
    FOREIGN KEY (user_id) REFERENCES ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (major_id) REFERENCES ohms_major (id) ON DELETE CASCADE ON UPDATE CASCADE,
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
    FOREIGN KEY (user_id) REFERENCES ohms_user (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT '用户登录记录';


--
-- 视图
--

-- 教师
CREATE VIEW ohms_view_teacher AS
SELECT u.*, t.teacher_id
FROM ohms_user u,
     ohms_teacher t
WHERE t.user_id = u.id;

-- 学生
CREATE VIEW ohms_view_student AS
SELECT u.*, s.student_id, s.major_id
FROM ohms_user u,
     ohms_student s
WHERE u.id = s.user_id;

--
-- 初始化数据
--
INSERT INTO ohms_role(name, description)
VALUES ('admin', '超级管理员'),
       ('teachingSecretary', '教学秘书'),
       ('teacher', '教师'),
       ('student', '学生');

INSERT INTO ohms_user(name, real_name, password, salt)
VALUES ('gzmu-201742010122', '王国富', '3a9b59d90b4c3a62aa12bcb1a495de17', '48bcaffd039640b4aa63a86a6d0883da');

INSERT INTO ohms_teacher(teacher_id, user_id)
VALUES ('201742010122', 1);

INSERT INTO ohms_user_role(user_id, role_id)
VALUES (1, 1),
       (1, 2);
