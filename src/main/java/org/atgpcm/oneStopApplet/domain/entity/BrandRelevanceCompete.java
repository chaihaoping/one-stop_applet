package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 品牌对应竞品表
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("brand_relevance_compete")
public class BrandRelevanceCompete extends Model<BrandRelevanceCompete> {

    private static final long serialVersionUID = 1L;

    /**
     * 本品id
     */
    private Integer brandId;

    /**
     * 竞品id
     */
    private Integer competeId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
