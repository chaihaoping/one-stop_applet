package org.atgpcm.oneStopApplet.service;

import org.atgpcm.oneStopApplet.domain.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌所辖大区表 服务类
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
public interface RegionService extends IService<Region> {

    List<Region> selectRegionList(Integer brandId);
}
