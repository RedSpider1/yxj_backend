## 简介
本仓库为”友小聚“后端仓库，基于Java开发。
核心功能截图：
![](https://file.yasinshaw.com/202203/13/1E1A3F8B054B.jpg)

## 贡献者
由于仓库里有秘钥等信息，所以删除了提交记录。特此说明一下本仓库是由以下贡献者共同开发，排名不分先后：

- 15735044435@163.com
- justrightnow@sina.com
- qbhinsist@gmail.com
- kyrenbo@163.com
- lucyvictor@aliyun.com
- w99949299@gmail.com
- holy468@live.com
- nametxy@163.com
- yasinshaw@gmail.com

## 主要框架或依赖
- SpringBoot
- MyBatis
- SpringSecurity
- MySQL
- Redis

## 相关配置修改
需要自己修改：
1. db相关配置
2. redis相关配置
3. 七牛云相关配置
4. 微信小程序相关配置

相关配置文件均在 pss-server/src/main/resources目录下

## 前端服务
前端服务基于微信小程序开发，地址：[RedSpider1/yxj_frontend](https://github.com/RedSpider1/yxj_frontkend)

## 公众号

欢迎关注微信公众号“编了个程”
![公众号](https://file.yasinshaw.com/202004/20/E432D8F2EA04.jpg)

## 开发规范
开发过程需要遵守以下规范，请仔细阅读。
<br/>
1. 代码规范
    - 项目模块
    - 模块详细说明
    - 名称命名
    - 注释说明
2. 打包命令

### 1 代码规范
#### 1.1 项目模块
1. **pss-base**：基础业务模块。
2. **pss-common**：公共模块：包含了所有的实体和接口、工具类等。被其他所有模块所依赖。
3. **pss-search**：搜索模块：包含推荐、最新、搜索。主要提供搜索功能。

#### 1.2 模块详细说明
1. pss-base
    - controller: 提供对外的 API 接口
    - service: 业务逻辑层接口
        - impl: 业务逻辑实现层
    - mapper: 数据库交互层
    - utils: 本模块专有工具类

<br/>
2. pss-common
- model
    - config: 配置相关类
    - exception: 异常类
    - enum: 枚举类
- utils: 公共工具类
- interface: 对外接口，接口中仅包含需要暴露的方法。如pss-search模块需要依赖到pss-base模块相关方法，则需要将接口方法放在该包下。

#### 1.3 名称命名
- 各个层的类名需包含具体的后缀，如**Controller**，**Service**，**Mapper**；
- 分层领域模型规约(详见: [阿里巴巴 Java 开发手册](https://kangroo.gitee.io/ajcg/#/app-layer))：
    - `DO(Data Object)`: 与数据库表结构一一对应，通过 DAO 层向上传输数据源对象；
    - `DTO(Data Transfer Object)`: 数据传输对象，Service 或 Manager 向外传输的对象。
    - `BO(Business Object)`: 业务对象。由 Service 层输出的封装业务逻辑的对象。
    - `AO(Application Object)`: 应用对象。在 Web 层与 Service 层之间抽象的复用对象模型，即为贴近展示层，复用度不高。
    - `VO(View Object)`: 显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
    - `Query`: 数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
- 配置类需要携带 **Config**，工具类需要携带 **Utils**，枚举类需要携带 **Enums** 等；
- 方法的命名一定做到见名知意，保证单词的拼写正确。

#### 1.4 注释说明
- [IDEA 设置 java 类方法注释模板](https://blog.csdn.net/u012946310/article/details/93495712)
1. 类注释
```
/**
 * @description: PSS业务层
 * @author: QingChen
 * @date: 2021/07/11 22:00
 */
```
2. 方法的注释
```
/**
 * @description: 组队单获取
 * @author: QingChen
 * @date：2021/07/11 22:00
 * @param labelId
 * @return 
 */
```

3. 代码注释：单行注释，需要独占一行，允许有多行
```
// 方法作用说明
```

### 2. 打包命令
```
cd pss-backend
mvn clean install -Dmaven.test.skip=true

```
