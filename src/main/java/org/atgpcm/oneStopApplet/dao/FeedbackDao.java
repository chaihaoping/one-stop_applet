package org.atgpcm.oneStopApplet.dao;

import org.atgpcm.oneStopApplet.domain.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户反馈建议表 Mapper 接口
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-11
 */
@Repository
public interface FeedbackDao extends BaseMapper<Feedback> {

}
