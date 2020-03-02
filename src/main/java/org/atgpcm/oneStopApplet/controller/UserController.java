package org.atgpcm.oneStopApplet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.RedisKeys;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.User;
import org.atgpcm.oneStopApplet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户信息相关模块")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value="用户登录", notes="用户登录", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value="账号",required=true,dataType="String"),
            @ApiImplicitParam(name="password",value="密码",required=true,dataType="String")
    })
    //@ApiIgnore 忽略参数
    public Result login(User user, HttpSession session) {
        log.info("=============用户登录=============");
        User u = this.userService.getOne(new QueryWrapper<User>().eq("username",user.getUsername()).
                eq("password",user.getPassword()).eq("flag",0).last("LIMIT 1"));
        if (u == null) {
            return Result.error(BizStatusEnum.ACCOUNT_PASSWORD_FAIL.getCode(),BizStatusEnum.ACCOUNT_PASSWORD_FAIL.getMsg());
        } else {
            session.setAttribute(RedisKeys.USER_KEY+session.getId(),u);
            return Result.success();
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value="用户登出", notes="用户登出", produces="application/json")
    public Result logout(HttpSession session) {
        log.info("=============用户登出=============");
        session.removeAttribute(RedisKeys.USER_KEY+session.getId());
        return Result.success();
    }
}
