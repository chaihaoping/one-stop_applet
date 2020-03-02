package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.CarModel;
import org.atgpcm.oneStopApplet.dao.CarModelDao;
import org.atgpcm.oneStopApplet.service.CarModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 车型表 服务实现类
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class CarModelServiceImpl extends ServiceImpl<CarModelDao, CarModel> implements CarModelService {

}
