package org.atgpcm.oneStopApplet.dao;

import org.atgpcm.oneStopApplet.domain.entity.CarModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 车型表 Mapper 接口
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Repository
public interface CarModelDao extends BaseMapper<CarModel> {

}
