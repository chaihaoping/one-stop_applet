package org.atgpcm.oneStopApplet.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.atgpcm.oneStopApplet.domain.entity.CarSeries;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 车系表 Mapper 接口
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Repository
public interface CarSeriesDao extends BaseMapper<CarSeries> {

    @DS("master")
    List<CarSeries> selectSeriesAndModelList(@Param("brandId") Integer brandId);

    @DS("master")
    List<CarSeries> selectSeriesAndCompeteList(@Param("seriesId") Integer seriesId);
}
