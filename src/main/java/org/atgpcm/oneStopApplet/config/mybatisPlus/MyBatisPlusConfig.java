package org.atgpcm.oneStopApplet.config.mybatisPlus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chaihaoping
 * @title Mybatis-Plus配置文件
 * @date 2020/1/7 14:41
 * @description Mybatis-Plus配置文件
 */
@Slf4j
@Configuration
@MapperScan("org.atgpcm.oneStopApplet.dao")
public class MyBatisPlusConfig {

    /**
     * @description: 配置分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.debug("=============注册分页插件=============");
        // 开启 count 的 join 优化,只针对 left join !!!
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }

}
