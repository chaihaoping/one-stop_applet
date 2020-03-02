package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.Area;
import org.atgpcm.oneStopApplet.dao.AreaDao;
import org.atgpcm.oneStopApplet.service.AreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 省市表 服务实现类
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class AreaServiceImpl extends ServiceImpl<AreaDao, Area> implements AreaService {

}
