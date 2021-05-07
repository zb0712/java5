package com.szb.java5.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 石致彬
 * @since 2021-03-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Department对象", description="部门")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "department_id", type = IdType.AUTO)
    private Long departmentId;
    @ApiModelProperty(value = "部门名称",required = true)
    private String departmentName;
    @ApiModelProperty(value = "报名链接",required = true)
    private String departmentUrl;
    @ApiModelProperty(value = "部门简介",required = true)
    private String introduction;
    @ApiModelProperty(value = "问卷id",example = "1")
    private Long questionnaireId;


}
