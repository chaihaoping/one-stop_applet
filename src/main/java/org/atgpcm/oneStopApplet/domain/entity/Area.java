package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 省市表
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("area")
public class Area extends Model<Area> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区类型
     */
    private String areaType;

    /**
     * 父级id
     */
    private Integer pid;

    @TableField(exist = false)
    private List<Area> areaList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
