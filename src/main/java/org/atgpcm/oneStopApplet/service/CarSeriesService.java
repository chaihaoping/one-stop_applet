package org.atgpcm.oneStopApplet.service;

import org.atgpcm.oneStopApplet.domain.entity.CarSeries;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 车系表 服务类
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
public interface CarSeriesService extends IService<CarSeries> {

    List<CarSeries> selectSeriesAndModelList(Integer brandId);

    List<CarSeries> selectSeriesAndCompeteList(Integer seriesId);
}
