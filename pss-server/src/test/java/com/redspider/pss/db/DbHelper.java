package com.redspider.pss.db;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * 创建数据源链接<br/>
 * {@link #createMemoryDataSource(Collection)} 使用h2数据库创建内存数据源，参数为初始化sql
 *
 *
 * @version 0.0.1
 * @Author: 千般婉转皆戏言丶
 * @Date: 2021/8/25 21:45
 */
public class DbHelper {

    public DbHelper() {
    }

    /**
     * 创建一个h2数据源
     * @param sqlScripts 初始化sql
     * @return DataSource
     */
    public static DataSource createMemoryDataSource(Collection<String> sqlScripts) {
        EmbeddedDatabaseBuilder builder = createMemoryDataBaseBuilder();
        sqlScripts.forEach(builder::addScript);
        return builder.build();
    }

    /**
     * 创建一个h2数据源builder，支持自行对builder参数设置
     * @return EmbeddedDatabaseBuilder
     */
    public static EmbeddedDatabaseBuilder createMemoryDataBaseBuilder() {
        return (new EmbeddedDatabaseBuilder()).setType(EmbeddedDatabaseType.H2).setName("testdb;mode=MySQL").setScriptEncoding("UTF-8").ignoreFailedDrops(true);
    }
}
