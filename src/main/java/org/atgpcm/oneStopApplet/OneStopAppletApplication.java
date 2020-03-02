package org.atgpcm.oneStopApplet;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800) //开启 redis session 支持,并配置 session 过期时间
// mybatis动态数据源排除原生Druid的快速配置类
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class OneStopAppletApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneStopAppletApplication.class, args);
        System.out.println("============OneStopAppletApplication启动成功============");
    }

}
