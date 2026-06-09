# 养老服务管理系统

基于 Spring Boot 3 + Vue 3 的前后端分离养老服务管理系统，提供养老院信息展示、老人入住管理、账单管理、访客管理等功能。

## 📋 项目简介

本项目是一个综合性的养老服务管理平台，包含前台展示系统和后台管理系统两大部分：

- **前台系统**：养老院信息展示、新闻资讯、公告通知、用户注册登录、入住申请、账单查询等
- **后台系统**：管理员和护工对养老院进行全方位管理，包括用户管理、房间床位管理、入住管理、账单管理、访客管理等

## 🛠️ 技术栈

### 后端

- **Spring Boot 3.0.2** - Web 应用框架
- **Spring Security + JWT** - 认证授权
- **MyBatis 3.0.2** - 持久层框架
- **MySQL 8.0+** - 数据库
- **Druid 1.2.18** - 数据库连接池
- **Redis** - 缓存、分布式锁、接口限流
- **Hutool 5.7.3** - Java 工具库
- **JWT 0.12.5** - JSON Web Token

### 前端

- **Vue 3.2.47** - 前端框架
- **Vue Router 4.2.2** - 路由管理
- **Vuex 4.1.0** - 状态管理
- **Element Plus 2.8.6** - UI 组件库
- **Axios 0.27.2** - HTTP 客户端
- **ECharts 5.3.3** - 图表库

## 📁 项目结构

```
code/
├── elderly-care-server/      # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/elderly/
│   │   │   │       ├── common/         # 通用模块
│   │   │   │       │   ├── config/     # 配置类
│   │   │   │       │   ├── dto/        # 数据传输对象
│   │   │   │       │   ├── exception/  # 异常处理
│   │   │   │       │   └── vo/         # 视图对象
│   │   │   │       ├── controller/     # 控制器
│   │   │   │       ├── entity/         # 实体类
│   │   │   │       ├── mapper/         # MyBatis Mapper
│   │   │   │       ├── service/        # 业务逻辑
│   │   │   │       └── utils/          # 工具类
│   │   │   └── resources/
│   │   │       ├── mapper/             # MyBatis XML 映射文件
│   │   │       └── application.yml     # 配置文件
│   │   └── test/                       # 测试
│   ├── pom.xml
│   └── files/                          # 文件上传目录
│
└── elderly-care-vue/        # 前端项目
    ├── src/
    │   ├── assets/          # 静态资源
    │   ├── components/      # 公共组件
    │   ├── layout/          # 布局组件
    │   ├── router/          # 路由配置
    │   ├── store/           # Vuex 状态管理
    │   ├── utils/           # 工具函数
    │   ├── views/           # 页面组件
    │   │   ├── back/        # 后台管理页面
    │   │   └── front/       # 前台展示页面
    │   ├── App.vue
    │   └── main.js
    ├── public/
    ├── package.json
    └── vue.config.js
```

## 🚀 快速开始

### 环境要求

- **JDK**: 17+
- **Node.js**: 14+
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Maven**: 3.6+

### 后端启动

1. 导入数据库

使用 `_localhost-2026_06_06_23_03_21-dump.sql` 文件导入数据库，或使用项目生成的 `schema.sql`：

```sql
CREATE DATABASE db_elderly_care DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_elderly_care;
SOURCE schema.sql;
```

2. 修改配置

编辑 `elderly-care-server/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db_elderly_care?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password
```

3. 启动项目

```bash
cd elderly-care-server
mvn clean package -DskipTests
java -jar target/elderly-care-server-0.0.1-SNAPSHOT.jar
```

后端服务启动后访问：`http://localhost:8080`

### 前端启动

```bash
cd elderly-care-vue
npm install
npm run serve
```

前端服务启动后访问：`http://localhost:9527`

## 👥 用户角色

| 角色 | 说明 | 功能权限 |
|------|------|----------|
| **管理员 (Admin)** | 系统管理员 | 用户管理、护工管理、房间管理、床位管理、入住管理、账单管理、新闻管理、公告管理、数据统计等 |
| **护工 (Employee)** | 养老院工作人员 | 房间管理、床位管理、入住管理、访客管理、外出管理等 |
| **用户 (User)** | 普通用户/老人家属 | 查看养老院信息、浏览新闻公告、申请入住、查询账单、访客登记、意见反馈等 |

## 📋 主要功能

### 前台系统

| 功能模块 | 说明 |
|---------|------|
| 首页展示 | 养老院简介、轮播图、热门护工、最新公告等 |
| 养老院信息 | 养老院基本信息、环境照片、服务内容等 |
| 新闻资讯 | 查看养老服务相关新闻资讯 |
| 公告通知 | 查看养老院发布的公告 |
| 用户登录/注册 | 用户账号管理 |
| 入住申请 | 在线申请养老院入住 |
| 账单查询 | 查询入住费用账单 |
| 访客登记 | 预约访客登记 |
| 意见反馈 | 用户意见建议反馈 |

### 后台系统

| 功能模块 | 说明 |
|---------|------|
| 用户管理 | 查看、编辑、删除用户信息 |
| 护工管理 | 护工信息管理 |
| 房间管理 | 房间信息增删改查 |
| 床位管理 | 床位状态管理 |
| 入住管理 | 入住申请审核、入住信息管理 |
| 账单管理 | 费用账单生成、查询、支付确认 |
| 访客管理 | 访客记录管理 |
| 外出管理 | 老人外出记录管理 |
| 新闻管理 | 新闻资讯发布管理 |
| 公告管理 | 公告通知发布管理 |
| 评论管理 | 用户评论审核管理 |
| 反馈管理 | 用户意见反馈处理 |
| 数据统计 | 用户数、入住率、账单统计等图表展示 |

## 🔐 安全认证

### JWT 认证流程

1. 用户登录，后端验证用户名密码
2. 验证成功后，生成 JWT Token 并返回给前端
3. 前端在后续请求的 `Authorization` 请求头中携带 Token：`Bearer <token>`
4. 后端 `JwtAuthenticationFilter` 拦截请求，验证 Token 有效性
5. 验证通过后，将用户信息设置到 Security Context 中

### 权限控制

- **公开接口**：登录、注册、验证码、新闻、公告、养老院信息等
- **管理员接口**：`/admin/**` 需要 ADMIN 角色
- **护工接口**：`/employee/**` 需要 EMPLOYEE 角色
- **用户接口**：`/user/**` 需要 USER 或 ADMIN 或 EMPLOYEE 角色

## 📊 数据库

项目包含以下主要数据表：

- `user` - 用户表
- `admin` - 管理员表
- `employee` - 护工表
- `resthome` - 养老院信息表
- `room` - 房间表
- `bed` - 床位表
- `live` - 入住记录表
- `bill` - 账单表
- `visit` - 访客记录表
- `leave` - 外出记录表
- `news` - 新闻表
- `notice` - 公告表
- `comment` - 评论表
- `like` - 点赞表
- `feedback` - 反馈表

## 📝 更新日志

### 2026-06-09
- **统一异常处理**：完善全局异常处理器，统一错误响应格式，添加统一错误码枚举 `ErrorCode`
- **统一响应格式**：`JSONReturn` 添加时间戳字段，支持统一的错误码处理
- **SQL 注入防护**：添加 `SqlInjectInterceptor` 拦截器，检测常见 SQL 注入攻击
- **输入参数校验**：为 `LoginDto` 添加 JSR-380 验证注解，增强接口安全性
- **Redis 缓存优化**：实现新闻浏览量统计、点赞功能缓存、验证码存储等缓存策略
- **分布式锁支持**：基于 Redis 实现 `DistributedLock`，支持高并发场景
- **接口限流**：基于 Redis 实现 IP 限流拦截器，防止恶意请求
- **JWT Token 黑名单**：支持主动登出，将 Token 加入 Redis 黑名单

### 2026-06-08
- 集成 Spring Security + JWT 实现认证授权
- 添加 Redis 缓存支持
- 完善权限控制配置

## 📄 许可证

本项目仅供学习交流使用。
