package org.atgpcm.oneStopApplet.domain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 销售记录
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sales_record")
public class SalesRecord extends Model<SalesRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 车系id
     */
    private Integer seriesId;

    /**
     * 车型id
     */
    private Integer modelId;

    /**
     * 大区id
     */
    private Integer regionId;

    /**
     * 省级id
     */
    private Integer provinceId;

    /**
     * 市级id
     */
    private Integer cityId;

    /**
     * 售价：万
     */
    private BigDecimal price;

    /**
     * 购买时间
     */
    private Date buyTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 竞品信息
     */
    @TableField(exist = false)
    private List<SalesRecord> competeList;

    /**
     * 销售数量
     */
    @TableField(exist = false)
    private Integer salesNum;

    /**
     * 销售数量是否多于本品
     */
    @TableField(exist = false)
    private Boolean salesNumRed;

    /**
     * 平均价格
     */
    @TableField(exist = false)
    private BigDecimal avgPrice;

    /**
     * 平均价格是否高于本品
     */
    @TableField(exist = false)
    private Boolean avgPriceRed;

    /**
     * 省级名称
     */
    @TableField(exist = false)
    private String provinceName;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    private String brandName;

    /**
     * 占比
     */
    @TableField(exist = false)
    private BigDecimal percent;

    /**
     * 车系名称
     */
    @TableField(exist = false)
    private String seriesName;

    /**
     * 车型名称
     */
    @TableField(exist = false)
    private String modelName;

    /**
     * 日期
     */
    @TableField(exist = false)
    private String time;

    /**
     * 大区名称
     */
    @TableField(exist = false)
    private String regionName;

    /**
     * 市级名称
     */
    @TableField(exist = false)
    private String cityName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
