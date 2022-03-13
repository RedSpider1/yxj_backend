/*
package com.redspider.pss;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class TestMybatis {


    public static void main(String[] args) {

        String module = "GroupTeam";
        String doName = "pss_group_team";
        if (StringUtils.isBlank(module)){
            System.out.println("你先写模块名字！！！！！！ module 啊啊啊啊啊啊啊啊啊啊啊 ");
        }
        if (StringUtils.isBlank(doName)){
            System.out.println("数据库表不写了？？？？！！！！！！ doName 啊啊啊啊啊啊啊 ");
        }

        AutoGenerator mpg = new AutoGenerator();
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();

        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");  //生成路径(一般都是生成在此项目的src/main/java下面)
        gc.setOutputDir(projectPath + "/pss-base/src/main/java/");  //生成路径(一般都是生成在此项目的src/main/java下面)
        gc.setAuthor("LucyVictor"); //设置作者
        gc.setOpen(false);
        gc.setFileOverride(true); //第二次生成会把第一次生成的覆盖掉
//        gc.setServiceName("%sService"); //生成的service接口名字首字母是否为I，这样设置就没有
        gc.setEntityName("%sDO");
        gc.setMapperName("%sMapper");
        gc.setBaseResultMap(false); //生成resultMap
        mpg.setGlobalConfig(gc);

        //2、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://8.136.193.14:3306/pss?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("pss");
        dsc.setPassword("pss");
        mpg.setDataSource(dsc);

        // 3、包配置
        PackageConfig pc = new PackageConfig();

        //自己写要得模块
        pc.setParent("com.redspider.pss.db");
        // 配置模块名（需要修改）
        pc.setModuleName("GroupTeam");
//        // 配置 entity 包名
        pc.setEntity("dataObject");
//        // 配置 mapper 包名
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); //使用lombok
        strategy.isEntitySerialVersionUID();
        strategy.setInclude(doName);  // 逆向工程使用的表   如果要生成多个,这里可以传入String[]
        mpg.setStrategy(strategy);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("");
        templateConfig.setService("");
        templateConfig.setServiceImpl("");
        mpg.setTemplate(templateConfig);
        //5、执行
        mpg.execute();
    }


//    @Test
//    public void autoGenerate() {
//        // Step1：代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // Step2：全局配置
//        GlobalConfig gc = new GlobalConfig();
//        // 填写代码生成的目录(需要修改)
////        String projectPath = "E:\\myProject\\test\\test_mybatis_plus";
//        String projectPath = "src/main/java/com/redspider/pss/db";
//        // 拼接出代码最终输出的目录
//        gc.setOutputDir(projectPath + "/src/main/java");
//        // 配置开发者信息（可选）（需要修改）
//        gc.setAuthor("LucyVictor");
//        // 配置是否打开目录，false 为不打开（可选）
//        gc.setOpen(false);
//        // 实体属性 Swagger2 注解，添加 Swagger 依赖，开启 Swagger2 模式（可选）
//        //gc.setSwagger2(true);
//        // 重新生成文件时是否覆盖，false 表示不覆盖（可选）
//        gc.setFileOverride(false);
//        // 配置主键生成策略，此处为 ASSIGN_ID（可选）
//        gc.setIdType(IdType.ASSIGN_ID);
//        // 配置日期类型，此处为 ONLY_DATE（可选）
//        gc.setDateType(DateType.ONLY_DATE);
//        // 默认生成的 service 会有 I 前缀
//        gc.setServiceName("%sService");
//        mpg.setGlobalConfig(gc);
//
//        // Step3：数据源配置（需要修改）
//        DataSourceConfig dsc = new DataSourceConfig();
//        // 配置数据库 url 地址
//        dsc.setUrl("jdbc:mysql://8.136.193.14:3306/pss?useUnicode=true&characterEncoding=utf8");
//        // dsc.setSchemaName("testMyBatisPlus"); // 可以直接在 url 中指定数据库名
//        // 配置数据库驱动
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        // 配置数据库连接用户名
//        dsc.setUsername("pss");
//        // 配置数据库连接密码
//        dsc.setPassword("pss");
//        mpg.setDataSource(dsc);
//
//        // Step:4：包配置
//        PackageConfig pc = new PackageConfig();
//        // 配置父包名（需要修改）
//        pc.setParent("com.lyh.test");
//        // 配置模块名（需要修改）
//        pc.setModuleName("test_mybatis_plus");
//        // 配置 entity 包名
//        pc.setEntity("entity");
//        // 配置 mapper 包名
//        pc.setMapper("mapper");
//        // 配置 service 包名
//        pc.setService("service");
//        // 配置 controller 包名
////        pc.setController("controller");
//        mpg.setPackageInfo(pc);
//
//        // Step5：策略配置（数据库表配置）
//        StrategyConfig strategy = new StrategyConfig();
//        // 指定表名（可以同时操作多个表，使用 , 隔开）（需要修改）
//        strategy.setInclude("test_mybatis_plus_user");
//        // 配置数据表与实体类名之间映射的策略
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        // 配置数据表的字段与实体类的属性名之间映射的策略
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        // 配置 lombok 模式
//        strategy.setEntityLombokModel(true);
//        // 配置 rest 风格的控制器（@RestController）
//        strategy.setRestControllerStyle(true);
//        // 配置驼峰转连字符
//        strategy.setControllerMappingHyphenStyle(true);
//        // 配置表前缀，生成实体时去除表前缀
//        // 此处的表名为 test_mybatis_plus_user，模块名为 test_mybatis_plus，去除前缀后剩下为 user。
//        strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//
//        // Step6：执行代码生成操作
//        mpg.execute();

}
*/
