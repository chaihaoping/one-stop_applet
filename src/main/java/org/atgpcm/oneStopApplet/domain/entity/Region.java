package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 品牌所辖大区表
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("region")
public class Region extends Model<Region> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 大区名称
     */
    private String regionName;

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

    @TableField(exist = false)
    private List<Area> provinceList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
