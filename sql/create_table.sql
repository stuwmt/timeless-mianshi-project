# 数据库初始化

-- 创建库
create database if not exists mianshi;

-- 切换库
use mianshi;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 题库表
create table if not exists question_bank
(
    id          bigint auto_increment comment 'id' primary key,
    title       varchar(256)                       null comment '标题',
    description text                               null comment '描述',
    picture     varchar(2048)                      null comment '图片',
    userId      bigint                             not null comment '创建用户 id',
    editTime    datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_title (title)
) comment '题库' collate = utf8mb4_unicode_ci;
-- 题目表
create table if not exists question
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(256)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    answer     text                               null comment '推荐答案',
    userId     bigint                             not null comment '创建用户 id',
    editTime   datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_title (title),
    index idx_userId (userId)
) comment '题目' collate = utf8mb4_unicode_ci;
-- 题库题目表（硬删除）
create table if not exists question_bank_question
(
    id             bigint auto_increment comment 'id' primary key,
    questionBankId bigint                             not null comment '题库 id',
    questionId     bigint                             not null comment '题目 id',
    userId         bigint                             not null comment '创建用户 id',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    UNIQUE (questionBankId, questionId)
) comment '题库题目' collate = utf8mb4_unicode_ci;


# 测试数据
-- 插入题库表数据
INSERT INTO question_bank (title, description, picture, userId)
VALUES ('题库1', '这是题库1的描述，包含基础数学题目。', NULL, 1898733106816815105),
       ('题库2', '题库2描述：高中物理经典习题集', 'physics.jpg', 1898733106816815105),
       ('题库3', '题库3：化学有机反应专项练习', 'chemistry.png', 1898733106816815105),
       ('题库4', '历史题库：近代史重要事件', NULL, 1898733106816815105),
       ('题库5', '地理题库5：气候与地形分析', 'geo.jpg', 1898733106816815105),
       ('题库6', '生物题库：细胞结构与功能', NULL, 1898733106816815105),
       ('题库7', '英语题库：语法选择题集', 'english.png', 1898733106816815105),
       ('题库8', '编程题库：算法入门习题', NULL, 1898733106816815105),
       ('题库9', '数据结构题库：树与图', 'data_structure.jpg', 1898733106816815105),
       ('题库10', '数学分析题库：极限与连续', NULL, 1898733106816815105);

-- 插入题目表数据
INSERT INTO question (title, content, tags, answer, userId)
VALUES ('题目1', '求二次方程 x² + 2x + 1 = 0 的解', '["数学", "代数"]', '解为 x = -1（重根）', 1898733106816815105),
       ('题目2', '牛顿第一定律的表述是什么？', '["物理", "力学"]',
        '任何物体都保持静止或匀速直线运动状态，除非外力迫使它改变。', 1898733106816815105),
       ('题目3', '苯的化学式是什么？', '["化学", "有机"]', 'C₆H₆', 1898733106816815105),
       ('题目4', '五四运动发生在哪一年？', '["历史"]', '1919年', 1898733106816815105),
       ('题目5', '季风气候的主要特征是什么？', '["地理"]', '夏季高温多雨，冬季寒冷干燥', 1898733106816815105),
       ('题目6', '线粒体的主要功能是什么？', '["生物", "细胞"]', '细胞进行有氧呼吸的主要场所', 1898733106816815105),
       ('题目7', '选择正确的句子：______ going to the park.', '["英语", "语法"]', 'They\'re', 1898733106816815105),
       ('题目8', '用递归实现斐波那契数列', '["编程", "算法"]',
        'def fib(n):\n    return n if n <=1 else fib(n-1)+fib(n-2)', 1898733106816815105),
       ('题目9', '二叉树的前序遍历顺序', '["数据结构"]', '根节点 → 左子树 → 右子树', 1898733106816815105),
       ('题目10', '证明 lim(x→0) sinx/x = 1', '["数学", "分析"]', '利用夹逼定理，详见教材P123', 1898733106816815105);

-- 插入题库题目关系表数据（假设题库和题目ID均从1开始自增）
INSERT INTO question_bank_question (questionBankId, questionId, userId)
VALUES (1, 1, 1898733106816815105), -- 题库1关联题目1
       (2, 2, 1898733106816815105), -- 题库2关联题目2
       (3, 3, 1898733106816815105), -- 题库3关联题目3
       (4, 4, 1898733106816815105), -- 题库4关联题目4
       (5, 5, 1898733106816815105), -- 题库5关联题目5
       (6, 6, 1898733106816815105), -- 题库6关联题目6
       (7, 7, 1898733106816815105), -- 题库7关联题目7
       (8, 8, 1898733106816815105), -- 题库8关联题目8
       (9, 9, 1898733106816815105), -- 题库9关联题目9
       (10, 10, 1898733106816815105); -- 题库10关联题目10