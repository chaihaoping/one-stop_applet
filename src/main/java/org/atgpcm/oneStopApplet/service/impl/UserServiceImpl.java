package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.User;
import org.atgpcm.oneStopApplet.dao.UserDao;
import org.atgpcm.oneStopApplet.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
