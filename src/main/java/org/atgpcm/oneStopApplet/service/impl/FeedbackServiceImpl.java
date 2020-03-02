package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.Feedback;
import org.atgpcm.oneStopApplet.dao.FeedbackDao;
import org.atgpcm.oneStopApplet.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户反馈建议表 服务实现类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-11
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDao, Feedback> implements FeedbackService {

}
