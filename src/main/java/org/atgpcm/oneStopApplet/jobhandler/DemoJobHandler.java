package org.atgpcm.oneStopApplet.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.atgpcm.oneStopApplet.config.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 */
@Component
public class DemoJobHandler {

    @Autowired
    private RedisUtil redisUtil;

    @XxlJob("demoJobHandler")
    public ReturnT<String> execute(String param) {
        XxlJobLogger.log("================XXL-JOB, demoJobHandler================");
//        User user = new User();
//        user.setAge(23);
//        user.setGender("柴浩平");
//        user.setCreateDate(new Date());
//        redisUtil.set("user",user);
//        redisUtil.set("str","123");
//        System.out.println("================XXL-JOB测试，执行成功================");
//        User u = (User) redisUtil.get("user");
//        System.out.println("user="+u.toString());
//        System.out.println("str="+redisUtil.get("str"));
        return ReturnT.SUCCESS;
    }

}