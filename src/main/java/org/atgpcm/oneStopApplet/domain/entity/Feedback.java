package org.atgpcm.oneStopApplet.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户反馈建议表
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-11
 */
@Data
/**
 * 此注解会自动生成equals和hashcode方法
 * callSuper = false，做类型判断(判断对象是否相等调用equals)时，只会针对子类的属性做类型判断
 * callSuper = true，做类型判断时不仅调用父类属性也会调用子类属性做类型判断
 */
@EqualsAndHashCode(callSuper = false)
@TableName("feedback")
public class Feedback extends Model<Feedback> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(hidden = true)
    private Integer userId;

    /**
     * 反馈建议
     */
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
