package org.atgpcm.oneStopApplet.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.Feedback;
import org.atgpcm.oneStopApplet.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户反馈建议表 前端控制器
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/feedback")
@Slf4j
@Api(tags = "反馈建议相关模块")
public class FeedbackController extends BaseController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/saveFeedback")
    @ApiOperation(value="反馈建议保存", notes="反馈建议保存", produces="application/json")
    @ApiImplicitParam(name="remark",value="反馈建议",required=true,dataType="String")

    public Result saveFeedback(Feedback feedback) {
        log.info("=============反馈建议保存=============");
        try {
            feedback.setUserId(this.getCurrentUser().getId());
            this.feedbackService.save(feedback);
            return Result.success();
        } catch (Exception e) {
            log.error("\n=============反馈建议保存:{}",e);
            return Result.error(BizStatusEnum.SAVE_FEEDBACK_FAIL.getCode(),BizStatusEnum.SAVE_FEEDBACK_FAIL.getMsg());
        }
    }
}
