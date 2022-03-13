#### 使用说明 
##### 单测
* 单测示例见PssGroupTeamQueryServiceTest#queryUsersByGroupIdTest()
* 需要使用spring环境的测试请继承PssApplicationTests
##### com.redsipder.pss.db文件夹说明
* DbHelper：用于创建dataSource，为MockBeanConfiguration提供支持
* MockBeanConfiguration：数据源mock配置，使用@TestConfiguration注解。
单测时覆盖dataSource，以适配单测时需要的不同数据源，或避免影响线上/测试库环境。
在单测类添加@ContextConfiguration(classes={MockBeanConfiguration.class})注解以激活

