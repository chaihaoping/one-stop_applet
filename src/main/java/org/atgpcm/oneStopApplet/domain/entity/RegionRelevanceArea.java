package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 大区下的省市地区表
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("region_relevance_area")
public class RegionRelevanceArea extends Model<RegionRelevanceArea> {

    private static final long serialVersionUID = 1L;

    /**
     * 大区id
     */
    private Integer regionId;

    /**
     * 省级id
     */
    private Integer areaId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
