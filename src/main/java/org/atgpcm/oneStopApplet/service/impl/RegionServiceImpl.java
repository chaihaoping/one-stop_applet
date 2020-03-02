package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.Region;
import org.atgpcm.oneStopApplet.dao.RegionDao;
import org.atgpcm.oneStopApplet.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 品牌所辖大区表 服务实现类
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class RegionServiceImpl extends ServiceImpl<RegionDao, Region> implements RegionService {

    @Autowired
    private RegionDao regionDao;

    @Override
    public List<Region> selectRegionList(Integer brandId) {
        return this.regionDao.selectRegionList(brandId);
    }
}
