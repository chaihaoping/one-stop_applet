package org.atgpcm.oneStopApplet.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.atgpcm.oneStopApplet.domain.entity.Brand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 品牌表 Mapper 接口
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@Repository
public interface BrandDao extends BaseMapper<Brand> {

    @DS("master")
    List<Brand> selectBrandAndCompeteList(Integer brandId);
}
