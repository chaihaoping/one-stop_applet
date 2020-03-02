package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车型表
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("car_model")
public class CarModel extends Model<Area> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车型名称
     */
    private String modelName;

    /**
     * 年款
     */
    private String mj;

    /**
     * 车系id
     */
    private Integer seriesId;

    /**
     * 是否删除：0 未删除，1 已删除。默认0
     */
    private Boolean flag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 年款+车型名称
     */
    @TableField(exist = false)
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
