package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.CarSeries;
import org.atgpcm.oneStopApplet.dao.CarSeriesDao;
import org.atgpcm.oneStopApplet.service.CarSeriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 车系表 服务实现类
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class CarSeriesServiceImpl extends ServiceImpl<CarSeriesDao, CarSeries> implements CarSeriesService {

    @Autowired
    private CarSeriesDao carSeriesDao;

    @Override
    public List<CarSeries> selectSeriesAndModelList(Integer brandId) {
        return this.carSeriesDao.selectSeriesAndModelList(brandId);
    }

    @Override
    public List<CarSeries> selectSeriesAndCompeteList(Integer seriesId) {
        return this.carSeriesDao.selectSeriesAndCompeteList(seriesId);
    }
}
