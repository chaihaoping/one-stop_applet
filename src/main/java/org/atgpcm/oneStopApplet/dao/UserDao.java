package org.atgpcm.oneStopApplet.dao;

import org.atgpcm.oneStopApplet.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@Repository
public interface UserDao extends BaseMapper<User> {

}
