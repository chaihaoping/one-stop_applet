package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车系对应竞品表
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("series_relevance_compete")
public class SeriesRelevanceCompete extends Model<SeriesRelevanceCompete> {

    private static final long serialVersionUID = 1L;

    /**
     * 车系id
     */
    private Integer seriesId;

    /**
     * 竞品车系id
     */
    private Integer competeId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
