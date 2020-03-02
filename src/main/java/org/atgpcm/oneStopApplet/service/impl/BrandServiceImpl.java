package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.Brand;
import org.atgpcm.oneStopApplet.dao.BrandDao;
import org.atgpcm.oneStopApplet.service.BrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class BrandServiceImpl extends ServiceImpl<BrandDao, Brand> implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> selectBrandAndCompeteList(Integer brandId) {
        return this.brandDao.selectBrandAndCompeteList(brandId);
    }
}
