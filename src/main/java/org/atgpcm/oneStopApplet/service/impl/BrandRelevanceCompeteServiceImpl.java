package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.BrandRelevanceCompete;
import org.atgpcm.oneStopApplet.dao.BrandRelevanceCompeteDao;
import org.atgpcm.oneStopApplet.service.BrandRelevanceCompeteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 品牌对应竞品表 服务实现类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class BrandRelevanceCompeteServiceImpl extends ServiceImpl<BrandRelevanceCompeteDao, BrandRelevanceCompete> implements BrandRelevanceCompeteService {

}
