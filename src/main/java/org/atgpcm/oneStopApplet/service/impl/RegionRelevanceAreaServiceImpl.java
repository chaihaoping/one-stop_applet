package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.RegionRelevanceArea;
import org.atgpcm.oneStopApplet.dao.RegionRelevanceAreaDao;
import org.atgpcm.oneStopApplet.service.RegionRelevanceAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 大区下的省市地区表 服务实现类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class RegionRelevanceAreaServiceImpl extends ServiceImpl<RegionRelevanceAreaDao, RegionRelevanceArea> implements RegionRelevanceAreaService {

}
