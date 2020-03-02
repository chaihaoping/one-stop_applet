package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车系表
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("car_series")
public class CarSeries extends Model<CarSeries> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车系名称
     */
    private String seriesName;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 是否删除：0 未删除，1 已删除。默认0
     */
    private Boolean flag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * false: 该属性不为数据库表字段，但又是必须使用的
     * true：该属性为数据库字段
     */
    @TableField(exist = false)
    private List<CarModel> carModelList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
