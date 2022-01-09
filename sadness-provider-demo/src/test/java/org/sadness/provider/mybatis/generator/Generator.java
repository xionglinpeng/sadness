package org.sadness.provider.mybatis.generator;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/17 20:39
 */
public class Generator {

    private static final String DB_JDBC_URL = "jdbc:mysql://localhost:3306/bank1?serverTimezone=UTC";

    private static final String DB_USERNAME = "root";

    private static final String DB_PASSWORD = "597646251";

    private static final String BASE_PACKAGE = "org.sadness.provider";

    private static final int PORT = 8068;

    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder()
                .jdbcUrl(DB_JDBC_URL)
                .userName(DB_USERNAME)
                .password(DB_PASSWORD)
                .driverClassName("com.mysql.cj.jdbc.Driver")
//                //数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
//                .schemaName("myBusiness")
                .basePackage(BASE_PACKAGE)
                .port(PORT)
                .build();
        MybatisPlusToolsApplication.run(config);
    }
}
