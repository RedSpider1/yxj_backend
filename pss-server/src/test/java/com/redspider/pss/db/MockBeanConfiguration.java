package com.redspider.pss.db;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * 修改dataSource为h2数据库<br/>
 * 使用resources.db.createTableV1和resources.db.createTableV2初始化数据库信息<br/>
 * 单测使用时须在测试类添加{@link org.springframework.test.context.ContextConfiguration}注解<br/>
 *
 * @author 千般婉转皆戏言丶
 * @date 2021/8/25 21:12
 *
 * @see org.springframework.test.context.ContextConfiguration
 */
@TestConfiguration
public class MockBeanConfiguration {

    public MockBeanConfiguration() {
    }

    @Bean
    public DataSource createMemoryDataSource() {
        return DbHelper.createMemoryDataSource(this.getSqlScripts());
    }

    protected List<String> getSqlScripts() {
        return Arrays.asList("classpath:db/createTableV1.sql","classpath:db/createTableV2.sql");
    }
}
