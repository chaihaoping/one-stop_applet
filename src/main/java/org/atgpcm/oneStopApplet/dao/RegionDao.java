package org.atgpcm.oneStopApplet.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.atgpcm.oneStopApplet.domain.entity.Region;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 品牌所辖大区表 Mapper 接口
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Repository
public interface RegionDao extends BaseMapper<Region> {

    @DS("master")
    List<Region> selectRegionList(@Param("brandId") Integer brandId);
}
