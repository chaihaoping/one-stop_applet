package org.atgpcm.oneStopApplet.service;

import org.atgpcm.oneStopApplet.domain.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
public interface BrandService extends IService<Brand> {

    List<Brand> selectBrandAndCompeteList(Integer brandId);
}
