package com.szb.java5.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "stu_id", type = IdType.AUTO)
    private Long stuId;

    @ApiModelProperty(value = "姓名", required = true)
    private String stuName;
    @ApiModelProperty(value = "学号",required = true)
    private String stuNum;
    @ApiModelProperty(value = "邮箱",required = true)
    private String stuEmail;

    @ApiModelProperty(value = "报名的部门id",required = true)
    @TableField(exist = false)
    private Long departmentId;

    @TableField(exist = false)
    private String code;

    @TableField(exist = false)
    private List<Answer> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!stuName.equals(student.stuName)) return false;
        return stuNum.equals(student.stuNum);
    }

}
