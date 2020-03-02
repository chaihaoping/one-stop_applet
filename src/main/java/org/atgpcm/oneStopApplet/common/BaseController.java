package org.atgpcm.oneStopApplet.common;

import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.domain.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author chaihaoping
 * @title controller基础类
 * @date 2020/2/7 11:13
 * @description controller基础类
 */
@Slf4j
public class BaseController {

    /**
     * 获取当前用户信息
     * @return
     */
    public User getCurrentUser() {
        log.info("=============获取系统登陆用户信息=============");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RedisKeys.USER_KEY+session.getId());
        return user;
    }

}
