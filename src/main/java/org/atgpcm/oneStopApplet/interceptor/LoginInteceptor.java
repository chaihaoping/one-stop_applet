package org.atgpcm.oneStopApplet.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BizException;
import org.atgpcm.oneStopApplet.common.RedisKeys;
import org.atgpcm.oneStopApplet.domain.entity.User;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author chaihaoping
 * @title 登录拦截器
 * @date 2020/1/16 14:11
 * @description 登录拦截器
 */
@Slf4j
public class LoginInteceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session中用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RedisKeys.USER_KEY+session.getId());
        if (user == null) {
            log.info("=============未获取到用户信息=============");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",BizStatusEnum.USER_IS_NOT_EXIST.getCode());
            jsonObject.put("data",BizStatusEnum.USER_IS_NOT_EXIST.getMsg());
            out.println(jsonObject.toString());
            out.flush();
            out.close();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
